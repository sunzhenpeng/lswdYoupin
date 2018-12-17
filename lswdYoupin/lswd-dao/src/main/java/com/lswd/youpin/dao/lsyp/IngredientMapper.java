package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.IngredientMapperGen;
import org.apache.ibatis.annotations.Param;

public interface IngredientMapper extends IngredientMapperGen {

    Integer deleteByTwoForeignKey(@Param(value = "nutritionid")Integer nutritionid,@Param(value = "menuid")String menuid);

}