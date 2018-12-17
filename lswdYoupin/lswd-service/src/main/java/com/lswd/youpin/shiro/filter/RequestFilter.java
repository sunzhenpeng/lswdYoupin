package com.lswd.youpin.shiro.filter;

import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.DataSourceSwitch;
import com.lswd.youpin.utils.SerializeUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/12/12.
 */
public class RequestFilter extends AdviceFilter {
    @Autowired
    private RedisManager redisManager;

    private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("根据请求进行跨库操作");
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            String url = req.getRequestURI();
            String token = req.getHeader("token");
            if(token==null){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                return true;
            }else {
                //type表示不同的用户登陆；0：web端user 1：小程序，商家会员tenantAssociator 2：H5，会员登录Associator 3：吧台收银 counterUser登陆 4。APP管理员
                Integer type = Integer.parseInt(req.getHeader("type"));
                Object object = SerializeUtils.deserialize(redisManager.get(token.getBytes()));
                if (type == 0) {
                    User user = (User) object;
                    request.setAttribute("user", user);
                    DataSourceSwitch.dbSwitch(url, user.getTenantId());
                } else if (type == 1) {
                    TenantAssociator tenantAssociator = (TenantAssociator) object;
                    request.setAttribute("tenantAssociator", tenantAssociator);
                    DataSourceSwitch.dbSwitch(url, tenantAssociator.getTenantId());
                } else if (type == 2) {
                    Associator associator = (Associator) object;
                    String canteenId = req.getHeader(ConstantsCode.CANTEEN_ID);
                    if (canteenId != null) {
                        DataSourceHandle.setDataSourceType(canteenId.substring(0, 4));
                        associator.setCanteenId(canteenId);
                    } else {
                        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                    }
                    request.setAttribute("associator", associator);
                } else{
                    CounterUser counterUser = (CounterUser) object;
                    request.setAttribute("counterUser", counterUser);
                    DataSourceSwitch.dbSwitch(url, counterUser.getTenantId());
                }
            }
        } catch (Exception e) {
            log.info("token校验抛出异常=============：={}", e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
