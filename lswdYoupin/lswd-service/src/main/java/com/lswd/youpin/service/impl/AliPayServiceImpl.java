package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.lswd.youpin.alipay.aliPayApi.AliPayApi;
import com.lswd.youpin.alipay.util.AlipayNotify;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.model.vo.OrderVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AliPayService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.RandomUtil;
import com.lswd.youpin.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/4.
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    private final Logger log = LoggerFactory.getLogger(AliPayServiceImpl.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private GoodOrderMapper goodOrderMapper;

    @Autowired
    private RecipeOrderMapper recipeOrderMapper;

    @Autowired
    private UnifiedOrderMapper unifiedOrderMapper;

    @Autowired
    private GoodOrderItemsMapper goodOrderItemsMapper;

    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private RecipeOrderItemsMapper recipeOrderItemsMapper;

    @Autowired
    private RecipePlanItemsMapper recipePlanItemsMapper;

    @Autowired
    private MemberPayBillMapper memberPayBillMapper;

    @Override
    public String getAliPayForm(Map<String, String> data, HttpServletRequest request, HttpServletResponse response) {
        log.info("getAliPayForm==========data中的数据是={}", JSON.toJSON(data));
        try {
            Associator associator = (Associator) request.getAttribute("associator");
            OrderVO orderVO = new OrderVO();
            orderVO.setCanteenId(associator.getCanteenId());
            orderVO.setOrders(data.get("out_trade_nos"));
            orderVO.setTradeType(data.get("tradeType"));
            orderVO.setMemberId(associator.getMemberId());
            String out_trade_no = RandomUtil.getRandomCodeStr(6);
            data.put("out_trade_no", out_trade_no);
            redisManager.set(out_trade_no.getBytes(), SerializeUtils.serialize(orderVO), 3600);
            return AliPayApi.getAliPayForm(data, response);
        } catch (Exception e) {
            log.info("getAliPayForm抛出异常={}", e.getMessage());
        }
        return null;
    }

    /**
     * 支付宝同步回调函数说明：
     * 用户在支付宝APP或H5收银台完成支付后，会根据商户在手机网站支付API中传入的前台回跳地址return_url自动跳转回商户页面，
     * 同时在URL请求中以Query String的形式附带上支付结果参数
     * 注意：由于前台回跳的不可靠性，前台回跳只能作为商户支付结果页的入口，最终支付结果必须以异步通知或查询接口返回为准，不能依赖前台回跳
     *
     * @param request
     * @param response
     * @throws AlipayApiException
     * @throws IOException
     */
    @Override
    public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {

    }

    /**
     * 支付宝异步通知说明:
     * 1、必须保证服务器异步通知页面（notify_url）上无任何字符，如空格、HTML标签、开发系统自带抛出的异常提示信息等；
     * 2、支付宝是用POST方式发送通知信息，因此该页面中获取参数的方式，如：request.Form(“out_trade_no”)、$_POST[‘out_trade_no’]；
     * 3、支付宝主动发起通知，该方式才会被启用；
     * 4、只有在支付宝的交易管理中存在该笔交易，且发生了交易状态的改变，支付宝才会通过该方式发起服务器通知（即时到账交易状态为“等待买家付款”的状态默认是不会发送通知的）；
     * 服务器间的交互，不像页面跳转同步通知可以在页面上显示出来，这种交互方式是不可见的；
     * 5、第一次交易状态改变（即时到账中此时交易状态是交易完成）时，不仅会返回同步处理结果，而且服务器异步通知页面也会收到支付宝发来的处理结果通知；
     * 6、程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。一般情况下，25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；
     * 7、程序执行完成后，该页面不能执行页面跳转。如果执行页面跳转，支付宝会收不到success字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知；
     * 8、cookies、session等在此页面会失效，即无法获取这些数据；
     * 9、该方式的调试与运行必须在服务器上，即互联网上能访问；
     * 10、该方式的作用主要防止订单丢失，即页面跳转同步通知没有处理订单更新，它则去处理；
     * 11、当商户收到服务器异步通知并打印出success时，服务器异步通知参数notify_id才会失效。也就是说在支付宝发送同一条异步通知时（包含商户并未成功打印出success导致支付宝重发数次通知），服务器异步通知参数notify_id是不变的。
     * 注意：
     * 1、商户系统接收到异步通知以后，必须通过验签（验证通知中的sign参数）来确保支付通知是由支付宝发送的。详细验签规则参考异步通知验签。/
     * 2、接受到异步通知并验签通过后，一定要检查通知内容，包括通知中的app_id, out_trade_no, total_amount是否与请求中的一致，并根据trade_status进行后续业务处理
     *
     * @param request
     * @param response
     * @throws AlipayApiException
     * @throws IOException
     */
    @Override
    public void notifyUrl(Map<String, String> params, OrderVO orderVO, HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {

        log.info("【移动端支付宝异步回调参数 params】======" + JSON.toJSON(params));
        String icon = "success";
        String total_fee = params.get("total_fee");
        String out_trade_no = params.get("out_trade_no");
        String trade_status = params.get("trade_status") == null ? "" : params.get("trade_status");
        //同一个订单如果一次退换则 trade_status=TRADE_CLOSED 这种情况是不触发异步通知函数的，一个订单分多次退款当没有全部退还 trade_status=TRADE_SUCCESS
        String refund_status = params.get("refund_status") == null ? "" : params.get("refund_status");
        boolean flag = redisManager.get(out_trade_no.getBytes()) == null ? true : false;
        if (flag) {
            redisManager.set(out_trade_no.getBytes(), "".getBytes(), 90000);
            return;
        }
        boolean verify_result = AlipayNotify.verify(params);
        if (verify_result) {//验证成功
            if ((trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) && (refund_status.equals("") || refund_status == null)) {
                String tradeType = orderVO.getTradeType();
                if (tradeType.equals(ConstantsCode.RECHARGE)) {
                    log.info("支付宝账户充值异步通知函数");
                    try {
                        String memberId=orderVO.getMemberId();
                        log.info("memberId==============="+memberId);
                        memberCardMapper.updateBalance(memberId,Float.valueOf(total_fee));
                        //添加会员充值记录
                        MemberPayBill memberPayBill = new MemberPayBill();
                        memberPayBill.setMoney(Float.valueOf(total_fee));
                        memberPayBill.setOutTradeNo(out_trade_no);
                        memberPayBill.setPayTime(Dates.now());
                        //0:线下充值，1：微信，2：支付宝
                        memberPayBill.setPayType(2);
                        memberPayBill.setMemberId(memberId);
                        memberPayBill.setUserId(orderVO.getAssociatorId());
                        memberPayBillMapper.insertSelective(memberPayBill);
                        log.info("支付成功！");
                    } catch (Exception e) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        log.info("支付失败：={}", e.getMessage());
                        icon = "fail";
                    }
                } else {
                    String out_trade_nos = orderVO.getOrders();
                    log.info("支付宝购买操作");
                    //用微信支付下单操作，将订单从临时表中转移到已支付表中
                    String[] tradeNumbers = out_trade_nos.split(",");
                    try {
                        UnifiedOrder unified = new UnifiedOrder();
                        unified.setOrderNo(out_trade_no);
                        unified.setOrderType(ConstantsCode.ALI);
                        unified.setAmount(Float.valueOf(total_fee));
                        unifiedOrderMapper.insertSelective(unified);
                        int id = unified.getId();
                        log.info("支付宝支付将订单合并之后添加到t_unified_order表中,添加成功之后返回的主键是：={}", id);
                        for (String orderNo : tradeNumbers) {
                            if (orderNo.charAt(orderNo.length() - 1) == '1') {
                                log.info("支付宝购买商品操作");
                                //将临时表中的订单查出来
                                GoodOrder goodOrder = goodOrderMapper.getGoodOrder(orderNo, "t_good_order_tmp");
                                goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(orderNo, "t_good_order_tmp");
                                goodOrder.setUnifiedId(id);
                                goodOrder.setPayType(ConstantsCode.ALI);
                                goodOrder.setStatus(ConstantsCode.PAID);
                                goodOrder.setUpdateTime(Dates.now());
                                goodOrderMapper.insertGoodOrder(goodOrder, "t_good_order_paid");
                            } else {
                                log.info("支付宝购买菜品操作");
                                //将临时表中的订单查出来
                                RecipeOrder recipeOrder = recipeOrderMapper.getRecipeOrder(orderNo, "t_recipe_order_tmp");
                                recipeOrderMapper.deleteByOrderNo(orderNo, "t_recipe_order_tmp");
                                recipeOrder.setUnifiedId(id);
                                recipeOrder.setPayType(ConstantsCode.ALI);
                                recipeOrder.setStatus(ConstantsCode.PAID);
                                recipeOrder.setUpdateTime(Dates.now());
                                log.info("从菜品临时表中转移到已支付表中的菜品================{}", JSON.toJSON(recipeOrder));
                                recipeOrderMapper.insertOrderPaid(recipeOrder);
                            }
                        }

                    } catch (Exception e) {
                        log.info("支付失败：={}", e.getMessage());
                        icon = "fail";
                    }
                }
            }
            Writer write = response.getWriter();
            write.write(icon);
            write.flush();
            write.close();
        } else {//验证失败
            log.info("验证失败");
        }
    }

    @Override
    public LsResponse refund(Map<String, String> data, HttpServletRequest request) {
        LsResponse lsResponse = new LsResponse();
        String canteenId = request.getHeader("canteenId");
        String out_trade_no = data.get("out_trade_no");
        String table;
        if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
            log.info("商品退款申请，订单号==========" + out_trade_no);
            table = "t_good_order_paid";
        } else {
            table = "t_recipe_order_paid";
        }
        try {
            UnifiedOrder unifiedOrder = unifiedOrderMapper.getUnifiedOrderByOrderNo(out_trade_no, table);
            log.info("unifiedOrder===========" + JSON.toJSON(unifiedOrder));
            Map<String, String> map = new HashMap<>();
            map.put("out_trade_no", unifiedOrder.getOrderNo());
            map.put("refund_amount", data.get("refund_fee"));
            map.put("refund_reason", data.get("refund_reason"));
            JSONObject result = JSONObject.parseObject(AliPayApi.refund(map));
            if (result != null && result.get("code").equals("10000")) {
                log.info("退款成功====================");
                if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
                    log.info("商品订单转移");
                    GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(out_trade_no, "t_good_order_paid");
                    goodOrder.setUpdateTime(Dates.now());
                    goodOrder.setUpdateTime(Dates.now());
                    goodOrder.setStatus(ConstantsCode.REFUND);
                    goodOrderMapper.insertGoodOrder(goodOrder, "t_good_order_refund");
                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(out_trade_no, "t_good_order_paid");
                    log.info("退款成功，将相应商品的数量增加");
                    List<GoodOrderItems> goodOrderItemsList = goodOrderItemsMapper.getGoodOrderItemsList(out_trade_no);
                    for (GoodOrderItems goodOrderItems : goodOrderItemsList) {
                        goodPlanItemsMapper.updateGoodPlanSurPlus(goodOrderItems.getPlanId(), goodOrderItems.getGoodId(), goodOrderItems.getQuantity());
                    }
                } else {
                    log.info("菜品订单转移");
                    RecipeOrder recipeOrders = recipeOrderMapper.getRecipeOrder(out_trade_no, "t_recipe_order_paid");
                    recipeOrders.setUpdateTime(Dates.now());
                    recipeOrders.setCreateTime(Dates.now());
                    recipeOrders.setStatus(ConstantsCode.REFUND);
                    recipeOrderMapper.insertOrderRefund(recipeOrders);
                    recipeOrderMapper.deleteByOrderNo(out_trade_no, "t_recipe_order_paid");
                    List<RecipeOrderItems> recipeOrderItemses = recipeOrderItemsMapper.getRecipeOrderItemsList(out_trade_no);
                    //菜品订单中 1：代表早餐，2：代表午餐，3：代笔晚餐
                    //菜品订单详情表中 0：代表早餐，1：代表午餐，2：代笔晚餐
                    for (RecipeOrderItems recipeOrderItems : recipeOrderItemses) {
                        recipePlanItemsMapper.updateSurPlus(recipeOrderItems.getPlanId(), recipeOrderItems.getRecipeId(), recipeOrders.getPickType()-1, recipeOrderItems.getQuantity()
                        );
                    }
                }
                lsResponse.setAsSuccess();
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage(String.valueOf(result.get("sub_msg")));
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("退款操作中抛出异常={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("退款失败" + e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse query(String out_trade_nos) {
        log.info("支付宝订单查询操作，传入的订单集合是：========" + out_trade_nos);
        LsResponse lsResponse = new LsResponse();
        String[] orderNos = out_trade_nos.split(",");
        UnifiedOrder unifiedOrder;
        String firstOrder = orderNos[0];
        if (firstOrder.charAt(firstOrder.length() - 1) == '1') {
            unifiedOrder = unifiedOrderMapper.getUnifiedOrderByOrderNo(firstOrder, "t_good_order_paid");
        } else {
            unifiedOrder = unifiedOrderMapper.getUnifiedOrderByOrderNo(firstOrder, "t_recipe_order_paid");
        }
        if (unifiedOrder != null) {
            lsResponse.setAsFailure();
        } else {
            lsResponse.setAsSuccess();
        }
        return lsResponse;
    }
}
