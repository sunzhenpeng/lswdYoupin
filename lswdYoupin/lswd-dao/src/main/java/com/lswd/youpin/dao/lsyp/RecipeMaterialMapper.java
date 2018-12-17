package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeMaterialMapperGen;
import com.lswd.youpin.model.lsyp.RecipeMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipeMaterialMapper extends RecipeMaterialMapperGen {

    List<RecipeMaterial> getRecipeDetails(@Param(value = "recipeId") String recipeId);

    Integer getMaxId();

    Integer deleteByRecipeId(@Param(value = "recipeId")String recipeId);

}