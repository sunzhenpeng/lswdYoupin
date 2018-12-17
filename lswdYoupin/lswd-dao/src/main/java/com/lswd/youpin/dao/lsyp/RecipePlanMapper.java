package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipePlanMapperGen;
import com.lswd.youpin.model.lsyp.RecipePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipePlanMapper extends RecipePlanMapperGen {

    int insertRecipePlan (RecipePlan recipePlan);

    RecipePlan getRecipePlanByRecipePlanId(String recipePlanId);

    int deleteRecipePlan(@Param(value = "id") Integer id);//真删

    int deleteRecipePlanByRecipePlanIdTrue(@Param(value = "recipePlanId") String recipePlanId);//真删

    int deleteRecipePlanIsDelete(RecipePlan recipePlan);//修改状态

    int updateRecipePlan (RecipePlan recipePlan);

    int updateRecipePlanIsDelete(RecipePlan recipePlan);

    int getRecipePlanCount(@Param(value = "keyword")String keyword,@Param(value = "canteenId") String canteenId);

    List<RecipePlan> getRecipePlanDetailsList(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId,
                                       @Param(value = "startdate") String startdate,@Param(value = "enddate") String enddate);

    int getRecipePlanListCountWebPageShow(@Param(value = "canteenId")String canteenId, @Param(value = "now")String now,
                              @Param(value = "startdate")String startdate,@Param(value = "enddate")String enddate,@Param(value = "canteenIds") String[] canteenIds);

    List<RecipePlan> getRecipePlanListWebPageShow(@Param(value = "canteenId")String canteenId, @Param(value = "now")String now,
                                          @Param(value = "startdate")String startdate,@Param(value = "enddate")String enddate,@Param(value = "canteenIds") String[] canteenIds,
                                          @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);


    String getRecipePlanListApp(@Param(value = "canteenId")String canteenId, @Param(value = "now")String now, @Param(value = "startdate")String startdate);
    Integer getMaxId();

    int addCommentByRecipePlanId(@Param(value = "recipePlanId")String recipePlanId,@Param(value = "comment")String comment);//根据菜品计划单号添加备注

    List<RecipePlan> getRecipePlanBeforeNow(@Param(value = "now")String now);

    /*H5页面需要用到的方法*/
    //int getRecipePlanByCanAndTimeCount(@Param(value = "canteenId")String canteenId, @Param(value = "dinnerTime")String dinnerTime);

    RecipePlan getRecipePlanByCanAndTime(@Param(value = "canteenId")String canteenId, @Param(value = "dinnerTime")String dinnerTime);

    Integer getPlanCountRecipe(@Param(value = "recipeId") String recipeId, @Param(value = "recipeType") Integer recipeType,
                               @Param(value = "planId") String planId);

    Integer updateRecipePlanCount(@Param(value = "recipeId") String recipeId, @Param(value = "recipeType") Integer recipeType,
                                    @Param(value = "planId") String planId,@Param(value = "num") Integer num);
}