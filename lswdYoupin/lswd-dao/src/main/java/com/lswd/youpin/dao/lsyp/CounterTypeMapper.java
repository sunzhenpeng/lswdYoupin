package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterTypeMapperGen;
import com.lswd.youpin.model.lsyp.CounterType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterTypeMapper extends CounterTypeMapperGen {

    List<CounterType> getCounterTypeList(@Param(value = "name")String name);

}