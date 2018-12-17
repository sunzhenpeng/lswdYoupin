package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.NutritionMapperGen;
import com.lswd.youpin.model.lsyp.Nutrition;
import com.lswd.youpin.model.vo.NutritionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NutritionMapper extends NutritionMapperGen {

    Integer getNutritionListCount(@Param(value = "keyword") String keyword);

    List<Nutrition> getNutritionList(@Param(value = "keyword") String keyword, @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    List<Nutrition> getNutritionListAll();

    List<Nutrition> getNutritionListByRecipeId(@Param(value = "recipeId")String recipeId);

    List<Nutrition> getNutritionByName(@Param(value = "name")String name);

    NutritionVo getRecipeNutritionByView(@Param(value = "recipeIdBH")String recipeIdBH);
}