package com.lswd.youpin.service;

import com.lswd.youpin.model.Tenant;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by liruilong on 2017/6/7.
 */
public interface TenantService {
    LsResponse getTenantByTenantId(String tenantId);

    LsResponse addOrUpdateTenant(Tenant tenant,User u);

    LsResponse deleteTenant(Integer id);

    LsResponse getAllTenants(String tenantId,String keyword,Integer pageNum,Integer pageSize) throws UnsupportedEncodingException;

    List<Tenant> getTenantAll();


}
