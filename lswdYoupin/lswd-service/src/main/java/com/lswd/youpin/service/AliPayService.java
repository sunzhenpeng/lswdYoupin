package com.lswd.youpin.service;

import com.alipay.api.AlipayApiException;
import com.lswd.youpin.model.vo.OrderVO;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/4.
 */
public interface AliPayService {
    String getAliPayForm(Map<String, String> map, HttpServletRequest request, HttpServletResponse response);

    void returnUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException;

    void notifyUrl(Map<String, String> data, OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException;

    LsResponse refund(Map<String, String> map,HttpServletRequest request);

    LsResponse query(String out_trade_nos);
}
