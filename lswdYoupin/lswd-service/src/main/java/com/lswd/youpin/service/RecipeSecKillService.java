package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipeSecKill;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/8/19.
 */
public interface RecipeSecKillService {

    LsResponse getRecipeSecKillListWeb(User user,String keyword,String canteenId,String dinnerTime,Integer pageNum,Integer pageSize);

    LsResponse addOrUpdate(User user, RecipeSecKill recipeSecKill);

    LsResponse delete(User user,Integer id);

    LsResponse getRecipeSecKillListH5(User user,String keyword,String canteenId,String dinnerTime,Integer eatType,Integer pageNum,Integer pageSize);
}
