package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.util.PropertiesUtils;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.commons.LoginType;
import com.lswd.youpin.dao.CounterUserMapper;
import com.lswd.youpin.dao.lsyp.CounterMapper;
import com.lswd.youpin.dao.lsyp.CounterUserLinkedMapper;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.model.lsyp.CounterUserLinked;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterUserService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.shiro.kit.ShiroKit;
import com.lswd.youpin.shiro.token.CustomizedToken;
import com.lswd.youpin.shiro.token.TokenProcessor;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.MD5Utils;
import com.lswd.youpin.utils.SerializeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenguanqi on 2017/11/29.
 */
@Service
public class CounterUserServiceImpl implements CounterUserService {
    private static final String COUNTERUSER_LOGIN_TYPE = LoginType.CounterUser.toString();
    private final Logger logger = LoggerFactory.getLogger(CounterUserServiceImpl.class);

    @Autowired
    private CounterUserMapper counterUserMapper;
    @Autowired
    private CounterUserLinkedMapper counterUserLinkedMapper;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private CounterMapper counterMapper;

    @Override
    public LsResponse getCounterUserList(String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        Integer offSet = 0;
        try {
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (!("").equals(keyword) && keyword != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("查看吧台用户列表失败，失败原因为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            total = counterUserMapper.getCounterUserListCount(keyword);
            List<CounterUser> counterUsers = counterUserMapper.getCounterUserList(keyword, offSet, pageSize);
            if (counterUsers.size() > 0 && counterUsers != null) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(counterUsers);
            } else {
                lsResponse.setAsFailure();
                if (!("").equals(keyword) && keyword != null) {
                    lsResponse.setMessage(CodeMessage.EMPTY_DATA.name());
                }else {
                    lsResponse.setMessage("用户列表暂无数据");
                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("查看吧台用户列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterUser(CounterUser counterUser, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterUser.getPassword() == null) {
            return lsResponse.checkSuccess(false, "请输入密码");
        }
        if (counterUser.getConfirmPass() == null) {
            return lsResponse.checkSuccess(false, "请输入确认密码");
        }
        if (!counterUser.getPassword().equals(counterUser.getConfirmPass())) {
            return lsResponse.checkSuccess(false, "输入的密码两次不一致，请重新输入");
        }
        counterUser.setIsDelete(false);
        counterUser.setIsUse(true);
        counterUser.setStatus((short) 0);
        counterUser.setTenantId(user.getTenantId());//设置吧台用户商家编号
        counterUser.setCanteenIds(user.getCanteenIds());//设置吧台用户绑定的餐厅
        counterUser.setCreateUser(user.getUsername());//设置吧台用户创建人
        counterUser.setCreateTime(Dates.now());//设置吧台用户创建时间
        counterUser.setUpdateUser(user.getUsername());//设置吧台用户更新人
        counterUser.setUpdateTime(Dates.now());//设置吧台用户更新时间

        counterUser.setPassword(ShiroKit.md5(counterUser.getPassword(),"BT"));
        try {
            Integer maxId = counterUserMapper.getMAXID();
            if (maxId == null) maxId = 0;
            Integer userId = maxId + 100001;
            counterUser.setUserId("BTU" + userId);
            int insertFlag = counterUserMapper.insertSelective(counterUser);
            if (insertFlag > 0) {
                lsResponse.setMessage("吧台用户新增成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台用户新增失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台用户新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterUser(CounterUser counterUser, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterUser.getPassword() == null) {
            return lsResponse.checkSuccess(false, "请输入密码");
        }
        if (counterUser.getConfirmPass() == null) {
            return lsResponse.checkSuccess(false, "请输入确认密码");
        }
        if (!counterUser.getPassword().equals(counterUser.getConfirmPass())) {
            return lsResponse.checkSuccess(false, "输入的密码两次不一致，请重新输入");
        }
        counterUser.setPassword(ShiroKit.md5(counterUser.getPassword(),"BT"));
        counterUser.setUpdateUser(user.getUsername());
        counterUser.setUpdateTime(Dates.now());
        try {
            int updateFlag = counterUserMapper.updateByPrimaryKey(counterUser);
            if (updateFlag > 0) {
                lsResponse.setMessage("吧台用户修改成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台用户修改失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台用户修改失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterUser(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null){
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = counterUserMapper.deleteByPrimaryKeyUpdate(id);
            if (deleteFlag > 0) {
                lsResponse.setMessage("吧台用户删除成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台用户删除失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台用户删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterUserStatus(Integer id, Boolean status) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer statusFlag = 0;
        try {
            if (id == null){
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            if (status){
                statusFlag = 1;
            }
            counterUserMapper.updateCounterUserStatus(id,statusFlag);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("修改吧台用的状态失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse resetCounterUserPass(Integer id, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        String updateUser = user.getUsername();
        Date updateTime = Dates.now();
        try {
            if (id == null){
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            String pass = MD5Utils.MD5Encode("123456","BT");
            counterUserMapper.resetCounterUserPass(id,pass,updateUser,updateTime);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("重置用户的密码失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterUserPassBT(String passData, CounterUser counterUser) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (passData == null && passData.equals("")){
            return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        JSONObject json = JSONObject.parseObject(passData);
        String oldPass = json.get("oldPass").toString();
        counterUser = counterUserMapper.getCounterUserByUserId(counterUser.getUserId());
        if (!counterUser.getPassword().equals(ShiroKit.md5(oldPass,"BT"))){
            return lsResponse.checkSuccess(false,"原密码 不正确");
        }
        String newPass = json.get("newPass").toString();
        String confirmPass = json.get("confirmPass").toString();
        if (!newPass.equals(confirmPass)){
            return lsResponse.checkSuccess(false,"确认密码与新密码不符");
        }
        try {
            Integer update = counterUserMapper.updateCounterUserPass(counterUser.getUserId(),ShiroKit.md5(newPass,"BT"));
            if (update > 0){
                lsResponse.setMessage("修改成功");
            }else {
                lsResponse.checkSuccess(false,"密码修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse loginCounterUserBT(String userData, HttpServletRequest request) {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String,String> map = new HashMap<>();
        if (userData != null && !userData.equals("")){
            JSONObject counterUser = JSONObject.parseObject(userData);
            String username = counterUser.get("username").toString();
            String password = counterUser.get("password").toString();
            String dataSource = counterUser.get("dataSource").toString();
//            String MD5pass = ShiroKit.md5(password,"BT");
            try {
                CustomizedToken customizedToken = new CustomizedToken(username, password, COUNTERUSER_LOGIN_TYPE);//根据用户名、密码生成token
                customizedToken.setRememberMe(true);
                Subject subject = SecurityUtils.getSubject();
                try {
                    subject.login(customizedToken);
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                    lsResponse.checkSuccess(false,"错误次数过多，请歇会再试");
                }
                CounterUser user = (CounterUser) subject.getPrincipal();
//                CounterUser user = counterUserMapper.loginCounterUserBT(username,MD5pass);
                if (user == null){
                    return lsResponse.checkSuccess(false,"用户名或密码错误");
                }
                DataSourceHandle.setDataSourceType(dataSource);
                CounterUserLinked linked = counterUserLinkedMapper.getCounterUserByUserId(user.getUserId());
                if (linked == null){
                    return lsResponse.checkSuccess(false,"该用户暂未绑定吧台,请联系管理员");
                }
                Counter counter = counterMapper.getCounterByCounterId(linked.getCounterId());
                String mac = PropertiesUtils.getLocalHostMAC();
                if (user.getMac() != null && !user.getMac().equals("")){
                    if (!mac.equals(user.getMac())){
                        return lsResponse.checkSuccess(false,"MAC地址不符，请联系管理员");
                    }
                }
                String access_token = TokenProcessor.getInstance().generateToken(username, true);//生成token
                redisManager.set(access_token.getBytes(), SerializeUtils.serialize(user), ConstantsCode.SESSION_EXPIRE);//向redis存储数据
                request.getSession().setAttribute("counterUser",user);//session中保存吧台用户登陆信息
                map.put("token",access_token);
                map.put("username",user.getUsername());
                map.put("counterId",linked.getCounterId());
                map.put("counterName",counter.getCounterName());
                lsResponse.setData(map);
                lsResponse.setMessage("登陆成功");
            } catch (Exception e) {
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                logger.error("登陆失败，异常信息为：" + e.toString());
            }
        }else {
            lsResponse.checkSuccess(false,"数据为空");
        }
        return lsResponse;
    }

    @Override
    public CounterUser getCounterUserByUserId(String userId) {
        return counterUserMapper.getCounterUserByUserId(userId);
    }

    @Override
    public LsResponse getCounterUserByName(String username) {
        LsResponse lsResponse = LsResponse.newInstance();
        CounterUser counterUser = counterUserMapper.getCounterUserByName(username);
        lsResponse.setData(counterUser);
        return lsResponse;
    }
}
