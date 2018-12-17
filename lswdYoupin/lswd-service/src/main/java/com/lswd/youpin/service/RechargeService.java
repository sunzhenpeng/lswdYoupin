package com.lswd.youpin.service;

import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/5.
 */
public interface RechargeService {
    String recharge(Map<String, String> data, HttpServletRequest request,HttpServletResponse response);

    LsResponse getRecharge(String associatorId, String startTime, String endTime, Integer pageNum, Integer pageSize);

    LsResponse refund(String associatorId);

    LsResponse getAccountInfo(String associatorId);
}
