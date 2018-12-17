package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.response.LsResponse;
import org.w3c.dom.ls.LSException;

/**
 * Created by zhenguanqi on 2017/6/22.
 */
public interface GoodCategoryService {

    LsResponse addOrUpdateGoodCategory(GoodCategory goodCategory, User user);

    LsResponse deleteGoodCategoryById(Integer id,User user);

    LsResponse getFirstGoodCategory(String name,Integer pageNum,Integer pageSize);

    LsResponse getSecondGoodCategory(String name,Integer id);

    LsResponse getGoodCategoryListAll(String canteenId,User user);

    LsResponse getGoodCategoryWeb(String keyword,String canteenId,Integer pageNum,Integer pageSize);

    LsResponse checkOutName(String name,String canteenId);

    /*------------------------------------------H5页面需要全部分类-------------------------------------------------------------------*/

    LsResponse getGoodCategoryListH5(String canteenId);
}
