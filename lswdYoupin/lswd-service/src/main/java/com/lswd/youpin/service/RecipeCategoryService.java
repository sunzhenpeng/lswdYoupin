package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipeCategory;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/6/30.
 */
public interface RecipeCategoryService {

    LsResponse insertOrUpdateRecipeCategory(RecipeCategory recipeCategory, User user);

    LsResponse deleteRecipeCategoryById(Integer id, User user);

    LsResponse getRecipeCategoryList(String keyword,Integer secondflag ,Integer parentId,Integer pageNum, Integer pageSize);

    LsResponse getRecipeCategoryWeb(String keyword,String canteenId ,Integer pageNum, Integer pageSize);

    LsResponse getRecipeCategoryListAll(String canteenId,User user);

    LsResponse getRecipeCategoryListH5(String canteenId);

    LsResponse checkOutName(String name,String canteenId);
}
