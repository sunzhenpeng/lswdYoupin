package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
public interface MemberPayBillService {

    LsResponse getMemberPayBillList(String keyword, String canteenId, String date, Integer payType, Integer pageNum, Integer pageSize, User user);

    LsResponse readCardUid();
}
