package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.TenantAssociatorMapperGen;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.TenantAssociator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TenantAssociatorMapper extends TenantAssociatorMapperGen {

    Integer insertTenantAssociator(TenantAssociator tenantAssociator);
    Integer deleteById(TenantAssociator tenantAssociator);
    Integer updateById(TenantAssociator tenantAssociator);

    Integer getTenantAssociatorCount(@Param(value = "keyword") String keyword,@Param(value = "tenantId") String tenantId);

    List<TenantAssociator> tenantAssociators(@Param(value = "offSet") int offSet, @Param(value = "pageSize") Integer pageSize,
                                             @Param(value = "keyword") String keyword,@Param(value = "tenantId") String tenantId);

    TenantAssociator selectTenantAssociatorById(@Param(value = "id") Integer id);

    TenantAssociator  selectTenantAssociatorByName(@Param(value = "account") String account);

    Integer addAssociatorCanteen(@Param(value = "canteens") List<Canteen> canteens, @Param(value = "associatorId")String associatorId);

    Integer selectTenantAssociatorLastId();

    List<Canteen> selectCanteens(@Param(value = "associatorId") String associatorId);

    Integer deleteAssociatorCanteens(@Param(value = "associatorId") String associatorId);

    Integer selectCanteenCount(@Param(value = "associatorId") String associatorId);
}