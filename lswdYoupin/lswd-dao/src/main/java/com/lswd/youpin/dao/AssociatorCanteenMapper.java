package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorCanteenMapperGen;
import com.lswd.youpin.model.AssociatorCanteen;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorCanteenMapper extends AssociatorCanteenMapperGen {

    Integer updateCanttenBindTime(AssociatorCanteen associatorCanteen);

    List<AssociatorCanteen> getCanteenByAssociatorId(@Param(value = "associatorId")String associatorId);

}