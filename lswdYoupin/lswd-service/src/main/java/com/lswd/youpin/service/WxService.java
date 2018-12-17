package com.lswd.youpin.service;

import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信相关
 * liruiong 2016/11/25
 */
public interface WxService {
    String getPackage(Map<String, String> data, HttpServletRequest servletRequest);

    LsResponse refund(Map<String, String> data);

    void wxNotify(Map<Object, Object> data, HttpServletRequest servletRequest, HttpServletResponse response) throws IOException;

    String getQrCode(String orderNo);

    LsResponse query(String out_trade_nos);

    LsResponse getJSSDKConfig(String url);

    LsResponse getOpenId(String code);


}
