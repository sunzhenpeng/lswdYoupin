package com.lswd.youpin.service;

import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/9.
 */
public interface WeiXinService {
    LsResponse add(TenantAssociator tenantAssociator, User user);
    LsResponse deleteById(Integer id, User user);

    LsResponse updateTenantAssociator(User user, TenantAssociator tenantAssociator);

    LsResponse getTenantAssociatorList(String keyword, Integer pageNum, Integer pageSize,String tenantId);

    LsResponse lookTenantAssociator(Integer id);

    LsResponse login(TenantAssociator associator);

    LsResponse lookCanteen(String associatorId);

    LsResponse selectByAccount(String account);

    LsResponse updatePasword(String data, TenantAssociator tenantAssociator);

    LsResponse updatePhone(String data, TenantAssociator tenantAssociator);

    TenantAssociator getName(String name);
}
