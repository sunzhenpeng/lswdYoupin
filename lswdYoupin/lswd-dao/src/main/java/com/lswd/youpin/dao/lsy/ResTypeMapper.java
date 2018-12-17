package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.ResTypeMapperGen;
import com.lswd.youpin.model.lsy.ResType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResTypeMapper extends ResTypeMapperGen {


    List<ResType> getResTypeList(@Param(value = "keyword") String keyword,
                                 @Param(value = "type") String type,
                                 @Param(value = "canteenIds") String[] canteenIds,
                                 @Param(value = "pageId") Integer pageId,
                                 @Param(value = "machineId") Integer machineId,
                                 @Param(value = "pageSize") Integer pageSize,
                                 @Param(value = "offset") Integer offset
    );

    Integer getCount(@Param(value = "keyword") String keyword,
                     @Param(value = "type") String type,
                     @Param(value = "canteenIds") String[] canteenIds,
                     @Param(value = "pageId") Integer pageId,
                     @Param(value = "machineId") Integer machineId
    );

    String getPageIdByResTypeId(@Param(value = "resTypeId") Integer resTypeId);


//app------------------------------------

    Integer getResTypeId(@Param(value = "machineNo") String machineNo);

    List<Map<String, Object>> getResTypeListByMachineNo(@Param(value = "machineId") Integer machineId,
                                                         @Param(value = "pageId") Integer pageId);



    List<Map<String, Object>> getResTypeListByPageId(@Param(value = "machineId") Integer machineId,
                                                        @Param(value = "pageId") Integer pageId);

    List<Map<String, Object>> getUploadResTypeList(@Param(value = "machineId") Integer machineId);

    String getResTypeNameById(@Param(value = "resTypeId") Integer resTypeId);
}