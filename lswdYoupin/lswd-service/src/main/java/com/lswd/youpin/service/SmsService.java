package com.lswd.youpin.service;

import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/30.
 */
public interface SmsService {

    LsResponse sendMsg(String phone);
    LsResponse sendTzMsg(String phone,String orderId,String time,String address);
}
