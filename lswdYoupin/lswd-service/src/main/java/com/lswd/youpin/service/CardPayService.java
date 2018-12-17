package com.lswd.youpin.service;

import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/8/10.
 */
public interface CardPayService {
    LsResponse getBalance(String memberId);
}
