package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.AppResourceMapperGen;
import com.lswd.youpin.model.lsy.AppResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AppResourceMapper extends AppResourceMapperGen {

    List<AppResource> getAppResourceByMachineId(@Param(value = "keyword") String keyword,
                                                @Param(value = "machineId") Integer machineId);

    List<Map<String, Object>> getAppResourceByMachineNo(@Param(value = "machineNo") String machineNo);

    List<AppResource> getAppResourceAll();


    int delAppResource(Integer resourceId);

}