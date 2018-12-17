package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.RegionMapperGen;
import com.lswd.youpin.model.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionMapper extends RegionMapperGen {

    Integer getTotalCount(@Param("regionName") String regionName);

    List<Region> getRegionList(@Param("regionName") String regionName, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Region> selectRegions();

    Region getRegionByRegionId(@Param(value = "regionId")String regionId);

    Region getRegionByRegionName(@Param(value = "regionName")String regionName);

    Integer getMaxId();
}