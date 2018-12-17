package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipePlanItemsMapperGen;
import com.lswd.youpin.model.lsyp.RecipePlanItems;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface RecipePlanItemsMapper extends RecipePlanItemsMapperGen {

    int insertPlanItems(RecipePlanItems recipePlanItems);

    int updatePlanItemsSurplus(RecipePlanItems recipePlanItems);

    List<RecipePlanItems> getPlanItemsByRecipePlanId(@Param(value = "recipePlanId")String recipePlanId);

    int deletePlanItemsByRecipePlanIdTrue(@Param(value = "recipePlanId")String recipePlanId);//真删,根据计划单号

    int deletePlanItemsByRecipePlanIdIsDelete(@Param(value = "recipePlanId")String recipePlanId);//修改状态

    int deleteRecipePlanOneRecipeTrue(@Param(value = "recipePlanId")String recipePlanId,@Param(value = "recipeId")String recipeId,@Param(value = "recipeType")Integer recipeType);

    int getRecipePlanItemsCount(@Param(value = "recipePlanId") String recipePlanId);

    List<RecipePlanItems> getRecipePlanItemsList(@Param(value = "recipePlanId") String recipePlanId);

    List<RecipePlanItems> getRecipeByRecipePlanIdAndRecipeType(@Param(value = "recipePlanId") String recipePlanId,@Param(value = "recipeType") Integer recipeType);

    /*下面是H5需要用到的方法*/
    int getItemsByPlanIdAndTypeAndCateCount(@Param(value = "keyword") String keyword,@Param(value = "recipePlanId") String recipePlanId,
                                            @Param(value = "eatType") Integer eatType,@Param(value = "categoryId")Integer categoryId);

    List<RecipePlanItems> getItemsByPlanIdAndTypeAndCate(@Param(value = "keyword") String keyword,@Param(value = "recipePlanId") String recipePlanId,@Param(value = "eatType") Integer eatType,
                                                         @Param(value = "categoryId")Integer categoryId, @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    int updateRecipePlanSurPlus(@Param(value = "recipePlanId")String recipePlanId,@Param(value = "recipeId")String recipeId,@Param(value = "eatType")Integer eatType,@Param(value = "surplus")Integer surplus);

    int updateSurPlus(@Param(value = "recipePlanId")String recipePlanId,@Param(value = "recipeId")String recipeId,@Param(value = "eatType")Integer eatType,@Param(value = "surplus")Integer surplus);

    //app+++++++++++++++++++++++++++++++++++++++++++++++

    List<Map<String,Object>> getPlanItemsApp(@Param(value = "recipePlanId")String recipePlanId, @Param(value = "recipeType") Integer recipeType);

}