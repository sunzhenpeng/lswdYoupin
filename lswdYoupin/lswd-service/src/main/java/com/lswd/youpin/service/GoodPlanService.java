package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodPlan;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/15.
 */
public interface GoodPlanService {

    LsResponse addGoodPlan(List<GoodPlan> goodPlen, User user);

    LsResponse deleteGoodPlan(Integer id, User user);

    LsResponse deleteGoodPlanOneGood(String goodPlanId,String goodId,User user);

    LsResponse updateGoodPlan(GoodPlan goodPlan, User user);

    LsResponse updateGoodPlanOneDay(GoodPlan goodPlan, User user);

    LsResponse getGoodPlanListWebPageShow(User user, String canteenId,String pickingTime,Integer pageNum, Integer pageSize);

    LsResponse getGoodPlanDetails(String goodPlanId);

    LsResponse getGoodPlanDetailsList(String keyword,String canteenId,String pickingTime);

    LsResponse addCommentByGoodPlanId(String goodplanid,String comment);//为商品计划添加备注！

    /*H5画面需要用到的方法！！！*/
    LsResponse getGoodPlanContentH5Show(Associator associator,String keyword,String canteenId, String saleTime, Integer categoryId, Integer pageNum, Integer pageSize);
}
