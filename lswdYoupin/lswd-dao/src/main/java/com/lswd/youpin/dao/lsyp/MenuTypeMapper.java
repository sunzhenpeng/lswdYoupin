package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MenuTypeMapperGen;
import com.lswd.youpin.model.lsyp.MenuType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuTypeMapper extends MenuTypeMapperGen {

    List<MenuType> getcounterMenuTypeListAll();

    int getcounterMenuTypeListCount(@Param(value = "name")String name);

    List<MenuType> getcounterMenuTypeList(@Param(value = "name")String name,@Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    List<MenuType> getCounterMenuType(@Param(value = "counterId")String counterId);

}