package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.TenantThin;
import com.lswd.youpin.model.Tenant;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.TenantService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liruilong on 2017/6/8.
 */
@Component
public class TenantThinImpl implements TenantThin {
    @Autowired
    private TenantService tenantService;

}
