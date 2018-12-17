package com.lswd.youpin.wxpay.WxApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lswd.youpin.model.SNSUserInfo;
import com.lswd.youpin.model.WeixinOauth2Token;
import com.lswd.youpin.utils.RandomUtil;
import com.lswd.youpin.utils.RequestHandler;
import com.lswd.youpin.wxpay.config.WXPayConfigImpl;
import com.lswd.youpin.wxpay.config.WxConfigInfo;
import com.lswd.youpin.wxpay.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by liruilong on 2017/7/1.
 */
public class WxPayApi {

    private final static Logger log = LoggerFactory.getLogger(WxPayApi.class);

    /**
     * 调用统一下单接口，获取统一下单接口的返回信息
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String getUnifiedOrderInfo(Map<String, String> data) {
        log.info("整理统一下单接口所需要的数据：={}", JSONObject.toJSON(data));
        String body="商品购买";
        try {
            String out_trade_no = data.get("out_trade_no");
            if (out_trade_no == null || out_trade_no == "") {
                //out_trade_no 为空说明是在线充值操作，订单支付传进来的订单号是存在out_trade_nos字段中
                out_trade_no = RandomUtil.getRandomCodeStr(6);
                body="餐卡充值";
            }
            String openId = data.get("openid");
            String totalFee = data.get("total_fee");
            // 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP
            String nonceStr = WXPayUtil.generateNonceStr();
            SortedMap<String, String> packageParams = new TreeMap<>();
            packageParams.put("appid", WxConfigInfo.APPID);
            packageParams.put("mch_id", WxConfigInfo.MCH_ID);
            // 随机字符串，不长于32位。
            packageParams.put("nonce_str", nonceStr);
            // 商品信息
            packageParams.put("body", body);

            packageParams.put("attach", data.get("attach"));
            // 订单号
            packageParams.put("out_trade_no", out_trade_no);
            // 微信的金额单位是分，传过来的是元，
            String total_fee = CommonUtil.getMoney(totalFee);
            // 金额
            packageParams.put("total_fee", total_fee);
            // APP和网页支付提交用户端ip,必须
            packageParams.put("spbill_create_ip", data.get("spbill_create_ip"));
            // 接收微信支付异步通知回调地址
            packageParams.put("notify_url", WxConfigInfo.NOTIFY_URL);
            // 交易类型
            packageParams.put("trade_type", WxConfigInfo.TRADE_TYPE);
            // 用户的openid，必须
            packageParams.put("openid", openId);
            RequestHandler reqHandler = new RequestHandler(null, null);
            reqHandler.init(WxConfigInfo.APPID, WxConfigInfo.APP_SECRECT, WxConfigInfo.API_KEY);
            String sign = reqHandler.createSign(packageParams);
            // 签名
            packageParams.put("sign", sign);
            String xml = CommonUtil.ArrayToXml(packageParams);
            String prepay_id = getPayNo(WxConfigInfo.createOrderURL, xml);
            // 获取prepay_id后，拼接最后请求支付所需要的package
            SortedMap<String, String> finalPackage = new TreeMap<>();
            String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
            String noncesStr = WXPayUtil.generateNonceStr();
            String packages = "prepay_id=" + prepay_id;
            finalPackage.put("appId", WxConfigInfo.APPID);
            finalPackage.put("timeStamp", timestamp);
            finalPackage.put("nonceStr", noncesStr);
            finalPackage.put("package", packages);
            finalPackage.put("signType", "MD5");
            String finalSign = reqHandler.createSign(finalPackage);
            finalPackage.put("paySign", finalSign);
            String result = JSON.toJSONString(finalPackage);
            log.info("统一下单获取的package================" + result);
            return result;
        } catch (Exception e) {
            log.info("调用统一下单接口出错：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    public static String getOrderInfo(String out_trade_no) {
        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            String nonceStr = WXPayUtil.generateNonceStr();
            data.put("appid", WxConfigInfo.APPID);
            data.put("mch_id", WxConfigInfo.MCH_ID);
            data.put("nonce_str", nonceStr);
            data.put("sign_type", WxConfigInfo.SIGN_TYPE);
            data.put("out_trade_no", out_trade_no);
            String sign = WXPayUtil.generateSignature(data, WxConfigInfo.API_KEY);
            data.put("sign", sign);
            Map<String, String> resultMap = wxpay.orderQuery(data);
            log.info("微信订单查询返回的参数={}", JSON.toJSON(resultMap));
            return resultMap.get("trade_state");
        } catch (Exception e) {
            log.error("查询订单信息失败:{}", e.getMessage());
        }
        return null;
    }


    /**
     * 根据订单编号查询退款信息
     *
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    public static String getRefundInfo(String out_trade_no) throws Exception {
        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            String nonceStr = WXPayUtil.generateNonceStr();
            data.put("appid", WxConfigInfo.APPID);
            data.put("mch_id", WxConfigInfo.MCH_ID);
            data.put("nonce_str", nonceStr);
            data.put("sign_type", WxConfigInfo.SIGN_TYPE);
            data.put("out_trade_no", out_trade_no);
            String sign = WXPayUtil.generateSignature(data, WxConfigInfo.API_KEY);
            data.put("sign", sign);
            Map<String, String> resultMap = wxpay.refundQuery(data);
            String result = JSON.toJSONString(resultMap);
            return result;
        } catch (Exception e) {
            log.error("查询订单退款信息失败:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据code 获取openId
     *
     * @param code
     * @return
     */
    //微信授权登录时，调用getOpenId 方法
/*    public static LsResponse getOpenId(String code) {
        log.info("code======================" + code);
        WeixinOauth2Token wat;
        String oauth_url = WxConfigInfo.OAUTH2_URL.replace("APPID", WxConfigInfo.APPID).replace("SECRET", WxConfigInfo.APP_SECRECT).replace("CODE", String.valueOf(code));
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth_url, "POST", null);
        LsResponse lsResponse = new LsResponse();
        if (jsonObject != null) {
            wat = new WeixinOauth2Token();
            try {
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
                lsResponse.setData(wat);
            } catch (Exception e) {
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        } else {
            lsResponse.setAsFailure();
            lsResponse.setMessage("微信授权失败");
            log.info("code获取失败");
        }
        return lsResponse;
    }*/
    public static String getOpenId(String code) {
        String oauth_url = WxConfigInfo.OAUTH2_URL.replace("APPID", WxConfigInfo.APPID).replace("SECRET", WxConfigInfo.APP_SECRECT).replace("CODE", String.valueOf(code));
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth_url, "POST", null);
        String openId="";
        if (jsonObject != null) {
            openId=jsonObject.getString("openid");
        }
        return openId;
        }

    public static WeixinOauth2Token getOpenId2(String code) {
        String oauth_url = WxConfigInfo.OAUTH2_URL.replace("APPID", WxConfigInfo.APPID).replace("SECRET", WxConfigInfo.APP_SECRECT).replace("CODE", String.valueOf(code));
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(oauth_url, "POST", null);
        String openId="";
        WeixinOauth2Token wat = null;
        if (jsonObject != null) {
            wat = new WeixinOauth2Token();
            try {
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }


    public static String getTicket() {
        String token_url = WxConfigInfo.TOKEN_URL.replace("APPID", WxConfigInfo.APPID).replace("APPSECRET", WxConfigInfo.APP_SECRECT);
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(token_url, "GET", null);
        log.info("返回的access_token======================{}", jsonObject);
        String access_token = jsonObject.getString("access_token");
        String ticket_url = WxConfigInfo.TICKET_URL.replace("ACCESS_TOKEN", access_token);
        JSONObject ticketObject = CommonUtil.httpsRequestToJsonObject(ticket_url, "GET", null);
        log.info("返回的ticketObject======================{}", ticketObject);
        String ticket = ticketObject.getString("ticket");
        return ticket;
    }

    public static Map<String, String> refund(Map<String, String> data) throws Exception {
        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);
            String out_refund_no = UUID.randomUUID().toString().substring(0, 32);// 退款单号，随机生成 ，但长度应该跟文档一样（32位）(卖家信息校验不一致，请核实后再试)
            String totalFee = CommonUtil.getMoney(data.get("total_fee"));
            String refundFee = CommonUtil.getMoney(data.get("refund_fee"));
            String nonceStr = WXPayUtil.generateNonceStr();
            data.put("appid", WxConfigInfo.APPID);
            data.put("mch_id", WxConfigInfo.MCH_ID);
            data.put("nonce_str", nonceStr);
            data.put("sign_type", WxConfigInfo.SIGN_TYPE);
            data.put("total_fee", totalFee);
            data.put("refund_fee", refundFee);
            data.put("out_refund_no", out_refund_no);
            return wxpay.refund(data);
        } catch (Exception e) {
            log.info("error={}", e.getMessage());
        }
        return null;
    }

    public static String closeOrder(String out_trade_no) {
        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            String nonceStr = WXPayUtil.generateNonceStr();
            data.put("appid", WxConfigInfo.APPID);
            data.put("mch_id", WxConfigInfo.MCH_ID);
            data.put("nonce_str", nonceStr);
            data.put("sign_type", WxConfigInfo.SIGN_TYPE);
            data.put("out_trade_no", out_trade_no);
            String sign = WXPayUtil.generateSignature(data, WxConfigInfo.API_KEY);
            data.put("sign", sign);
            Map<String, String> resultMap = wxpay.closeOrder(data);
            String result = resultMap.get("return_code");
            if (result.equals("SUCCESS")) {
                log.info("微信订单关闭成功");
            } else {
                log.info("微信订单关闭失败");
            }
            return result;
        } catch (Exception e) {
            log.error("关闭订单失败，请重新尝试:={}", e.getMessage());
        }
        return null;
    }

    /**
     * description:获取预支付id
     *
     * @param url
     * @param xmlParam
     * @return String
     */
    public static String getPayNo(String url, String xmlParam) {
        String prepay_id = "";
        try {
            String jsonStr = CommonUtil.getResponseStr(url, xmlParam);
            log.info("统一下单响应---" + jsonStr);
            if (jsonStr.indexOf("FAIL") != -1) {
                return prepay_id;
            }
            Map map = CommonUtil.doXMLParse(jsonStr);
            prepay_id = (String) map.get("prepay_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prepay_id;
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                List<String> list = (List) JSONObject.parseArray(String.valueOf(jsonObject.getJSONArray("privilege")), List.class);
                snsUserInfo.setPrivilegeList(list);
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

}
