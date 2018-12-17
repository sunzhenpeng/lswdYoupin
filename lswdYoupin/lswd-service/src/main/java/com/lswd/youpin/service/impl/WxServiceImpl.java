package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.RechargeLogMapper;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.WxService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.SerializeUtils;
import com.lswd.youpin.wxpay.WxApi.WxPayApi;
import com.lswd.youpin.wxpay.config.WxConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/7.
 */

@Service
public class WxServiceImpl implements WxService {

    @Autowired
    private GoodOrderMapper goodOrderMapper;

    @Autowired
    private RecipeOrderMapper recipeOrderMapper;

    @Autowired
    private UnifiedOrderMapper unifiedOrderMapper;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private GoodOrderItemsMapper goodOrderItemsMapper;

    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private RecipeOrderItemsMapper recipeOrderItemsMapper;

    @Autowired
    private RecipePlanItemsMapper recipePlanItemsMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private MemberPayBillMapper memberPayBillMapper;

    private final Logger log = LoggerFactory.getLogger(WxServiceImpl.class);

    /**
     * 调用统一下单接口获取prepay_id
     *
     * @param data
     * @return
     */
    @Override
    public String getPackage(Map<String, String> data, HttpServletRequest servletRequest) {
        log.info("{} h5支付传入的参数 = {}", "getPackage", JSON.toJSON(data));
        Associator associator = (Associator) servletRequest.getAttribute("associator");
        log.info("调用微信统一下单接口，当前的会员信息==============={}", JSON.toJSON(associator));
        LsResponse lsResponse = new LsResponse();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tradeType", WxConfigInfo.GOOD_PAY);
            //多个订单同时支付，所以传进来多个订单号，多个订单号以“,”隔开
            jsonObject.put("out_trade_nos", data.get("out_trade_nos"));
            jsonObject.put("canteenId", associator.getCanteenId());
            data.put("attach", jsonObject.toJSONString());
            data.put("openid", associator.getAssociatorWx());
            String result = WxPayApi.getUnifiedOrderInfo(data);
            return result;
        } catch (Exception e) {
            log.error("获取统一下单返回参数失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return null;
    }

    /**
     * 申请退款
     *
     * @param data
     * @return
     */
    @Override
    public LsResponse refund(Map<String, String> data) {
        LsResponse lsResponse = new LsResponse();
        boolean flag = false;
        try {
            String out_trade_no = data.get("out_trade_no");
            String table;
            if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
                log.info("商品退款申请，订单号==========" + out_trade_no);
                table = "t_good_order_paid";
            } else {
                table = "t_recipe_order_paid";
            }
            UnifiedOrder unifiedOrder = unifiedOrderMapper.getUnifiedOrderByOrderNo(out_trade_no, table);
            log.info("unifiedOrder====================" + JSON.toJSON(unifiedOrder));
            Map<String, String> refundData = new HashMap<>();
            refundData.put("out_trade_no", unifiedOrder.getOrderNo());
            refundData.put("total_fee", String.valueOf(unifiedOrder.getAmount()));
            refundData.put("refund_fee", data.get("refund_fee"));
            refundData.put("refund_desc", data.get("refund_reason"));
            Map<String, String> resultMap = WxPayApi.refund(refundData);
            log.info("微信退款接口返回的数据是===============" + JSON.toJSON(resultMap));
            if (resultMap.get("return_code").equals("SUCCESS") && resultMap.get("result_code").equals("SUCCESS")) {
                log.info("退款成功====================");
                if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
                    log.info("商品订单迁移，订单号==========" + out_trade_no);
                    //将已支付中的订单查出来
                    GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(out_trade_no, "t_good_order_paid");
                    goodOrder.setUpdateTime(Dates.now());
                    goodOrder.setCreateTime(Dates.now());
                    goodOrder.setStatus(ConstantsCode.REFUND);
                    goodOrderMapper.insertGoodOrder(goodOrder, "t_good_order_refund");
                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(out_trade_no, "t_good_order_paid");
                    log.info("退款成功，将相应商品的数量增加");
                    List<GoodOrderItems> goodOrderItemsList = goodOrderItemsMapper.getGoodOrderItemsList(out_trade_no);
                    for (GoodOrderItems goodOrderItems : goodOrderItemsList) {
                        goodPlanItemsMapper.updateGoodPlanSurPlus(goodOrderItems.getPlanId(), goodOrderItems.getGoodId(), goodOrderItems.getQuantity());
                    }
                } else {
                    log.info("菜品订单迁移，订单号==========" + out_trade_no);
                    //将临时表中的订单查出来
                    RecipeOrder recipeOrders = recipeOrderMapper.getRecipeOrder(out_trade_no, "t_recipe_order_paid");
                    log.info("从临时表表中查出的订单信息============={}", JSON.toJSON(recipeOrders));
                    recipeOrderMapper.deleteByOrderNo(out_trade_no, "t_recipe_order_paid");
                    recipeOrders.setUpdateTime(Dates.now());
                    recipeOrders.setCreateTime(Dates.now());
                    recipeOrders.setStatus(ConstantsCode.REFUND);
                    recipeOrderMapper.insertOrderRefund(recipeOrders);
                    List<RecipeOrderItems> recipeOrderItemses = recipeOrderItemsMapper.getRecipeOrderItemsList(out_trade_no);
                    //菜品订单中 1：代表早餐，2：代表午餐，3：代笔晚餐
                    //菜品订单详情表中 0：代表早餐，1：代表午餐，2：代笔晚餐
                    for (RecipeOrderItems recipeOrderItems : recipeOrderItemses) {
                        recipePlanItemsMapper.updateSurPlus(recipeOrderItems.getPlanId(), recipeOrderItems.getRecipeId(), recipeOrders.getPickType() - 1, recipeOrderItems.getQuantity()
                        );
                    }
                }
                flag = true;
            } else {
                lsResponse.setMessage(resultMap.get("err_code_des"));
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.setAsFailure();
            log.error("退款失败：{}", e.getMessage());
        }
        lsResponse.setSuccess(flag);
        return lsResponse;
    }

    /**
     * 微信支付回调函数
     * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，
     * 尽可能提高通知的成功率，但微信不保证通知最终能成功。 （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）。
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
     * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失
     *
     * @return
     */
    public void wxNotify(Map<Object, Object> packageParams, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //是否需要过滤掉重复的回调通知
        String resXml;
        String out_trade_no = String.valueOf(packageParams.get("out_trade_no"));
        //boolean flag = redisManager.get(out_trade_no.getBytes()) == null ? true : false;
        boolean flag = redisManager.get(out_trade_no.getBytes()) == null ? false : true;
        if (flag) {
            redisManager.set(out_trade_no.getBytes(), "".getBytes(), 11040);
            return;
        }
        try {
            if ("SUCCESS".equals(String.valueOf(packageParams.get("result_code")))) {
                log.info("微信通知支付成功，接下来执行自己的业务逻辑");
                String jsonAttach = String.valueOf(packageParams.get("attach"));
                JSONObject jsonObject = JSON.parseObject(jsonAttach);
                String associatorId = String.valueOf(jsonObject.get("associatorId"));
                String tradeType = String.valueOf(jsonObject.get("tradeType"));
                String out_trade_nos = String.valueOf(jsonObject.get("out_trade_nos"));//订单集合
                log.info("传入的订单集合是========" + out_trade_nos);
                String totalFee = String.valueOf(packageParams.get("total_fee"));
                Float total_fee = Float.valueOf(totalFee) / 100;
                //首先判断是微信充值操作还是微信购买操作
                if (tradeType.equals(WxConfigInfo.RECHARGE)) {
                    log.info("微信充值异步通知函数");
                    String memberId = String.valueOf(jsonObject.get("memberId"));
                    memberCardMapper.updateBalance(memberId, Float.valueOf(total_fee));
                    log.info("将会员充值记录添加到会员充值记录表中");
                    //添加会员充值记录
                    MemberPayBill memberPayBill = new MemberPayBill();
                    memberPayBill.setMoney(total_fee);
                    memberPayBill.setOutTradeNo(out_trade_no);
                    memberPayBill.setPayTime(Dates.now());
                    memberPayBill.setUserId(associatorId);
                    //0:线下充值，1：微信，2：支付宝
                    memberPayBill.setPayType(1);
                    memberPayBill.setMemberId(memberId);
                    memberPayBillMapper.insertSelective(memberPayBill);
                } else {
                    log.info("微信购买操作");
                    //用微信支付下单操作，将订单从临时表中转移到已支付表中
                    String[] tradeNumbers = out_trade_nos.split(",");
                    UnifiedOrder unifiedOrder = new UnifiedOrder();
                    unifiedOrder.setOrderNo(out_trade_no);
                    unifiedOrder.setOrderType(ConstantsCode.WX);
                    unifiedOrder.setAmount(total_fee);
                    unifiedOrderMapper.insertSelective(unifiedOrder);
                    int key = unifiedOrder.getId();
                    for (String orderNo : tradeNumbers) {
                        if (orderNo.charAt(orderNo.length() - 1) == '1') {
                            log.info("微信购买商品操作");
                            //将临时表中的订单查出来
                            GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderNo, "t_good_order_tmp");
                            goodOrder.setUnifiedId(key);
                            goodOrder.setPayType(ConstantsCode.WX);
                            goodOrder.setStatus(ConstantsCode.PAID);
                            goodOrder.setUpdateTime(Dates.now());
                            goodOrderMapper.insertGoodOrder(goodOrder, "t_good_order_paid");
                            goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(orderNo, "t_good_order_tmp");
                        } else {
                            log.info("微信购买菜品操作");
                            //将临时表中的订单查出来
                            RecipeOrder recipeOrders = recipeOrderMapper.getRecipeOrder(orderNo, "t_recipe_order_tmp");
                            log.info("从临时表表中查出的订单信息============={}", JSON.toJSON(recipeOrders));
                            recipeOrderMapper.deleteByOrderNo(orderNo, "t_recipe_order_tmp");
                            recipeOrders.setUnifiedId(key);
                            recipeOrders.setPayType(ConstantsCode.WX);
                            recipeOrders.setStatus(ConstantsCode.PAID);
                            recipeOrders.setUpdateTime(Dates.now());
                            recipeOrderMapper.insertOrderPaid(recipeOrders);
                        }
                    }
                }
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                // 通知微信.交易失败.
                log.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        } catch (Exception e) {
            log.info("支付失败,抛出异常：" + packageParams.get("err_code"));
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @Override
    public String getQrCode(String orderNo) {
        log.info("取货订单号===============" + orderNo);
        String result = "";
        try {
            if (orderNo.charAt(orderNo.length() - 1) == '1') {
                log.info("准备生成商品订单取货二维码");
                GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderNo, "t_good_order_paid");
                result = JSON.toJSONString(goodOrder);
            } else {
                RecipeOrder recipeOrder = recipeOrderMapper.getRecipeOrder(orderNo, "t_recipe_order_paid");
                result = JSON.toJSONString(recipeOrder);
            }
        } catch (Exception e) {
            log.info("取货二维码生成失败：={}", e.getMessage());
        }
        return result;
    }

    @Override
    public LsResponse query(String out_trade_nos) {
        log.info("微信订单查询操作，传入的订单集合是：========" + out_trade_nos);
        LsResponse lsResponse = new LsResponse();
        String[] orderNos = out_trade_nos.split(",");
        Object object = null;
        String firstOrder = orderNos[0];
        try {
            if (firstOrder.charAt(firstOrder.length() - 1) == '1') {
                object = goodOrderMapper.getGoodOrderByGoodOrderId(firstOrder, "t_good_order_paid"); //unifiedOrderMapper.getUnifiedOrderByOrderNo(firstOrder, "t_good_order_paid");
            } else {
                object = recipeOrderMapper.getOrderInPaidByOrderId(firstOrder);
            }
        } catch (Exception e) {
            log.info("微信订单查询异常：={}", e.getMessage());
        }
        if (object != null) {
            lsResponse.setAsSuccess();
        } else {
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getJSSDKConfig(String url) {
        log.info("传入的url是===================" + url);
        LsResponse lsResponse = new LsResponse();
        try {
            String nonce_str = WXPayUtil.generateNonceStr();
            String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
            String ticket;
            Object object = SerializeUtils.deserialize(redisManager.get("ticket".getBytes()));
            if (object == null) {
                ticket = WxPayApi.getTicket();
                redisManager.set("ticket".getBytes(), SerializeUtils.serialize(ticket), 7200);
            } else {
                ticket = String.valueOf(object);
            }
            StringBuffer str = new StringBuffer("jsapi_ticket=");
            str.append(ticket).
                    append("&noncestr=").
                    append(nonce_str).
                    append("&timestamp=").
                    append(timestamp).
                    append("&url=").
                    append(url);
           /*String string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str
                    + "&timestamp=" + timestamp + "&url=" + url;*/
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(String.valueOf(str).getBytes("UTF-8"));
            String signature = WxPayApi.byteToHex(crypt.digest());
            HashMap<String, String> jssdk = new HashMap<>();
            jssdk.put("appId", WxConfigInfo.APPID);
            jssdk.put("timestamp", timestamp);
            jssdk.put("nonceStr", nonce_str);
            jssdk.put("signature", signature);
            log.info("jssdk============================{}" + jssdk);
            lsResponse.setData(jssdk);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.setAsFailure();
            log.info("出错啦====================={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getOpenId(String code) {
        //String oauth_url = WxConfigInfo.OAUTH2_URL.replace("APPID", WxConfigInfo.APPID).replace("SECRET", WxConfigInfo.APP_SECRECT).replace("CODE", String.valueOf(code));
        // String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3b6b75804a1cd6ba&redirect_uri=https%3a%2f%2fwww.u-meal.com.cn%2flsyp%2fmember%2flogin&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        //JSONObject jsonObject = CommonUtil.httpsRequestToJsonObject(url, "GET", null);
        //  log.info("从微信服务器获取到的code值是======code======" + jsonObject);
        log.info("从微信服务器获取到的code值是======code======" + code);
        LsResponse lsResponse = new LsResponse();
        try {
            String openId = WxPayApi.getOpenId(code);
            boolean flag = (openId.equals("")||openId==null) ? false : true;
            lsResponse.setSuccess(flag);
            lsResponse.setData(openId);
        } catch (Exception e) {
            lsResponse.setAsFailure();
            log.error("获取openId 失败==={}", e.getMessage());
        }
        return lsResponse;
    }
}
