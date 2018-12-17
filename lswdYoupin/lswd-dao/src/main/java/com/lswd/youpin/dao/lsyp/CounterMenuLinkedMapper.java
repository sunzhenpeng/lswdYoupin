package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterMenuLinkedMapperGen;
import com.lswd.youpin.model.lsyp.CounterMenuLinked;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterMenuLinkedMapper extends CounterMenuLinkedMapperGen {

    Integer deleteByTwoForeignKey(@Param(value = "counterId")String counterId,@Param(value = "menuId")String menuId);

    List<CounterMenuLinked> getMenuListByCounterId(@Param(value = "counterId")String counterId);

    List<CounterMenuLinked> getMenuListByMenuId(@Param(value = "menuId")String menuId);

    Integer updateByMenuId(@Param(value = "menuId")String menuId,@Param(value = "typeId")Integer typeId);

}