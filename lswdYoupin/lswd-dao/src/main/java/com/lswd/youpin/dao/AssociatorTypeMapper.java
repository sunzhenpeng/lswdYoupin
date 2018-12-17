package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorTypeMapperGen;
import com.lswd.youpin.model.AssociatorType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorTypeMapper extends AssociatorTypeMapperGen {
    List<AssociatorType> getAssociatorTypeList(@Param(value = "name")String name);
}