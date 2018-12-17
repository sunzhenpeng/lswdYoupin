package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.CanteenLocationMapperGen;
import com.lswd.youpin.model.CanteenLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CanteenLocationMapper extends CanteenLocationMapperGen {

    List<CanteenLocation> getCanteenByLocation(@Param("longitude") Double longitude, @Param("latitude") Double latitude);

}