package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Good;
import com.lswd.youpin.response.LsResponse;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import java.util.List;


/**
 * Created by zhenguanqi on 2017/6/14.
 */
public interface GoodService {

    LsResponse addGood(Good good, User user);

    LsResponse getGoodDetails(String goodId, User user);

    LsResponse addGoodDetails(Good good, User user);

    LsResponse delGoodDetailsImg(String goodId, String imageurl, User user);

    LsResponse delCancelImg(List<String> imageurls,User user);

    LsResponse deleteGood(Integer id, User user);

    LsResponse updateGood(Good good, User user);

    LsResponse getGoodInfoByGoodId(String goodId);

    LsResponse getGoodList(User user,String keyword, String supplier, Integer categoryId, String canteenId, Integer pageNum, Integer pageSize);

    /*商品计划新增时需要用到的方法*/
    LsResponse getGoodMINI(String goodPlanId,String keyword, String supplierId, Integer categoryId, String pickingTime,String canteenId, Integer pageNum, Integer pageSize);

    /*-------------------------------H5用到的方法----------------------------------*/
    LsResponse getGoodInfoByGoodIdH5(Associator associator,String goodId,String canteenId,String pickingTime,String goodPlanId);

    /*------------------------------------------------------------------微信小程序需要用到接口------------------------------------------------------------------------------------------------------*/
    LsResponse getGoodOrRecipeByNoWx(String goodOrRecipeId);
}