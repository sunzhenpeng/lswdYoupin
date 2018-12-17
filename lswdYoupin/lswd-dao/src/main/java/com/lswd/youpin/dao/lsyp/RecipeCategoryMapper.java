package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeCategoryMapperGen;
import com.lswd.youpin.model.lsyp.RecipeCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipeCategoryMapper extends RecipeCategoryMapperGen {
    Integer getGoodCategoryCount(@Param(value = "keyword")String keyword,@Param(value = "secondflag")Integer secondflag,@Param(value = "parentId")Integer parentId);

    List<RecipeCategory> getRecipeCategoryList(@Param(value = "keyword")String keyword,@Param(value = "secondflag")Integer secondflag,
                                               @Param(value = "parentId")Integer parentId,
                                               @Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    Integer getRecipeCategoryWebCount(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId);

    List<RecipeCategory> getRecipeCategoryWeb(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,
                                                 @Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    List<RecipeCategory> getRecipeCategoryAll(@Param(value = "canteenId")String canteenId);

    RecipeCategory getRecipeCategoryById(@Param(value = "id") Integer id);

    RecipeCategory getRecipeCategoryByName(@Param(value = "name") String name,@Param(value = "canteenId") String canteenId);

    RecipeCategory checkOutName(@Param(value = "name") String name,@Param(value = "canteenId") String canteenId);

    List<RecipeCategory> getRecipeCategoryListH5(String canteenId);

}