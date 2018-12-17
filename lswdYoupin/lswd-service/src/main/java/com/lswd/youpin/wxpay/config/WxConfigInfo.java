package com.lswd.youpin.wxpay.config;

/**
 * Created by liruilong on 2017/7/3.
 */
public class WxConfigInfo {
    /**
     * 服务号相关信息
     */
    public final static String APPID = "wx3b6b75804a1cd6ba";//服务号的appid

    public final static String APP_SECRECT = "2dad34293b6b08df6cc19ce5f6ce5858";//服务号的appSecrect

    public final static String TOKEN = "weixin0128";//服务号的配置token

    public final static String MCH_ID = "1460498602";//开通微信支付分配的商户号

    public final static String API_KEY = "201706292046lsgrp1506wangxiaoqdd";//商户API密钥 自行去商户平台设置

    public final static String SIGN_TYPE = "MD5";//签名加密方式

    public final static String LITTLE_APPID = "wx68201259a076a431";

    public final static String RECHARGE = "1";

    public final static String GOOD_PAY = "2";

    public final static String WX_PAY = "1";

    public final static String ALI_PAY = "2";

    public final static String TRADE_TYPE = "JSAPI";

    public final static int HTTPCONNECTTIMEOUTMS = 8000;

    public final static int HTTPREADTIMEOUTMS = 10000;

    public final static String CHARSET = "UTF-8";

    /**
     * 微信基础接口地址
     */
    //异步通知的回调地址，必须是外网可以访问
    public final static String NOTIFY_URL = "https://web.lsypct.com/lswd-web/pay/wxPay/wxNotify";
    //用餐卡支付时的异步回调地址
    public final static String CARD_NOTIFY_URL = "http://www.lsypct.com/wx/wxNotiy";
    //微信支付成功支付后跳转的地址
    public final static String SUCCESS_URL = "http://www.lsypct.com/pay_success.jsp";
    //oauth2授权时回调action
    public final static String REDIRECT_URI = "http://14.117.25.80:8016/GoMyTrip/a.jsp?port=8016";

    public final static String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信基础接口地址
     */
    //获取用户的基本信息
    public final static String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    //获取token接口(GET)
    public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //获取code，通过code获取openId
    public final static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //oauth2授权接口(GET)
    public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //刷新access_token接口（GET）
    public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    //获取ticket 接口
    public static  final String TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

}
