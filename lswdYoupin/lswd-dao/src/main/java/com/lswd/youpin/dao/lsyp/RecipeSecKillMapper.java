package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RecipeSecKillMapperGen;
import com.lswd.youpin.model.lsyp.RecipeSecKill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecipeSecKillMapper extends RecipeSecKillMapperGen {

    int getRecipeSecKillCountWeb(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,
                              @Param(value = "dinnerTime")String dinnerTime,@Param(value = "canteenIds")String[] canteenIds);

    List<RecipeSecKill> getRecipeSecKillListWeb(@Param(value = "keyword")String keyword, @Param(value = "canteenId")String canteenId,
                                             @Param(value = "dinnerTime")String dinnerTime, @Param(value = "canteenIds")String[] canteenIds,
                                             @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    int deleteStatus(@Param(value = "id")Integer id);

    int getRecipeSecKillCountH5(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,
                              @Param(value = "dinnerTime")String dinnerTime,@Param(value = "canteenIds")String[] canteenIds,@Param(value = "eatType")Integer eatType);

    List<RecipeSecKill> getRecipeSecKillListH5(@Param(value = "keyword")String keyword, @Param(value = "canteenId")String canteenId,
                                             @Param(value = "dinnerTime")String dinnerTime, @Param(value = "canteenIds")String[] canteenIds,@Param(value = "eatType")Integer eatType,
                                             @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

}