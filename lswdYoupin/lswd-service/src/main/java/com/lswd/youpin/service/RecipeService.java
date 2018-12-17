package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Ingredient;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/6/19.
 */
public interface RecipeService {
    LsResponse insertRecipe (User user, Recipe recipe);

    LsResponse getRecipeDetails(String recipeId,User user);

    LsResponse addRecipeDetails(Recipe recipe,User user);

    LsResponse delRecipeDetailsImg(String recipeId,String imageurl,User user);

    LsResponse deleteRecipe (User user, Integer id);

    LsResponse updateRecipe (User user, Recipe recipe);

    LsResponse getRecipeList (User user,String keyword,String canteenId,Integer categoryId,Integer pageNum,Integer pageSize);

    LsResponse getRecipeListAll(String canteenId);

    LsResponse getRecipeListAllPage(String keyword,String canteenId,Integer categoryId,String recipeId,Integer pageNum,Integer pageSize);

    LsResponse getRecipeInfo (User user, Integer id);

    LsResponse getAccessNumber();

    LsResponse getUnitAndCookType();

    LsResponse getRecipeMINI(String keyword,String recipePlanId,Integer recipeType,Integer categoryId,String canteenId,Integer pageNum,Integer pageSize);

    /*-------------------------------------------------------H5需要用到的方法----------------------------------------------------------------------------------*/
    LsResponse getRecipeInfoByRecipeIdH5(Associator associator, String recipeId,String canteenId,String dinnerTime,Integer eatType,String recipePlanId);

    /*------------------------------------------------------------------微信小程序需要用到接口------------------------------------------------------------------------------------------------------*/
    LsResponse getRecipeInfoByRecipeIdWx(String recipeId);

    /*-------------------------------------------------------------菜品营养成分管理部分-----------------------------------------------------*/

    LsResponse getNutritionByRecipeId(String recipeId);

    LsResponse recipeAddNutrition(User user,Ingredient ingredient);

    LsResponse recipeDeleteNutrition(User user,String ingredientId);

    LsResponse getRecipeNutritionList (User user,String keyword,String canteenId,Integer categoryId,Integer pageNum,Integer pageSize);
}
