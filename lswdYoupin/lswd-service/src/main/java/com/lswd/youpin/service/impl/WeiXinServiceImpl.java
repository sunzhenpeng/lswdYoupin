package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.commons.LoginType;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.TenantAssociatorMapper;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.WeiXinService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.shiro.kit.ShiroKit;
import com.lswd.youpin.shiro.token.CustomizedToken;
import com.lswd.youpin.shiro.token.TokenProcessor;
import com.lswd.youpin.utils.SerializeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2017/6/9.
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USER_LOGIN_TYPE = LoginType.WxUser.toString();
    @Autowired
    private TenantAssociatorMapper tenantAssociatorMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private RedisManager redisManager;
    @Override
    public LsResponse add(TenantAssociator tenantAssociator, User user) {
        log.info("{} is being executed. User = {}", "addTenantAssociator", JSON.toJSON(user.getUsername()
                + "准备注册新商家微信号用户其电话号码为") + tenantAssociator.getTelephone());
        LsResponse lsResponse = new LsResponse();
        try {
            TenantAssociator associator = tenantAssociatorMapper.selectTenantAssociatorByName(tenantAssociator.getAccount());
            if (associator == null) {
                tenantAssociator.setUpdateTime(Dates.now());
                tenantAssociator.setPassword(ShiroKit.md5(tenantAssociator.getPassword(), tenantAssociator.getAccount()));
                List<Canteen> canteens = tenantAssociator.getCanteenList();
                Integer id = tenantAssociatorMapper.selectTenantAssociatorLastId();
                if(id==null)
                {
                    id=0;
                }
                String associatorId = "t" + String.valueOf(1001 + id);
                tenantAssociator.setAssociatorId(associatorId);
                if (canteens != null && canteens.size() > 0) {
                    tenantAssociatorMapper.addAssociatorCanteen(canteens, tenantAssociator.getAssociatorId());
                }
                tenantAssociator.setTenantId(user.getTenantId());
                Integer b = tenantAssociatorMapper.insertTenantAssociator(tenantAssociator);
                if (b!=null&&b > 0) {
                    log.info("{} is being executed. User = {}", "addTenantAssociator", JSON.toJSON(user.getUsername()
                            + "追加了账户名为") + tenantAssociator.getAccount());
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_ADD_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_YE_ACCOUNT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteById(Integer id, User user) {
        log.info("{} is being executed. User = {}", "deleteTenantAssociator", JSON.toJSON(user.getUsername()
                + "准备删除id为" + id + "的商家微信号用户"));
        LsResponse lsResponse = new LsResponse();
        try {
            TenantAssociator tenantAssociator = new TenantAssociator();
            tenantAssociator.setId(id);
            tenantAssociator.setDelete(true);
            tenantAssociator.setUpdateTime(Dates.now());
            Integer b = tenantAssociatorMapper.deleteById(tenantAssociator);
            if (b!=null&&b > 0) {
                log.info("{} is being executed. User = {}", "deleteTenantAssociator", JSON.toJSON(user.getUsername()
                        + "删除了id为" + id + "的商家微信号用户"));
            } else {
                lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_DELETE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateTenantAssociator(User user, TenantAssociator tenantAssociator) {
        log.info("{} is being executed. User = {}", "updateTenantAssociator", JSON.toJSON(user.getUsername()
                + "准备修改id为" + user.getId() + "的商家微信号用户信息"));
        LsResponse lsResponse = new LsResponse();
        tenantAssociator.setUpdateTime(Dates.now());
        try {
            Integer b = tenantAssociatorMapper.updateById(tenantAssociator);
            tenantAssociatorMapper.deleteAssociatorCanteens(tenantAssociator.getAssociatorId());
            List<Canteen>canteens=tenantAssociator.getCanteenList();
            if(canteens!=null&&canteens.size()>0)
            {
                tenantAssociatorMapper.addAssociatorCanteen(canteens,tenantAssociator.getAssociatorId());
            }
            if (b!=null&&b > 0) {
                log.info("{} is being executed. User = {}", "updateTenantAssociator", JSON.toJSON(user.getUsername()
                        + "修改了id为" + user.getId() + "的商家微信号用户信息"));
            } else {
                lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_DELETE_ERR.name());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getTenantAssociatorList(String keyword, Integer pageNum, Integer pageSize,String tenantId) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;

        try {
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (keyword != null && !"".equals(keyword)) {
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            Integer total = tenantAssociatorMapper.getTenantAssociatorCount(keyword,tenantId);
            List<TenantAssociator> tenantAssociators = tenantAssociatorMapper.tenantAssociators(offSet, pageSize, keyword,tenantId);
            if(tenantAssociators!=null&&tenantAssociators.size()>0){
                List<TenantAssociator>tenantAssociatorList=new ArrayList<>();
                for(TenantAssociator associator:tenantAssociators)
                {
                    Integer canteenCount = tenantAssociatorMapper.selectCanteenCount(associator.getAssociatorId());
                    if (canteenCount == null) {
                        canteenCount = 0;
                    }
                    associator.setCanteenCount(canteenCount);
                    tenantAssociatorList.add(associator);
                }
                lsResponse.setData(tenantAssociatorList);
                lsResponse.setTotalCount(total);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_NO_SELECT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookTenantAssociator(Integer id) {
        LsResponse lsResponse=new LsResponse();
        try {
            TenantAssociator tenantAssociator= tenantAssociatorMapper.selectTenantAssociatorById(id);
            if(tenantAssociator!=null)
            {
                List<Canteen>canteenList=tenantAssociatorMapper.selectCanteens(tenantAssociator.getAssociatorId());
                tenantAssociator.setCanteenList(canteenList);
                lsResponse.setSuccess(true);
                lsResponse.setData(tenantAssociator);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }
    @Override
    public LsResponse login(TenantAssociator associator) {
        LsResponse lsResponse=new LsResponse();
        if (associator != null) {
            try {
                if (associator.getAccount() != null && !associator.getAccount().equals("")) {
                    CustomizedToken customizedToken = new CustomizedToken(associator.getAccount(), associator.getPassword(), USER_LOGIN_TYPE);
                    customizedToken.setRememberMe(true);
                    Subject subject = SecurityUtils.getSubject();
                    subject.login(customizedToken);
                    TenantAssociator tenantAssociator = (TenantAssociator) subject.getPrincipal();
                    String access_token = TokenProcessor.getInstance().generateToken(tenantAssociator.getAccount(), true);//生成token
                    redisManager.set(access_token.getBytes(), SerializeUtils.serialize(tenantAssociator), ConstantsCode.SESSION_EXPIRE);//向redis存储数据
                    lsResponse.setData(access_token);//返回token值
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_ACCOUNT.name());
                }
            } catch (UnknownAccountException e) {
                log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_PASSWORD.name());
            } catch (IncorrectCredentialsException e) {
                log.error("用户或密码错误：{}", CodeMessage.USER_LOGIN_NO.name());
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_PASSWORD.name());
            } catch (LockedAccountException e) {
                log.error("账号被锁定：{}", CodeMessage.USER_TIME_ERR.name());
            } catch (Exception e) {
                lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
                log.error(e.toString());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_CAN.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookCanteen(String associatorId) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(associatorId!=null&&!"".equals(associatorId))
            {
                List<Canteen>canteens= tenantAssociatorMapper.selectCanteens(associatorId);
                if(canteens!=null&&canteens.size()>0)
                {
                    lsResponse.setData(canteens);
                }else{
                   lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_NO_CANTEEN.name());
                }
            }else{
                lsResponse.checkSuccess(false,CodeMessage.TENANTASSOCIATOR_NO_CAN.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse selectByAccount(String account) {
        LsResponse lsResponse=new LsResponse();
        TenantAssociator tenantAssociator= tenantAssociatorMapper.selectTenantAssociatorByName(account);
        lsResponse.setData(tenantAssociator);
        return lsResponse;
    }

    @Override
    public LsResponse updatePasword(String data, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        if (data != null && !"".equals(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
            String oldPassword = jsonObject.get("oldPassword").toString();
            String password = jsonObject.get("password").toString();
            String code = jsonObject.get("code").toString();//获取短信验证码值
            TenantAssociator associator = tenantAssociatorMapper.selectTenantAssociatorById(tenantAssociator.getId());
            String telephone = associator.getTelephone();
            if (redisManager.get(telephone.getBytes()) != null) {
                String smsCode = new String(redisManager.get(telephone.getBytes())).split(",")[0];
                if (smsCode.equals(code)) {
                    if (ShiroKit.md5(oldPassword, associator.getAccount()).equals(associator.getPassword())) {
                        associator.setPassword(ShiroKit.md5(password, associator.getAccount()));
                        Integer b = tenantAssociatorMapper.updateByPrimaryKey(associator);
                        if (b!=null&&b > 0) {
                            lsResponse.setMessage(CodeMessage.TENANTASSOCIATOR_UPDATE_SUCCESS.name());
                        } else {
                            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_UPDATE_ERR.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_PASSWORD_ERR.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_SMS_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_SMS_ERR.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_CAN.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePhone(String data, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        if (data != null && !"".equals(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
            String tel = jsonObject.get("tel").toString();
            String password = jsonObject.get("password").toString();
            String code = jsonObject.get("code").toString();//获取短信验证码值
            String account = jsonObject.get("account").toString();//获取会员账号
            if (redisManager.get(tel.getBytes()) != null) {
                String smsCode = new String(redisManager.get(tel.getBytes())).split(",")[0];
                if (smsCode.equals(code)) {
                    TenantAssociator associator = tenantAssociatorMapper.selectTenantAssociatorByName(account);
                    if (associator != null) {
                        if (ShiroKit.md5(password, associator.getAccount()).equals(associator.getPassword())) {
                            associator.setTelephone(tel);
                            Integer b = tenantAssociatorMapper.updateByPrimaryKey(associator);
                            if (b!=null&&b > 0) {
                                lsResponse.setMessage(CodeMessage.TENANTASSOCIATOR_UPDATE_SUCCESS.name());
                            } else {
                                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_UPDATE_ERR.name());
                            }
                        } else {
                            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_PASSWORD_ERR.name());
                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_ACCOUNT.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_SMS_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_SMS_ERR.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_CAN.name());
        }
        return lsResponse;
    }

    @Override
    public TenantAssociator getName(String name) {
        return tenantAssociatorMapper.selectTenantAssociatorByName(name);
    }
}