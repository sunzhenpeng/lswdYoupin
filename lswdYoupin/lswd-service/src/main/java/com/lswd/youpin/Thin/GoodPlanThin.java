package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/8/1.
 */
public interface GoodPlanThin {
    LsResponse getGoodPlanListWebPageShow(User user, String canteenId, String dinnerTime, Integer pageNum, Integer pageSize);

    LsResponse getGoodPlanDetails(String goodPlanId);
}
