package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.util.Verifycode;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.commons.LoginType;
import com.lswd.youpin.dao.AssociatorCanteenMapper;
import com.lswd.youpin.dao.AssociatorMapper;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.lsyp.MembersMapper;
import com.lswd.youpin.model.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.shiro.kit.ShiroKit;
import com.lswd.youpin.shiro.token.CustomizedToken;
import com.lswd.youpin.shiro.token.TokenProcessor;
import com.lswd.youpin.utils.SerializeUtils;
import com.lswd.youpin.utils.StringsUtil;
import com.lswd.youpin.wxpay.WxApi.WxPayApi;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/6/6.
 */
@Service
public class AssociatorServiceImpl implements AssociatorService {
    private final Logger log = LoggerFactory.getLogger(AssociatorServiceImpl.class);
    private static final String USER_LOGIN_TYPE = LoginType.ASSOCIATOR.toString();
    @Value("${user.lockTime}")//用户默认密码
    private String lockTime;
    @Value("${password.yan}")//会员密码加盐
    private String passwordYan;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private AssociatorMapper associatorMapper;

    @Autowired
    private CanteenMapper canteenMapper;

    @Autowired
    private MembersMapper membersMapper;

    @Autowired
    private AssociatorCanteenMapper associatorCanteenMapper;

    @Override
    public LsResponse registerAssociator(String data) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (data != null && !"".equals(data)) { //判断参数是否为空
                JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
                String telephone = jsonObject.get("telephone").toString();//获取注册电话号码
                String password = jsonObject.get("password").toString();//获取密码
//                String picvertical = jsonObject.get("picvertical").toString();//获取图片验证码值
                String msgVertical = jsonObject.get("msgVertical").toString();//获取短信验证码值
//                String uuid = jsonObject.get("uuid").toString();//获取图片验证码的uuid
//                if (redisManager.get(uuid.getBytes()) != null) {
//                    if (uuid != null && picvertical.equals(new String(redisManager.get(uuid.getBytes())))) {  //判断图片验证码是否正确
                if (telephone != null) { //判断用户手机号是否不为空
                    // 判断手机号是否被注册过
                    Associator target = associatorMapper.selectByAccount(telephone);
                    if (target != null) {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_PHONE.name());
                        return lsResponse;
                    }
                    if (redisManager.get(telephone.getBytes()) != null) {
                        String codes = new String(redisManager.get(telephone.getBytes()));//获取redis中短信验证码键值对
                        String code = codes.split(",")[0];//获取短信验证码
                        if (code.equals(msgVertical)) {//判断短信验证码是否正确
                            Associator a = associatorMapper.selectByAccount(telephone);//查找判断该手机号是否被注册
                            if (a == null) {
                                Associator associator = new Associator();
                                Integer id = associatorMapper.selectLastAssociatorId();//查找数据库表中最后一条记录的ID值
                                if (id == null) {
                                    id = 0;
                                }
                                associator.setAssociatorId(String.valueOf(10000001 + id));//设置会员编号，其规则为1000001+自增id
                                associator.setCreateTime(Dates.now());
                                associator.setUpdateTime(Dates.now());
                                associator.setIsUse(true);
                                associator.setIsDelete(false);
                                associator.setPassword(ShiroKit.md5(password, passwordYan));
                                String payPassword = new String(Verifycode.generateRandomArray(6));
                                associator.setPayPassword(ShiroKit.md5(payPassword, passwordYan));
                                associator.setAccount(telephone);
                                associator.setTelephone(telephone);
                                log.info("{} is being executed. User = {}", "addAssociator", JSON.toJSON("注册新的微信会员") + associator.getAccount());
                                Integer b = associatorMapper.addAssociator(associator);//添加会员信息
                                if (b != null && b > 0) {
                                    log.info("{} is being executed. User = {}", "addAssociator", JSON.toJSON("新的微信会员") + associator.getAccount() + "注册成功");
                                    lsResponse.checkSuccess(true, CodeMessage.ASSOCIATOR_ADD_SUCCESS.name());
                                } else {
                                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_ADD_ERR.name());
                                }
                            } else {
                                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_PHONE.name());
                            }
                        } else {
                            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_PHONE.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse login(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (associator != null) {
                String password = associator.getPassword();
                String account = associator.getAccount();
                CustomizedToken customizedToken = new CustomizedToken(account, password, USER_LOGIN_TYPE);
                customizedToken.setRememberMe(true);
                Subject subject = SecurityUtils.getSubject();
                subject.login(customizedToken);
                Associator a = (Associator) subject.getPrincipal();
                if (a.getIsUse()) {
                    String access_token = TokenProcessor.getInstance().generateToken(a.getAccount(), true);//生成token
                    redisManager.set(access_token.getBytes(), SerializeUtils.serialize(a), ConstantsCode.SESSION_EXPIRE);//向redis存储数据
                    String flag=(a.getMemberId().equals("0")?"0":"1");
                    Map<String,String> map=new HashMap<>();
                    map.put("token",access_token);
                    map.put("flag",flag);
                    lsResponse.setData(map);//返回token值
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOCK.name());
                }
            }
        } catch (UnknownAccountException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_ACCOUNT.name());
        } catch (IncorrectCredentialsException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_ERR.name());
        } catch (LockedAccountException e) {
            log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOCK.name());
        } catch (ExcessiveAttemptsException e) {
            log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            lsResponse.checkSuccess(false, CodeMessage.USER_TIME_ERR.name());
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lock(Integer id, Boolean lock) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator a = new Associator();
            a.setId(id);
            if (lock) {
                a.setIsUse(true);
            } else {
                a.setIsUse(false);
            }
            Integer b = associatorMapper.updateLockById(a);
            if (b != null && b > 0) {
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_LOCK_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse canteenList(String associatorId, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            String[] canteenIds = user.getCanteenIds().split(",");
            List<Canteen> canteenList = canteenMapper.selectCanteenByAid(associatorId, user.getUserType(), canteenIds);
            if (canteenList != null && canteenList.size() > 0) {
                lsResponse.setData(canteenList);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_CANTEEN.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    /*
    删除功能有待完善，还不行目前
     */
    @Override
    public LsResponse deleteAssociator(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator a = new Associator();
            a.setId(id);
            a.setIsDelete(true);
            Integer b = associatorMapper.deleteById(a);
            if (b != null && b > 0) {
                lsResponse.setMessage(CodeMessage.ASSOCIATOR_DELETE_SUCCESS.name());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ADDRESS_DELETE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAssociator(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            //修改会员性别
            if (associator.getSex() != null && !"".equals(associator.getSex())) {
                if ("男".equals(associator.getSex())) {
                    associator.setSexType(true);
                } else {
                    associator.setSexType(false);
                }
            }
            //修改会员账号
            if (associator.getAccount() != null && !"".equals(associator.getAccount())) {
                Associator a = associatorMapper.selectByAccount(associator.getAccount());
                if (a != null) {
                    lsResponse.setSuccess(false);
                    lsResponse.setMessage("该账号已存在");
                    return lsResponse;
                }
            }
            associator.setUpdateTime(Dates.now());
            Integer b = associatorMapper.updateByPrimaryKeySelective(associator);
            if (b != null && b > 0) {
                lsResponse.setMessage(CodeMessage.ASSOCIATOR_UPDATE_SUCCESS.name());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_UPDATE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAssociatorList(String keyword, Integer pageNum, Integer pageSize, User user, String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            keyword= StringsUtil.encodingChange(keyword);
            List<String> canteenIds = new ArrayList<>();
            if (canteenId == null || "".equals(canteenId)) {
                String[] canteens = user.getCanteenIds().split(",");
                for (String can : canteens) {
                    canteenIds.add(can);
                }
            } else {
                canteenIds.add(canteenId);
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = associatorMapper.selectAssociatorCount(keyword, user, canteenIds);
            List<Associator> associatorList = associatorMapper.selectAssociatorList(keyword, pageSize, offSet, user, canteenIds);
            if (associatorList != null && associatorList.size() > 0) {
                List<Associator> associators = new ArrayList<>();
                for (Associator associator : associatorList) {
                    if (associator.getAssociatorAccount() == null) {
                        associator.setAssociatorAccount(new AssociatorAccount());
                    }
                    associators.add(associator);
                }
                lsResponse.setData(associators);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage(String.valueOf(associatorList.size()));
            } else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage("没有查到会员信息");
            }
        } catch (UnsupportedEncodingException e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookAssociator(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator associator = associatorMapper.selectAssociatorById(id);
            if (associator != null) {
                lsResponse.setData(associator);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_ACCOUNT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse bindCanteen(String associatorId, String canteenId) {
        LsResponse lsResponse = new LsResponse();
        AssociatorCanteen associatorCanteen = new AssociatorCanteen();
        associatorCanteen.setAssociatorId(associatorId);
        associatorCanteen.setCanteenId(canteenId);
        associatorCanteen.setBindTime(Dates.now());
        Integer b = associatorCanteenMapper.insertSelective(associatorCanteen);
        if (b != null && b > 0) {
            lsResponse.checkSuccess(true, CodeMessage.ASSOCIATOR_BIND_SUCCESS.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_BIND_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAssociatorByAssociatorId(String associatorId) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator associator = associatorMapper.getAssociatorByAssociatorId(associatorId);
            if (associator != null) {
                lsResponse.setData(associator);
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("收货人查询失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAssociatorByAccount(String account) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator associator = associatorMapper.selectByAccount(account);
            lsResponse.setData(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPay(String associatorId, String keyword, Integer pageSize, Integer pageNum) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;
        try {
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            Integer total = associatorMapper.getPlayLogCount(associatorId, keyword);
            List<RechargeLog> rechargeLogs = associatorMapper.getPayLog(associatorId, offSet, pageSize, keyword);
            if (rechargeLogs != null && rechargeLogs.size() > 0) {
                lsResponse.setData(rechargeLogs);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.setMessage("没有充值记录");
                lsResponse.setSuccess(false);
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse getAssociatorById(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            Associator a = associatorMapper.selectById(associator);
            if (a != null) {
                if (a.getAssociatorWx() == null || "".equals(a.getAssociatorWx())) {
                    a.setAssociatorWx("");
                }
                lsResponse.setData(a);
            } else {
                lsResponse.setMessage("请重新登录");
                lsResponse.setSuccess(false);
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePassword(String data) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (data != null && !"".equals(data)) {
                JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
                String tel = jsonObject.get("telephone").toString();
                String password = jsonObject.get("password").toString();
//                String picvertical = jsonObject.get("picvertical").toString();//获取图片验证码值
                String msgVertical = jsonObject.get("msgVertical").toString();//获取短信验证码值
//                String uuid = jsonObject.get("uuid").toString();//获取图片验证码的uuid
//                if (redisManager.get(uuid.getBytes()) != null && picvertical.equals(new String(redisManager.get(uuid.getBytes())))) {
                Associator associator = associatorMapper.selectByAccount(tel);
                if (associator != null) {
                    String codes = new String(redisManager.get(tel.getBytes()));//获取redis中短信验证码键值对
                    if (codes != null) {
                        String code = codes.split(",")[0];
                        if (msgVertical.equals(code)) {
                            associator.setPassword(ShiroKit.md5(password, passwordYan));
                            Integer b = associatorMapper.updateByPrimaryKeySelective(associator);
                            if (b != null && b > 0) {
                                lsResponse.setMessage(CodeMessage.ASSOCIATOR_UPDATE_PASSWORD_SUCCESS.name());
                            } else {
                                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_UPDATE_PASSWORD_ERR.name());
                            }
                        } else {
                            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_PHONE_ERR.name());
                }
//                } else {
//                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_IMAGE_ERR.name());
//                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateLoginPwd(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
        String tel = associator.getTelephone();
        String password = jsonObject.get("password").toString();
        String oldPassword = jsonObject.get("oldPassword").toString();//获取图片验证码值
        String msgVertical = jsonObject.get("msgVertical").toString();//获取短信验证码值
        Associator a = associatorMapper.selectByAccount(associator.getAccount());
        if (a != null) {
            if (associator.getPassword().equals(ShiroKit.md5(oldPassword, passwordYan))) {
                if (redisManager.get(tel.getBytes()) != null) {
                    if (msgVertical.equals(new String(redisManager.get(tel.getBytes())).split(",")[0])) {
                        a.setPassword(ShiroKit.md5(password, passwordYan));
                        a.setPayPassword(null);
                        associatorMapper.updateByPrimaryKeySelective(a);
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_PASSWORD.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOGIN.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePayPwd(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
        String tel = associator.getTelephone();
        String password = jsonObject.get("password").toString();
        String oldPassword = jsonObject.get("oldPassword").toString();//获取密码
        String msgVertical = jsonObject.get("msgVertical").toString();//获取短信验证码值
        Associator a = associatorMapper.selectByAccount(associator.getAccount());
        if (a != null) {
            if (associator.getPayPassword().equals(ShiroKit.md5(oldPassword, passwordYan))) {
                if (redisManager.get(tel.getBytes()) != null) {
                    if (msgVertical.equals(new String(redisManager.get(tel.getBytes())).split(",")[0])) {
                        a.setPayPassword(ShiroKit.md5(password, passwordYan));
                        a.setPassword(null);
                        associatorMapper.updateByPrimaryKeySelective(a);
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_PASSWORD.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOGIN.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePhone(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        String phone = associator.getTelephone();
        JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
        String tel = jsonObject.get("tel").toString();
        String password = jsonObject.get("password").toString();
        String msgVertical = jsonObject.get("msgVertical").toString();//获取短信验证码值
        Associator a = associatorMapper.selectByAccount(phone);
        if (a != null) {
            if (a.getPassword().equals(ShiroKit.md5(password, passwordYan))) {
                if (redisManager.get(tel.getBytes()) != null) {
                    if (msgVertical.equals(new String(redisManager.get(tel.getBytes())).split(",")[0])) {
                        if (associatorMapper.selectByAccount(tel) == null) {
                            Associator as = new Associator();
                            as.setTelephone(tel);
                            as.setId(a.getId());
                            as.setAssociatorId(a.getAssociatorId());
                            associatorMapper.updateByPrimaryKeySelective(as);
                        } else {
                            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_PHONE.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_PASSWORD.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOGIN.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMoney(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        Double price = associatorMapper.selectMoney(associator);
        if (price == null) {
            price = 0d;
        }
        lsResponse.setData(price);
        return lsResponse;
    }

    @Override
    public LsResponse deleteCanteen(String canteenId, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        Integer b = associatorMapper.deleteCanteen(canteenId, associator.getAssociatorId());
        if (b != null && b > 0) {
            lsResponse.checkSuccess(true, CodeMessage.ASSOCIATOR_YE_CANTEEN_BIND.name());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_CANTEEN_BIND.name());
        }
        return lsResponse;
    }

    @Override
    public Integer updateBindCanteen(AssociatorCanteen associatorCanteen) {
        return associatorCanteenMapper.updateCanttenBindTime(associatorCanteen);
    }


    @Override
    public LsResponse addPhone(String tel, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        if (tel != null && !"".equals(tel)) {
            System.out.println(associator.getTelephone() + "------------" + associator.getAssociatorWx());
            JSONObject jsonObject = JSONObject.parseObject(tel);//解析json参数
            String telephone = jsonObject.get("tel").toString();
            System.out.println(telephone + "------------");
            associator.setTelephone(telephone);
            Integer b = associatorMapper.updateByPrimaryKeySelective(associator);
            if (b != null && b > 0) {
                lsResponse.checkSuccess(true, CodeMessage.ADDRESS_ADD_SUCCESS.name());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_ADD_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse add(WeixinOauth2Token wat, String associatorId) {
        LsResponse lsResponse = new LsResponse();
        boolean flag = true;
        try {
            if (wat != null) {
                Associator associator = associatorMapper.getByOpenId(wat.getOpenId());
                if (associator == null) {
                    SNSUserInfo userInfo = WxPayApi.getSNSUserInfo(wat.getAccessToken(), wat.getOpenId());
                    if (userInfo != null) {
                        associator = new Associator();
                        Integer maxId = associatorMapper.selectLastAssociatorId();
                        if (maxId == null) {
                            maxId = 0;
                        }
                        associator.setAssociatorId(String.valueOf(10000001 + maxId));
                        associator.setAccount(String.valueOf(10000001 + maxId));
                        associator.setAssociatorWx(wat.getOpenId());
                        associator.setPassword(ShiroKit.md5("123456", passwordYan));
                        associator.setPayPassword(ShiroKit.md5("123456", passwordYan));
                        associator.setNickName(userInfo.getNickname());
                        if (userInfo.getSex() == 0) {
                            associator.setSexType(false);
                        } else {
                            associator.setSexType(true);
                        }
                        associator.setTelephone("12345678901");
                        associator.setImg(userInfo.getHeadImgUrl());
                        associator.setCreateTime(Dates.now());
                        associator.setUpdateTime(Dates.now());
                        Integer b = associatorMapper.insertSelective(associator);
                        if (b == null || b == 0) {
                            flag = false;
                        }
                    }

                }
                if (flag) {
                    CustomizedToken customizedToken = new CustomizedToken(associator.getAccount(), "123456", USER_LOGIN_TYPE);
                    customizedToken.setRememberMe(true);
                    Subject subject = SecurityUtils.getSubject();
                    subject.login(customizedToken);
                    Associator a = (Associator) subject.getPrincipal();
                    if (a.getIsUse()) {
                        if (associatorId != null && !"".equals(associatorId) && !associatorId.equals(a.getAssociatorId())) {
                            a.setShare(share(a.getAssociatorId(), associatorId));
                        }
                        String access_token = TokenProcessor.getInstance().generateToken(a.getAccount(), true);//生成token
                        redisManager.set(access_token.getBytes(), SerializeUtils.serialize(a), ConstantsCode.SESSION_EXPIRE);//向redis存储数据
                        Map<String, Object> map = new HashedMap();
                        map.put("access_token", access_token);
                        map.put("userInfo", associator);
                        lsResponse.setData(map);//返回token值
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOCK.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_ADD_ERR.name());
                }
            } else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage("获取openId失败");
            }
        } catch (UnknownAccountException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_ACCOUNT.name());
        } catch (IncorrectCredentialsException e) {
            log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_ERR.name());
        } catch (LockedAccountException e) {
            log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOCK.name());
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public Integer updateById(Integer id, String memberId) {
        try {
            int count = associatorMapper.updateById(id, memberId);
            return count;
        } catch (Exception e) {
            log.error("更新会员信息失败=={}", e.getMessage());
        }
        return 0;
    }

    @Override
    public LsResponse getBalance(String memberId) {
        log.info("根据会员绑定的memberId获取餐卡余额======memberId======" + memberId);
        LsResponse lsResponse = new LsResponse();
        if (memberId == null || memberId == "") {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_CARD.name());
            return lsResponse;
        }
        try {
            Float balance = membersMapper.getBalance(memberId);
            lsResponse.setData(balance);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            log.error("查询餐卡余额信息失败====={}", e.getMessage());
            lsResponse.checkSuccess(false, "余额查询失败");
        }
        return lsResponse;
    }

    private AssociatorShare share(String associatorId, String shareId) {
        AssociatorShare share;
        try {
            share = associatorMapper.selectShare(associatorId, shareId);
            if (share == null) {
                share = new AssociatorShare();
                share.setAssociatorId(associatorId);
                share.setCount(1);
                share.setShareId(shareId);
                associatorMapper.insertShare(share);
            } else {
                System.out.println(share.getCount() + "----------------------" + share.getId());
                share.setCount(share.getCount() + 1);
                System.out.println(share.getCount() + "----------------------");
                associatorMapper.updateShare(share);
            }
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
        return share;
    }


}

