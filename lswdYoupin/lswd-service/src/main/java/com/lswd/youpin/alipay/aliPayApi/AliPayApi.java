package com.lswd.youpin.alipay.aliPayApi;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.lswd.youpin.alipay.config.AliPayConfig;
import com.lswd.youpin.alipay.util.AliPayClientUtil;
import com.lswd.youpin.alipay.util.AlipayCore;
import com.lswd.youpin.alipay.util.AlipaySubmit;
import com.lswd.youpin.utils.RandomUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liruilong on 2017/7/6.
 */
public class AliPayApi {

    private final static Logger log = LoggerFactory.getLogger(AliPayApi.class);

    public static String getAliPayForm(Map<String, String> data, HttpServletResponse httpResponse) throws UnsupportedEncodingException {
        log.info("执行AliPayApi中的getAliPayForm方法，获取支付宝支付返回的参数");
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
        sParaTemp.put("partner", AliPayConfig.PARTNER);
        sParaTemp.put("seller_id", AliPayConfig.PARTNER);
        sParaTemp.put("_input_charset", AliPayConfig.INPUT_CHARSET);
        sParaTemp.put("notify_url", AliPayConfig.NOTIFY_URL);
        sParaTemp.put("return_url", AliPayConfig.RETURN_URL);
        sParaTemp.put("out_trade_no", data.get("out_trade_no"));
        sParaTemp.put("app_pay", "Y");
        sParaTemp.put("subject", "商品支付");
        sParaTemp.put("total_fee", data.get("total_fee"));
        sParaTemp.put("body", "商品选购");
        sParaTemp.put("payment_type", AliPayConfig.PAYMENT_TYPE);
        Map<String, String> result = AlipaySubmit.buildRequestPara(sParaTemp);
        String action = "https://mapi.alipay.com/gateway.do";
        String link = AlipayCore.createLinkString(result);
        String resultUrl = action + "?" + link;
        log.info("支付宝支付请求url={}", resultUrl);
        return resultUrl;
    }

    public static String refund(Map<String, String> data) {
        AlipayClient alipayClient = AliPayClientUtil.getInstance();
        AlipayTradeRefundRequest aliPayRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        String out_trade_no = MapUtils.getString(data, "out_trade_no");
        //支付宝交易号，和商户订单号二选一
        //String trade_no = MapUtils.getString(data, "trade_no");
        //退款金额，不能大于订单总金额
        String refund_amount = MapUtils.getString(data, "refund_amount");
        //退款的原因说明
        String refund_reason = MapUtils.getString(data, "refund_reason");
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        String out_request_no = UUID.randomUUID().toString().substring(0, 32);
        model.setOutTradeNo(out_trade_no);
        model.setRefundAmount(refund_amount);
        model.setRefundReason(refund_reason);
        model.setOutRequestNo(out_request_no);
        aliPayRequest.setBizModel(model);
        try {
            log.info("支付宝开始退款");
            AlipayTradeRefundResponse response = alipayClient.execute(aliPayRequest);
            String result = JSON.toJSONString(response);
            return result;
        } catch (Exception e) {
            log.error("退款失败={}", e.getMessage());
        }
        return null;
    }

    /**
     * 支付宝签名
     *
     * @param array
     * @return
     */
    public static String signAllString(String[] array) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < array.length; i++) {
            if (i == (array.length - 1)) {
                sb.append(array[i]);
            } else {
                sb.append(array[i] + "&");
            }
        }
        System.out.println(sb.toString());
        String sign = "";
        try {
            try {
                sign = URLEncoder.encode(AlipaySignature.rsa256Sign(sb.toString(), AliPayConfig.APP_PRIVATE_KEY, "utf-8"));//private_key私钥
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("&sign=\"" + sign + "\"&");
        sb.append("sign_type=\"RSA\"");
        return sb.toString();
    }

    public static String closeOrder(String out_trade_no) {
        //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
        //商户订单号，和支付宝交易号二选一
        AlipayClient alipayClient = AliPayClientUtil.getInstance();
        AlipayTradeCloseRequest aliPayRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(out_trade_no);
        aliPayRequest.setBizModel(model);
        String result = "";
        try {
            AlipayTradeCloseResponse aliPay_response = alipayClient.execute(aliPayRequest);
            result = aliPay_response.getBody();
            log.info("支付宝关闭订单返回的参数是：={}", result);
        } catch (Exception e) {
            log.info("支付宝关闭订单异常={}", e.getMessage());
        }
        return result;
    }

    public static String getOrderInfo(String out_trade_no) throws AlipayApiException {

        AlipayClient alipayClient = AliPayClientUtil.getInstance();
        AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(out_trade_no);
        alipay_request.setBizModel(model);
        AlipayTradeQueryResponse alipay_response = alipayClient.execute(alipay_request);
        String result = alipay_response.getBody();
        return result;
    }
}
