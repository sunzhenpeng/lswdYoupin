package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.CanteenMapperGen;
import com.lswd.youpin.model.Canteen;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CanteenMapper extends CanteenMapperGen {
    List<Canteen> getAllCanteenByInstitutionId(@Param("institutionId") String institutionId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Canteen getCanteenByCanteenId(@Param("canteenId") String canteenId);

    Canteen getCanteenPart(@Param("canteenId") String canteenId);

    List<Canteen> selectCanteenByAid(@Param(value = "associatorId") String associatorId, @Param(value = "userType") boolean userType,
                                     @Param(value = "canteenIds") String[] canteenIds);

    Integer getTotalCount(@Param("canteenIds") String[] canteenIds, @Param("institutionId") String institutionId, @Param("canteenName") String canteenName);

    List<Canteen> getCanteenList(@Param("canteenIds") String[] canteenIds, @Param("institutionId") String institutionId, @Param("canteenName") String canteenName, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Canteen> getCanteenByInstitutionId(@Param("institutionId") String institutionId);

    List<Canteen> getCanteenByLocation(@Param("longitude") Double longitude, @Param("latitude") Double latitude);

    List<Canteen> getUserCanteenList(@Param("canteenIds") String[] canteenIds);

    List<Canteen> getCanteenPartList(@Param("canteenIds") String[] canteenIds, @Param("tenantId") String tenantId, @Param("institutionId") String institutionId, @Param("canteenName") String canteenName, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Canteen> getCanteenListByCanteenName(@Param("canteenName") String canteenName);

    List<Canteen> getAssociatorCanteenList(@Param("associatorId") String associatorId);

    List<Canteen> getTenantAssociatorCanteenList(@Param("associatorId") String associatorId);

    Integer getTotalCountWithinTenant(@Param("canteenIds") String[] canteenIds, @Param("tenantId") String tenantId, @Param("institutionId") String institutionId, @Param("canteenName") String canteenName);

    Integer getMaxId();

    Canteen getNearestCanteen(String associatorId);

    boolean getPayType(@Param("canteenId") String canteenId);

    String getCanteenIdByResTypeId(@Param("resTypeId") Integer resTypeId);

    String getCanteenIdByPageId(@Param("pageId") Integer pageId);

    List<Map<String,Object>> getCanteensByCanteenIds(@Param(value = "canteenIds") String[] canteenIds
    );



}