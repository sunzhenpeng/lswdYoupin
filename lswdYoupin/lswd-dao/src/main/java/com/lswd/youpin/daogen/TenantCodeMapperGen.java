package com.lswd.youpin.daogen;

import com.lswd.youpin.model.TenantCode;

public interface TenantCodeMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    int insert(TenantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    int insertSelective(TenantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    TenantCode selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TenantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_tenant_codes
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TenantCode record);
}