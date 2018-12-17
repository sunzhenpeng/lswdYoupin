package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/30.
 */
public interface AssociatorRefundBillThin {

    LsResponse getAssociatorRefundBillList(String keyword,String canteenId,String date,Integer pageNum,Integer pageSize,User user);
}
