package com.lswd.youpin.daogen;

import com.lswd.youpin.model.Tenant;

public interface TenantMapperGen {

    int deleteByPrimaryKey(Integer id);

    int insert(Tenant record);

    int insertSelective(Tenant record);

    Tenant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tenant record);

    int updateByPrimaryKey(Tenant record);
}