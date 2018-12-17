package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.JSPlatformMapperGen;
import com.lswd.youpin.model.lsyp.JSPlatform;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JSPlatformMapper extends JSPlatformMapperGen {

    List<JSPlatform> getJSPlatformListAll(@Param(value = "canteenId")String canteenId);

    Integer getJSPlatformListCount(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId);

    List<JSPlatform> getJSPlatformList(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId,
                                       @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    Integer getMaxId();

    Integer deleteByPrimaryKeyUpdate(@Param(value = "id")Integer id);

}