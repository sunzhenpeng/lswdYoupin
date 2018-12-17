package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/7/17.
 */
public interface RecipePlanThin {

    LsResponse getRecipePlanListWebPageShow(User user,String canteenId, String dinnerTime,Integer pageNum,Integer pageSize);

    LsResponse getRecipePlanDetails(String recipePlanId);

}
