package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeOrderItemsMapperGen;
import com.lswd.youpin.model.lsyp.RecipeOrderItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipeOrderItemsMapper extends RecipeOrderItemsMapperGen {

    void insertItem(RecipeOrderItems recipeOrderItems);

    /*begin zgq write*/
    List<RecipeOrderItems> getRecipeOrderItemsList(@Param("orderId")String orderId);
    /*end zgq write*/
}