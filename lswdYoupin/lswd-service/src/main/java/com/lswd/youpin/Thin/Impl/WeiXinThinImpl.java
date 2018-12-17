package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.WeiXinThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.WeiXinService;
import com.lswd.youpin.service.impl.UserServiceImpl;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuhao on 2017/6/9.
 */
@Component
public class WeiXinThinImpl implements WeiXinThin {
    private final Logger log = LoggerFactory.getLogger(WeiXinThinImpl.class);
    @Autowired
    private WeiXinService weiXinService;

    @Override
    public LsResponse tenantAssociator(TenantAssociator tenantAssociator, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType("LSWD");
            lsResponse = weiXinService.add(tenantAssociator, user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("微信用户详情" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteTenantAssociator(Integer id, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType("LSWD");
            lsResponse = weiXinService.deleteById(id, user);
        } catch (Exception e) {
            log.error("删除微信用户: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateTenantAssociator(User user, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType("LSWD");
            lsResponse = weiXinService.updateTenantAssociator(user, tenantAssociator);
        } catch (Exception e) {
            log.error("修改微信用户: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getTenantAssociatorList(User user, String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        if (user != null) {
            try {
                DataSourceHandle.setDataSourceType("LSWD");
                if (keyword != null) {
                    keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                }
                String tenantId="";
                if(user.getUserType()){
                    tenantId=user.getTenantId();
                }
                lsResponse = weiXinService.getTenantAssociatorList(keyword, pageNum, pageSize,tenantId );
            } catch (Exception e) {
                log.error("微信用户列表: {}" + e.toString());
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.USER_NO_TIME.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookTenantAssociator(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType("LSWD");
            lsResponse = weiXinService.lookTenantAssociator(id);
        } catch (Exception e) {
            log.error("查看微信用户信息: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse login(TenantAssociator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = weiXinService.login(associator);
        } catch (Exception e) {
            log.error("微信用户登录: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookCanteen(String associatorId) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = weiXinService.lookCanteen(associatorId);
        } catch (Exception e) {
            log.error("微信用户绑定的餐厅: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePasword(String data, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = weiXinService.updatePasword(data, tenantAssociator);
        } catch (Exception e) {
            log.error("修改微信用户密码: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePhone(String data, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = weiXinService.updatePhone(data, tenantAssociator);
        } catch (Exception e) {
            log.error("修改绑定的手机: {}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }

        return lsResponse;
    }

    @Override
    public LsResponse getName(String name) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            TenantAssociator tenantAssociator = weiXinService.getName(name);
            if (tenantAssociator != null) {
                lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_YE_ACCOUNT.name());
            }
        } catch (Exception e) {
            log.error("查看用户名是否被注册");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}