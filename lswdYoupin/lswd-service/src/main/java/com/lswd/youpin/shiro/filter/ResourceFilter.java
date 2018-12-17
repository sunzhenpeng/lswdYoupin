package com.lswd.youpin.shiro.filter;

import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.TenantService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.*;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/22.
 */
public class ResourceFilter extends AccessControlFilter {
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private TenantService tenantService;

    private String errorUrl;

    private String successUrl;
    private static final Logger logger = LoggerFactory.getLogger(ResourceFilter.class);

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    /**
     * 表示是否允许访问 ，如果允许访问返回true，否则false；
     *
     * @param servletRequest
     * @param servletResponse
     * @param o               表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        //所有的请求必须带type标识字段 0：代表后台用户，1：代表小程序商家用户,2：代表H5 页面会员
        String loginType = req.getHeader(ConstantsCode.TYPE);
        Subject subject = null;
        String url = getPathWithinApplication(servletRequest);
        Subject.Builder b = new Subject.Builder();
        try {
            String token = req.getHeader(ConstantsCode.TOKEN);
            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            Object object = SerializeUtils.deserialize(redisManager.get(token.getBytes()));
            if (loginType.equals(ConstantsCode.ZERO)) {
                logger.info("商家后台用户授权认证");
                User value = (User) object;
                req.setAttribute(ConstantsCode.USER, value);
                principalCollection.add(value, value.getUsername() + "," + ConstantsCode.ZERO);
                b.principals(principalCollection);
                subject = b.buildSubject();
                DataSourceSwitch.dbSwitch(url, value.getTenantId());
            } else if (loginType.equals(ConstantsCode.ONE)) {
                logger.info("小程序用户授权认证");
                TenantAssociator value = (TenantAssociator) object;
                String tenantId = value.getTenantId();
                DataSourceSwitch.dbSwitch(url, value.getTenantId());
                req.setAttribute("tenantAssociator", value);
                principalCollection.add(value, value.getAccount() + "," + ConstantsCode.ONE);
                b.principals(principalCollection);
                subject = b.buildSubject();
            } else {
                Associator value = (Associator) object;
                principalCollection.add(value, value.getAccount() + "," + ConstantsCode.TWO);
                b.principals(principalCollection);
                subject = b.buildSubject();
                String canteenId = req.getHeader(ConstantsCode.CANTEEN_ID);
                if (canteenId != null) {
                    DataSourceHandle.setDataSourceType(canteenId.substring(0, 4));
                    value.setCanteenId(canteenId);
                } else {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                req.setAttribute("associator", value);
            }
        } catch (Exception e) {
            logger.info("抛出异常={}", e.getMessage());
        }
        return subject.isPermitted(url);
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        logger.debug("当 isAccessAllowed 返回 false 的时候，才会执行 method onAccessDenied ");
        HttpServletResponse rep = (HttpServletResponse) servletResponse;
        rep.setHeader("Access-Control-Allow-Origin", "*");
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,token");
        Map<String, String> map = new HashMap<>();
        map.put("msg", "当前用户没有权限");
        OutUtil.out(servletResponse, map);
        return Boolean.FALSE;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

}
