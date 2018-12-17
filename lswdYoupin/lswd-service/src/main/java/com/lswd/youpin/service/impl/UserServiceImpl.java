package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.security.MD5;
import com.lswd.youpin.common.util.Strings;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.commons.LoginType;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.RoleMapper;
import com.lswd.youpin.dao.UserLogMapper;
import com.lswd.youpin.dao.UserMapper;
import com.lswd.youpin.dao.lsy.MachineMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.UserLog;
import com.lswd.youpin.model.vo.Nodes;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.response.LsyResponse;
import com.lswd.youpin.service.UserService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.shiro.kit.ShiroKit;
import com.lswd.youpin.shiro.token.CustomizedToken;
import com.lswd.youpin.shiro.token.TokenProcessor;
import com.lswd.youpin.utils.MD5Utils;
import com.lswd.youpin.utils.SerializeUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/4/12.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_LOGIN_TYPE = LoginType.USER.toString();
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${user.lockTime}")//用户锁定次数
    private String defaultLockTime;
    @Value("${user.defaultPassword}")//用户锁定次数
    private String defaultPassword;
    @Value("${page.maxCount}")//用户锁定次数
    private String maxCount;
    @Value("${user.defaultPassword}")//用户默认密码
    private String defaultUserPassword;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogMapper userLogMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    private CanteenMapper canteenMapper;

    /**
     * @param name
     * @return
     * @author liuhao
     * @功能 通过用户名查找用户
     */

    public User getUserByName(String name) {
        log.info("根据有户名查找用户信息=======username==========" + name);
        try {
            User user = userMapper.selectByName(name);
            return user;
        } catch (Exception e) {
            log.error("根据用户名查找用户失败==={}", e.getMessage());
        }
        return null;
    }


    @Override
    public LsResponse signUp(User user, User u) {

        log.info("{} is being executed. User = {}", "signUp", JSON.toJSON(u.getUsername() + "准备注册新的量食用户") + user.getUsername());
        LsResponse lsResponse = new LsResponse();
        if (user != null) {
            if (!"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
                try {
                    User u1 = userMapper.selectByName(user.getUsername());
                    if (u1 == null) {
                        user.setPassword(MD5.generate(defaultPassword, true));
                        user.setCreateTime(Dates.now());
                        user.setUpdateTime(Dates.now());
                        user.setUUID(MD5Utils.getMD5ForRandomUUID());
                        int id = userMapper.insertUser(user);
                        if (id != 0) {
                            UserLog userLog = new UserLog();
                            userLog.setUserId(user.getId());
                            userLog.setUsername(user.getUsername().getBytes());
                            userLog.setLoginId(String.valueOf(u.getId()));
                            userLog.setOperation(u.getUsername() + "添加了用户名为" + user.getUsername() + "的用户");
                            userLog.setOperationTime(Dates.now());
                            userLog.setOperatorType((short) 1);
                            userLogMapper.insertUserLog(userLog);
                            log.info("{} is being executed. User = {}", "signUp", JSON.toJSON(u.getUsername() + "注册新的量食用户") + user.getUsername() + "成功");
                            lsResponse.checkSuccess(true, CodeMessage.USER_REGIST_SUCCESS.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.USER_REGIST_ERR.name());
                    }
                } catch (Exception e) {
                    lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                    log.error("{} is being executed. User = {}", "signUp", JSON.toJSON(e.toString()));
                }
            }
        }
        return lsResponse;
    }

    /**
     * @param data
     * @return
     * @功能 用户登录
     */
    @Override
    public LsResponse login(String data, HttpServletRequest request) {
        log.info("{} is being executed. User = {}", "userLogin", JSON.toJSON(data));
        LsResponse lsResponse = new LsResponse();
        JSONObject jsonObject = JSONObject.parseObject(data);
        String code = jsonObject.get("code").toString();
        String uuid = jsonObject.get("uuid").toString();
        String userName = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();
        JSONObject stateObject=JSON.parseObject("{selected:true}");
        //校验图片验证码
        String verifyCode = new String(redisManager.get(uuid.getBytes()));
        try {
            if ((verifyCode != null && !verifyCode.equals("")) && verifyCode.equals(code)) {
                CustomizedToken customizedToken = new CustomizedToken(userName, password, USER_LOGIN_TYPE);
                customizedToken.setRememberMe(true);
                Subject subject = SecurityUtils.getSubject();
                subject.login(customizedToken);
                User u = (User) subject.getPrincipal();
                List<Nodes> topNodes = roleMapper.getResource(u.getRoleId(), 0, 1);
                topNodes.get(0).setState(stateObject);
                List<Nodes> rightNodes = roleMapper.getResource(u.getRoleId(), 1, 2);
                Map<String, Object> map = new HashedMap();
                String access_token = TokenProcessor.getInstance().generateToken(userName, true);//生成token
                redisManager.set(access_token.getBytes(), SerializeUtils.serialize(u), ConstantsCode.SESSION_EXPIRE);//向redis存储数据
                request.setAttribute("user", u);
                map.put("token", access_token);
                map.put("topNodes", topNodes);
                map.put("rightNodes", rightNodes);
                String nickName = u.getNickname() != null ? u.getNickname() : "欢迎您";
                lsResponse.setMessage(nickName);
                lsResponse.setData(map);//返回token值
                UserLog userLog = new UserLog();
                userLog.setUserId(u.getId());
                userLog.setUsername(u.getUsername().getBytes());
                userLog.setLoginId(String.valueOf(u.getId()));
                userLog.setOperation(u.getUsername() + "登录后台管理程序");
                userLog.setOperationTime(Dates.now());
                userLog.setOperatorType((short) 1);
                userLogMapper.insertUserLog(userLog);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.USER_NO_CODE.name());
                return lsResponse;
            }
        } catch (UnknownAccountException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.USER_LOGIN_NO.name());
        } catch (IncorrectCredentialsException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.USER_LOGIN_NO.name());
        } catch (ExcessiveAttemptsException e) {
            log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            lsResponse.checkSuccess(false, CodeMessage.USER_TIME_ERR.name());
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


    /**
     * @param
     * @return
     * @功能 APP用户登录
     */
    @Override
    public LsyResponse doLogin(String username,String password,HttpServletRequest request) {
        log.info("{} is being executed. User = {}", "userLogin", JSON.toJSON(username));
        LsyResponse lsyResponse = new LsyResponse();
        JSONObject stateObject=JSON.parseObject("{selected:true}");
        try {
                CustomizedToken customizedToken = new CustomizedToken(username, password, USER_LOGIN_TYPE);
                customizedToken.setRememberMe(true);
                Subject subject = SecurityUtils.getSubject();
                subject.login(customizedToken);
                User u = (User) subject.getPrincipal();

                String access_token = TokenProcessor.getInstance().generateToken(username, true);//生成token
                redisManager.set(access_token.getBytes(), SerializeUtils.serialize(u));//向redis存储数据
               Map<String, Object> map = new HashedMap();

                request.setAttribute("user", u);
                 String[] canteenIds = u.getCanteenIds().split(",");
                 log.info("canteenIds" + u.getCanteenIds().toString());
                 log.info("token" + access_token);
                 List<Map<String,Object>> canteens  = canteenMapper.getCanteensByCanteenIds(canteenIds);
                map.put("token", access_token);
                map.put("defaultid", canteenIds[0]);
                map.put("avartar", "https://web.lsypct.com/upload/nodata/manager.png");
                map.put("list", canteens);

                lsyResponse.setData(map);//返回token值
                UserLog userLog = new UserLog();
                userLog.setUserId(u.getId());
                userLog.setUsername(u.getUsername().getBytes());
                userLog.setLoginId(String.valueOf(u.getId()));
                userLog.setOperation(u.getUsername() + "登录后台管理程序");
                userLog.setOperationTime(Dates.now());
                userLog.setOperatorType((short) 1);
                userLogMapper.insertUserLog(userLog);

        } catch (UnknownAccountException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsyResponse.setAsFailure();
        } catch (IncorrectCredentialsException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsyResponse.setAsFailure();
        } catch (ExcessiveAttemptsException e) {
            log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            lsyResponse.setAsFailure();
        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error(e.toString());
        }
        return lsyResponse;
    }

    @Override
    public User selectUserById(Integer id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    /**
     * @param id
     * @param u
     * @return
     * @功能 重置用户密码，重置后的密码为123456
     */
    @Override
    public LsResponse restPassword(Integer id, User u) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "restPassword", JSON.toJSON(u.getUsername() + "想要初始化id为" + id + "用户的密码"));
        try {
            User user = new User();
            user.setId(id);
            user.setPassword(ShiroKit.md5(defaultPassword, user.getUsername()));
            userMapper.updateByPrimaryKeySelective(user);
            UserLog userLog = new UserLog();
            userLog.setUserId(user.getId());
            userLog.setUsername(user.getUsername().getBytes());
            userLog.setLoginId(String.valueOf(u.getId()));
            userLog.setOperation(u.getUsername() + "重置用户名为" + user.getUsername() + "的用户密码");
            userLog.setOperationTime(Dates.now());
            userLog.setOperatorType((short) 1);
            userLogMapper.insertUserLog(userLog);
            log.info("{} is being executed. User = {}", "restPassword", JSON.toJSON(user.getUsername() + "修改成功"));
            lsResponse.setMessage("重置成功");
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.USER_INIT_PASSWORD_ERR.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    /**
     * @param user
     * @return
     * @author liuhao
     * @功能 添加用户
     */
    @Override
    public LsResponse add(User user, User u) {
        log.info("{} is being executed. User = {}", "addUser", JSON.toJSON(u.getUsername() + "准备追加" + user.getUsername()));
        LsResponse lsResponse = new LsResponse();
        if (user != null) {
            if (!Strings.isContainChinese(user.getUsername())) {
                User u1 = userMapper.selectByName(user.getUsername());
                if (u1 == null) {
                    //对用户密码进行处理,采取shiro配置中的MD5加密，加盐方式为用户账号
                    String code = u.getTenantId().substring(0, 4);
                    user.setPassword(ShiroKit.md5(user.getPassword(), user.getUsername()));
                    user.setCreateTime(Dates.now());
                    user.setUpdateTime(Dates.now());
                    user.setUUID(MD5Utils.getMD5ForRandomUUID());
                    user.setIsDelete(false);
                    user.setUserType(u.getUserType());
                    user.setTenantId(u.getTenantId());
                    //一个用户只有一个角色
                    user.setRoleId(user.getId());
                    //设置用户编号其规则为用户商家编号加100001+自增id
                    Integer id = userMapper.getLastUserId();
                    if (id == null) {
                        id = 0;
                    }
                    user.setUserId(code + String.valueOf(100001 + id));
                    try {
                        userMapper.insertSelective(user);
                        //添加用户操作日志
                        UserLog userLog = new UserLog();
                        userLog.setUserId(user.getId());
                        userLog.setUsername(user.getUsername().getBytes());
                        userLog.setLoginId(String.valueOf(u.getId()));
                        userLog.setOperation(u.getUsername() + "添加了用户名为" + user.getUsername() + "的用户");
                        userLog.setOperationTime(Dates.now());
                        userLog.setOperatorType((short) 1);
                        userLogMapper.insertUserLog(userLog);
                        log.info("{} is being executed. User = {}", "addUser", JSON.toJSON(u.getUsername() + "添加了用户" + user.getUsername()));
                    } catch (Exception e) {
                        lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                        log.error("{} is being executed. User = {}", "addUser", JSON.toJSON(e.toString()));
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.USER_YE_LOGIN_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.USER_CHINA_WEN.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.USER_MESSAGE_ERR.name());
        }
        return lsResponse;
    }

    /**
     * @param user
     * @param u
     * @return
     * @author liuhao
     * @功能 修改用户信息
     */

    @Override
    public LsResponse updateUser(User user, User u) {
        log.info("{} is being executed. User = {}", "updateUser", JSON.toJSON(user.getUsername() + "准备修改ID为" + user.getId() + "的用户信息"));
        LsResponse lsResponse = new LsResponse();
        if (user != null) {
            try {
                user.setUpdateTime(Dates.now());
                userMapper.updateByPrimaryKeySelective(user);
                UserLog userLog = new UserLog();
                userLog.setUserId(user.getId());
                userLog.setOperation(u.getUsername() + "修改了用户ID为" + user.getId() + "的用户");
                userLog.setOperationTime(Dates.now());
                userLog.setOperatorType((short) 3);
                userLog.setLoginId(String.valueOf(u.getId()));
                userLog.setUsername(user.getUsername().getBytes());
                userLogMapper.insertUserLog(userLog);
                log.info("{} is being executed. User = {}", "updateUser", JSON.toJSON(u.getUsername() + "修改了ID为" + user.getId() + "的用户信息"));
            } catch (Exception e) {
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("{} is being executed. User = {}", "updateUser", JSON.toJSON(e.toString()));
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.USER_NO_USER_ERR.name());
        }
        return lsResponse;
    }

    /**
     * @param id
     * @param user
     * @return
     * @author liuhao
     * @功能 删除用户
     */
    @Override
    public LsResponse deleteUser(int id, User user) {
        log.info("{} is being executed. User = {}", "deleteUser", JSON.toJSON(user.getUsername() + "想要删除ID为" + id + "的用户"));
        LsResponse lsResponse = new LsResponse();
        try {
            User u = new User();
            u.setId(id);
            u.setUpdateTime(Dates.now());
            u.setIsDelete(true);
            int count = userMapper.updateByPrimaryKeySelective(u);
            UserLog userLog = new UserLog();
            userLog.setUserId(id);
            userLog.setOperation(user.getUsername() + "删除了用户ID为" + id + "的用户");
            userLog.setOperationTime(Dates.now());
            userLog.setOperatorType((short) 3);
            userLog.setLoginId(String.valueOf(user.getId()));
            userLog.setUsername(u.getUsername().getBytes());
            userLogMapper.insertUserLog(userLog);
            log.info("{} is being executed. User = {}", "deleteUser", JSON.toJSON(user.getUsername() + "删除了ID为" + id + "的用户"));
            lsResponse.setAsSuccess();
            lsResponse.setMessage("修改成功");
            lsResponse.setData(count);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.USER_DELETE_ERR.name());
            log.error("{} is being executed. User = {}", "deleteUser", JSON.toJSON("删除用户异常" + e.toString()));
        }
        return lsResponse;
    }

    /**
     * @param user
     * @return
     * @author liuhao
     * @功能 获取用户列表
     */
    @Override
    public LsResponse getUsers(User user, Integer pageNum, Integer pageSize, String keyword) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;
        try {
            if (user != null) {
                //搜索关键词中文论码的处理
                if (keyword != null && !"".equals(keyword)) {
                    keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                    keyword = "%" + keyword + "%";
                } else {
                    keyword = "";
                }
                //分页处理
                if (pageSize != null && pageNum != null) {
                    offSet = (pageNum - 1) * pageSize;
                }
                //用户条数
                Integer total = userMapper.getUserCount(user, keyword);
                //用户列表信息
                List<User> users = userMapper.selectUsers(user, offSet, pageSize, keyword);
                if (users != null && users.size() > 0) {
                    lsResponse.setData(users);
                    lsResponse.setTotalCount(total);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.USER_NO_SELECT.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.USER_NO_TIME.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePwd(String data, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            //此处没有检验传入的参数是否为空,也没有判断user 是否为空
            JSONObject jsonObject = JSONObject.parseObject(data);
            String password = jsonObject.get("password").toString().trim();
            String oldPassword = jsonObject.get("oldPassword").toString().trim();
            if (user.getPassword().equals(ShiroKit.md5(oldPassword, user.getUsername()))) {
                log.info("{} is being executed. User = {}", "updatePassword", JSON.toJSON(user.getUsername()));
                user.setPassword(ShiroKit.md5(password, user.getUsername()));
                user.setUpdateTime(Dates.now());
                userMapper.updateUserPasswordById(user);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.USER_OLD_PASSWORD_ERR.name());
                return lsResponse;
            }
            lsResponse.setAsSuccess();
            lsResponse.setMessage("修改成功");
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse loginOut(String token) {
        LsResponse lsResponse = new LsResponse();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            redisManager.del(token.getBytes());
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse add(User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            String password = ShiroKit.md5(user.getPassword(), user.getUsername());
            user.setPassword(password);
            user.setCreateTime(Dates.now());
            user.setUpdateTime(Dates.now());
            user.setLockTime((short) 0);
            int r = userMapper.insertSelective(user);
            lsResponse.setData(r);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse isToken(String token) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (token != null && !"".equals(token)) {
                byte[] bytes = redisManager.get(token.getBytes());
                if (bytes == null) {
                    lsResponse.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.TOKEN_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

}
