package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MealMenuMapperGen;
import com.lswd.youpin.model.lsyp.MealMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MealMenuMapper extends MealMenuMapperGen {

    List<MealMenu> getMenusByRecordId(@Param(value = "recordId")Integer recordId);

}