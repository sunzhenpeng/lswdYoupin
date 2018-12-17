package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeAttributeMapperGen;
import com.lswd.youpin.model.lsyp.RecipeAttribute;
import org.apache.ibatis.annotations.Param;

public interface RecipeAttributeMapper extends RecipeAttributeMapperGen {

    RecipeAttribute getAttributeByRecipeId(@Param(value = "recipeId") String recipeId);
}