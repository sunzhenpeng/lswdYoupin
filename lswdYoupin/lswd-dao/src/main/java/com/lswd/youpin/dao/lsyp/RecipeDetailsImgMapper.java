package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeDetailsImgMapperGen;
import com.lswd.youpin.model.lsyp.RecipeDetailsImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipeDetailsImgMapper extends RecipeDetailsImgMapperGen {

    List<RecipeDetailsImg> getRecipeDetailsImgs(@Param(value = "recipeId")String recipeId);

    int deleteImgByRecipeId(@Param(value = "recipeId")String recipeId);

    int insertRecipeDetailsImg(RecipeDetailsImg recipeDetailsImg);

    int deleteImg(@Param(value = "recipeId")String recipeId,@Param(value = "imageurl")String imageurl);

}