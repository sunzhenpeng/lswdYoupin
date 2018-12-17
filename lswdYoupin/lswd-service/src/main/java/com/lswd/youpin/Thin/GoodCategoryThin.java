package com.lswd.youpin.Thin;


import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/9/13.
 */
public interface GoodCategoryThin {

    LsResponse getGoodCategoryWeb(String keyword,String canteenId,Integer pageNum,Integer pageSize);
}
