package com.lswd.youpin.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.CounterUserMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Resources;
import com.lswd.youpin.service.*;
import com.lswd.youpin.shiro.token.CustomizedToken;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.DataSourceSwitch;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liruilong on 2017/6/19.
 */
public class UserDbRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(UserDbRealm.class);
    @Value("${user.lockTime}")//用户锁定次数
    private String defaultLockTime;
    @Value("${password.yan}")//会员密码加盐
    private String passwordYan;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private CounterUserService counterUserService;


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("给当前用户设置权限");
        List<String> roleSnList = new ArrayList<>();
        List<String> resStrList = new ArrayList<>();
        List<Resources> resources;
        //获取主体信息
        Object object = principalCollection.getPrimaryPrincipal();
        //获取realm 名称 ，根据名称判断登录身份
        Iterator<String> i = principalCollection.getRealmNames().iterator();
        //flag :0 用户登录，1：会员登录，2：商家用户登录
        String flag = "";
        SimpleAuthorizationInfo info;
        while (i.hasNext()) {
            flag = i.next().split(",")[1];
        }
        if (flag.equals("0")) {
            try {
                User user = (User) object;
                if (user != null) {
                    roleSnList.add(user.getRole().getRoleName());
                    resources = user.getRole().getResources();
                } else {
                    User u = (User) roleService.getRoleResourceByUserId(user.getId()).getData();
                    roleSnList.add(u.getRole().getRoleName());
                    resources = u.getRole().getResources();
                }
                for (Resources res : resources) {
                    resStrList.add(res.getUrl());
                }
                info = new SimpleAuthorizationInfo();
                info.setRoles(new HashSet<>(roleSnList));
                info.setStringPermissions(new HashSet<>(resStrList));
                return info;
            } catch (Exception e) {
                logger.info("用户认证失败={}", e.getMessage());
            }
        } else {
            roleSnList.add("admin");
            resStrList.add("/**");
            //获得经过认证的主体信息
            info = new SimpleAuthorizationInfo();
            info.setRoles(new HashSet<>(roleSnList));
            info.setStringPermissions(new HashSet<>(resStrList));
            return info;
        }
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("--- MyRealm doGetAuthenticationInfo ---");
        CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
        String loginType = customizedToken.getLoginType();
        String username = authenticationToken.getPrincipal().toString();
        try {
            if (loginType.equals("User")) {
                User u = userService.getUserByName(username);
                if (u != null) {
                    logger.info("商家后台用户登录：={}", JSON.toJSON(u));
                    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(u, u.getPassword(), getName());
                    info.setCredentialsSalt(ByteSource.Util.bytes(username.getBytes()));
                    return info;
                }
            } else if (loginType.equals("WxUser")) {
                TenantAssociator tenantAssociator = (TenantAssociator) weiXinService.selectByAccount(username).getData();
                if (tenantAssociator != null) {
                    logger.info("小程序用户登录：={}", JSON.toJSON(tenantAssociator));
                    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(tenantAssociator, tenantAssociator.getPassword(), getName());
                    info.setCredentialsSalt(ByteSource.Util.bytes(username.getBytes()));
                    return info;
                }
            } else if (loginType.equals("Associator")) {
                Associator associator = (Associator) associatorService.getAssociatorByAccount(username).getData();
                if (associator != null) {
                    logger.info("h5会员登录：={}", JSON.toJSON(associator));
                    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(associator, associator.getPassword(), getName());
                    info.setCredentialsSalt(ByteSource.Util.bytes(passwordYan.getBytes()));
                    return info;
                }
            } else if (loginType.equals("CounterUser")) {
                DataSourceHandle.setDataSourceType(ConstantsCode.LSWD_DB);
                CounterUser counterUser = (CounterUser) counterUserService.getCounterUserByName(username).getData();
                if (counterUser != null) {
                    logger.info("吧台用户登录：={}", JSON.toJSON(counterUser));
                    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(counterUser, counterUser.getPassword(), getName());
                    info.setCredentialsSalt(ByteSource.Util.bytes("BT".getBytes()));//设置密码加严方式，credentials是证书的意思
                    return info;
                }
            }
        } catch (Exception e) {
            logger.error("用户登录失败={}", e.getMessage());
        }

        return null;
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        Cache c = getAuthenticationCache();
        logger.info("清除【认证】缓存之前");
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        super.clearCachedAuthenticationInfo(principals);
        logger.info("调用父类清除【认证】缓存之后");
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }

        // 添加下面的代码清空【认证】的缓存
        User user = (User) principals.getPrimaryPrincipal();
        SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUsername(), getName());
        super.clearCachedAuthenticationInfo(spc);
        logger.info("添加了代码清除【认证】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【认证】缓存的大小:" + c.keys().size());
        if (cacheSize == 0) {
            logger.info("说明【认证】缓存被清空了。");
        }
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        logger.info("清除【授权】缓存之前");
        Cache c = getAuthorizationCache();
        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        super.clearCachedAuthorizationInfo(principals);
        logger.info("清除【授权】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【授权】缓存的大小:" + cacheSize);

        for (Object o : c.keys()) {
            logger.info(o + " , " + c.get(o));
        }
        if (cacheSize == 0) {
            logger.info("说明【授权】缓存被清空了。");
        }

    }
}
