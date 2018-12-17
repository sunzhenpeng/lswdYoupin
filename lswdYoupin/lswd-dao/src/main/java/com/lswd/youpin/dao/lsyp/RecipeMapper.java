package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeMapperGen;
import com.lswd.youpin.model.lsyp.Recipe;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecipeMapper extends RecipeMapperGen {

    List<Recipe> getRecipeListAll(@Param(value = "canteenIds") String[] canteenIds);//查询所有的菜品信息，导出用！

    List<Recipe> getRecipeListAllAll(@Param(value = "canteenId") String canteenId);//查询所有的菜品信息,餐盘类型对应的时候用

    Integer getRecipeListAllPageCount(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,@Param(value = "categoryId")Integer categoryId);

    List<Recipe> getRecipeListAllPage(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,@Param(value = "categoryId")Integer categoryId,
                                      @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    List<Recipe> getRecipeByCategoryId(@Param(value = "categoryId") Integer categoryId);

    Integer insertRecipe(Recipe recipe);

    Integer deleteRecipe(@Param(value = "id") Integer id);

    Integer updateRecipe(Recipe recipe);

    Integer updateRecipeIsDelete(@Param(value = "id") Integer id);

    Recipe getRecipeByRecipeId(@Param(value = "recipeId") String recipeId);//根据菜品编号操作菜品

    Recipe getRecipeById(@Param(value = "id") Integer id);//根据菜品主键ID查找商品

    /*下面这两个方法是是用来WEB端列表显示的*/
    int getRecipeCount(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,@Param(value = "categoryId")Integer categoryId,@Param(value = "canteenIds") String[] canteenIds);

    List<Recipe> getRecipeList(@Param(value = "keyword")String keyword, @Param(value = "canteenId")String canteenId, @Param(value = "categoryId")Integer categoryId,
                               @Param(value = "canteenIds") String[] canteenIds, @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    Integer getRecipeMaxID();

    /*下面这两个方法是 菜品计划新增时查询的数据，只包括id，recipe_id，recipe_name，surplus四个字段，而且还需要很多的查询条件*/
    List<Recipe> getRecipeMINI(@Param(value = "keyword")String keyword,@Param(value = "categoryId")Integer categoryId, @Param(value = "canteenId")String canteenId,
                               @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    int getRecipeMINICount(@Param(value = "keyword")String keyword, @Param(value = "categoryId")Integer categoryId,@Param(value = "canteenId")String canteenId);

    int updateRecipeSurPlus(@Param(value = "recipeId")String recipeId,@Param(value = "surplus")Integer surplus);

//app+++++++++++++

    Map<String, Object> getRecipeUrlByRecipeId(@Param(value = "id") Integer id);//根据菜品编号操作菜品
}