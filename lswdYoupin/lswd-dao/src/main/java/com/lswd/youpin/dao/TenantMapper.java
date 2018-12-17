package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.TenantMapperGen;
import com.lswd.youpin.model.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuhao on 2017/6/14.
 */
public interface TenantMapper extends TenantMapperGen {

    Tenant getTenantByTenantId(@Param("tenantId") String tenantId);

    List<Tenant> getAllTenants(@Param("tenantId") String tenantId,@Param("name") String name,@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Integer getTotalCount(@Param("tenantName") String tenantName);
    Integer deleteById(Integer id);

    Integer getMaxId();

    List<Tenant> getTenantALL();
}