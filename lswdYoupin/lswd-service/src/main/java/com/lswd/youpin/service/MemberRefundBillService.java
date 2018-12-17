package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
public interface MemberRefundBillService {

    LsResponse getMemberRefundBillList(String keyword,String canteenId,String date,Integer pageNum,Integer pageSize,User user);

}
