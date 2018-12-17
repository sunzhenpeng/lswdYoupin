package com.lswd.youpin.alipay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.lswd.youpin.alipay.config.AliPayConfig;

/**
 * Created by liruilong on 2017/7/4.
 */
public class AliPayClientUtil {
    private static AlipayClient alipayClient;

    private AliPayClientUtil(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    public static AlipayClient getInstance() {
        alipayClient = new DefaultAlipayClient(AliPayConfig.URL, AliPayConfig.APP_ID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.NEW_SIGN_TYPE); //获得初始化的AlipayClient
        return alipayClient;
    }
}
