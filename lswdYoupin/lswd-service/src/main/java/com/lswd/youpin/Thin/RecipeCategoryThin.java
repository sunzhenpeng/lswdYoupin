package com.lswd.youpin.Thin;


import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/9/14.
 */
public interface RecipeCategoryThin {
    LsResponse getRecipeCategoryWeb(String keyword,String canteenId,Integer pageNum,Integer pageSize);
}
