package com.lswd.youpin.Thin;

import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by SAMA on 2017/6/9.
 */
public interface WeiXinThin {
    LsResponse tenantAssociator(TenantAssociator tenantAssociator, User user);

    LsResponse deleteTenantAssociator(Integer id, User user);

    LsResponse updateTenantAssociator(User user, TenantAssociator tenantAssociator);

    LsResponse getTenantAssociatorList(User user,String keyword, Integer pageNum, Integer pageSize);

    LsResponse lookTenantAssociator(Integer id);

    LsResponse login(TenantAssociator associator);

    LsResponse lookCanteen(String associatorId);


    LsResponse updatePasword(String data, TenantAssociator tenantAssociator);

    LsResponse updatePhone(String data, TenantAssociator tenantAssociator);

    LsResponse getName(String name);
}
