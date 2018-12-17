package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
/**
 * Created by zhenguanqi on 2017/11/22.
 */
public interface AssociatorPayBillThin {

    LsResponse getAssociatorPayBillList(String keyword,String canteenId,String date,Integer pageNum,Integer pageSize,User user);

}
