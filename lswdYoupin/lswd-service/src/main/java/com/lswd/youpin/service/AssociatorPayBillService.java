package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/22.
 */
public interface AssociatorPayBillService {

    LsResponse getAssociatorPayBillList(String keyword,String canteenId,String date,Integer pageNum,Integer pageSize,User user);

}
