package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipePlan;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/20.
 */
public interface RecipePlanService {
    LsResponse insertRecipePlan(User user,List<RecipePlan> recipePlan);

    LsResponse deleteRecipePlan(Integer id,User user);

    LsResponse deleteRecipePlanOneRecipe(String recipePlanId,String recipeId,Integer recipeType,User user);

    LsResponse updateRecipePlan(RecipePlan recipePlan,User user);

    LsResponse updateRecipePlanOneDay(RecipePlan recipePlan,User user);

    LsResponse getRecipePlanDetailsList(String keyword,String canteenId,String dinnerTime);

    LsResponse getRecipePlanDetails(String recipePlanId);

    LsResponse getRecipePlanListWebPageShow(User user,String canteenId, String dinnerTime,Integer pageNum,Integer pageSize);

    LsResponse addCommentByRecipePlanId(String recipePlanId,String comment);

    /*以下是H5画面用到的方法，方法补充*/
    LsResponse getEatingDetailsH5Show(Associator associator,String keyword,String canteenId, String dinnerTime, Integer categoryId, Integer eatType, Integer pageNum, Integer pageSize);
}
