package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.alipay.aliPayApi.AliPayApi;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.AssociatorAccountMapper;
import com.lswd.youpin.dao.RechargeLogMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAccount;
import com.lswd.youpin.model.RechargeLog;
import com.lswd.youpin.model.vo.OrderVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RechargeService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.RandomUtil;
import com.lswd.youpin.utils.SerializeUtils;
import com.lswd.youpin.wxpay.WxApi.WxPayApi;
import com.lswd.youpin.wxpay.config.WxConfigInfo;
import com.lswd.youpin.wxpay.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/5.
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    private final Logger log = LoggerFactory.getLogger(RechargeServiceImpl.class);
    @Autowired
    private RechargeLogMapper rechargeLogMapper;

    @Autowired
    private AssociatorAccountMapper associatorAccountMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public String recharge(Map<String, String> data, HttpServletRequest request, HttpServletResponse response) {
        log.info("开始执行充值操作输入的data是========{}", JSON.toJSON(data));
        Associator associator = (Associator) request.getAttribute("associator");
        log.info("当前的会员信息=========={}" + JSON.toJSON(associator));
        //充值类型，0：微信充值，1：代表支付宝充值，
        int operatorType = Integer.parseInt(data.get("operatorType"));
        //因为充值操作没有明确的订单号，这里采用时间戳加6位随机数的方式生成订单号，格式 yyyyMMddHHmmss01
        String out_trade_no = RandomUtil.getRandomCodeStr(6);
        data.put("out_trade_no", out_trade_no);
        String result = null;
        try {
            if (operatorType == 0) {
                log.info("开始执行微信账户充值操作:{}", JSON.toJSON(operatorType));
                data.put("spbill_create_ip", IpUtils.getIpAddr(request));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tradeType", WxConfigInfo.RECHARGE);
                jsonObject.put("canteenId", associator.getCanteenId());
                jsonObject.put("associatorId", associator.getAssociatorId());
                jsonObject.put("memberId",associator.getMemberId());
                data.put("attach", jsonObject.toString());
                result = WxPayApi.getUnifiedOrderInfo(data);
            } else {
                log.info("开始执行支付宝账户充值操作:{}", JSON.toJSON(operatorType));
                OrderVO orderVO = new OrderVO();
                log.info("当前餐厅编号：=====" + associator.getCanteenId());
                orderVO.setCanteenId(associator.getCanteenId());
                orderVO.setTradeType(ConstantsCode.RECHARGE);
                orderVO.setAssociatorId(associator.getAssociatorId());
                orderVO.setMemberId(associator.getMemberId());
                data.put("out_trade_no", out_trade_no);
                redisManager.set(out_trade_no.getBytes(), SerializeUtils.serialize(orderVO), 1800);
                result = AliPayApi.getAliPayForm(data, response);
            }
        } catch (Exception e) {
            log.error("充值失败：={}", e.getMessage());
        }
        return result;
    }

    /**
     * 获取会员的充值记录
     *
     * @param associatorId
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public LsResponse getRecharge(String associatorId, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        log.info("按时间段查询会员的充值记录={}");
        LsResponse lsResponse = new LsResponse();
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            List<RechargeLog> rechargeLogs = rechargeLogMapper.getRechargeLog(associatorId, startTime, endTime, offset, pageSize);
            Integer count = rechargeLogMapper.getTotalCount(associatorId, startTime, endTime);
            lsResponse.setData(rechargeLogs);
            lsResponse.setTotalCount(count);
            return lsResponse;
        } catch (Exception e) {
            lsResponse.setAsFailure();
            log.error("会员充值记录查询失败={}", e.getMessage());
        }
        return null;
    }

    /**
     * 1、首先查出会员的账户余额，会员的账户余额包括两部分:微信账户余额，支付宝账户余额
     * 2、退款时，微信账户余额中的钱找微信退，支付宝账户余额中的钱找支付宝退
     * 3、退款是根据充值单号进行退款
     * 4、账户余额的钱可能是分多次充入，需要根据多个充值单号进行退款
     *
     * @param associatorId
     * @return
     */
    @Override
    public LsResponse refund(String associatorId) {
        log.info("会员退款={}", JSON.toJSON(associatorId));
        LsResponse lsResponse = new LsResponse();
        List<AssociatorAccount> associatorAccount;
        List<Map<String, String>> wxTradeNo = new ArrayList<>();
        List<Map<String, String>> aliTradeNo = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer("");
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        //个人账户-微信余额
        Double wxBalance = 0.0;
        //个人账户-支付宝余额
        Double aliBalance = 0.0;
        //个人账户-微信余额
        Double wxCredit = 0.0;
        //个人账户-支付宝余额
        Double aliCredit = 0.0;
        //使用微信充值的金额
        Float wxRecharge =0F;
        //使用支付宝充值的金额
        Float aliRecharge = 0F;
        //查询会员账户余额信息
        try {
            associatorAccount = associatorAccountMapper.getInfo(associatorId);
            for (AssociatorAccount a : associatorAccount) {
                totalBalance += a.getBalance();
                totalCredit += a.getCredit();
                switch (a.getAccountType()) {
                    case 0: {
                        wxBalance = a.getBalance();
                        wxCredit = a.getCredit();
                    }
                    break;
                    case 1: {
                        aliBalance = a.getBalance();
                        aliCredit = a.getCredit();
                    }
                    break;
                    default:
                        break;
                }
            }
            totalBalance = wxBalance + aliBalance;
            totalCredit = wxCredit + aliCredit;
            log.info("totalBalance======" + totalBalance + "totalCredit===========" + totalCredit);
            if (totalBalance.doubleValue() != totalCredit.doubleValue()) {
                lsResponse.setAsFailure();
                lsResponse.setMessage("存在未刷卡的订单，请刷卡之后再退款");
                return lsResponse;
            }
            if(totalBalance==0.00){
                lsResponse.setAsFailure();
                lsResponse.setMessage("账户余额0，没有余额可退");
                return lsResponse;
            }
            //查询充值记录，根据账户余额和充值记录进行退款
            List<RechargeLog> rechargeLogs = rechargeLogMapper.getRechargeLogById(associatorId);
            log.info("会员的充值记录============="+JSON.toJSON(rechargeLogs));
            for (RechargeLog rechargeLog : rechargeLogs) {
                if (rechargeLog.getRechargeType() == ConstantsCode.WX) {
                    wxRecharge = rechargeLog.getRechargeMoney();
                    if (wxRecharge < wxBalance) {
                        Map<String, String> map = new HashMap<>();
                        map.put("out_trade_no", rechargeLog.getOutTradeNo());
                        map.put("total_fee", String.valueOf(wxRecharge));
                        map.put("refund_fee", String.valueOf(wxRecharge));
                        wxTradeNo.add(map);
                        wxBalance = wxBalance - wxRecharge;
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("out_trade_no", rechargeLog.getOutTradeNo());
                        map.put("total_fee", String.valueOf(wxRecharge));
                        map.put("refund_fee", String.valueOf(wxBalance));
                        wxTradeNo.add(map);
                        break;
                    }
                }
            }
            for (RechargeLog rechargeLog : rechargeLogs) {
                if (rechargeLog.getRechargeType() == ConstantsCode.ALI) {
                    aliRecharge = rechargeLog.getRechargeMoney();
                    if (aliRecharge < aliBalance) {
                        Map<String, String> map = new HashMap<>();
                        map.put("total_fee", String.valueOf(aliRecharge));
                        map.put("refund_amount", String.valueOf(aliRecharge));
                        map.put("out_trade_no", rechargeLog.getOutTradeNo());
                        map.put("refund_reason", "退款");
                        aliTradeNo.add(map);
                        aliBalance = aliBalance - aliRecharge;
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("total_fee", String.valueOf(aliRecharge));
                        map.put("refund_amount", String.valueOf(aliBalance));
                        map.put("out_trade_no", rechargeLog.getOutTradeNo());
                        map.put("refund_reason", "退款");
                        aliTradeNo.add(map);
                        break;
                    }
                }
            }
            for (Map<String, String> map : wxTradeNo) {
                Map<String, String> result = WxPayApi.refund(map);
                if (result != null && result.get("return_code").equals("SUCCESS")) {
                    stringBuffer.append("成功退款" + result.get("total_amount") + ",");
                    //退款成功进行自己的业务逻辑处理
                } else {
                    stringBuffer.append("退款失败" + result.get("total_amount") + "请稍后继续退款");
                }

            }
            for (Map<String, String> map : aliTradeNo) {
                JSONObject result = JSONObject.parseObject(AliPayApi.refund(map));
                if (result != null && result.get("code").equals("10000")) {
                    stringBuffer.append("成功退款" + result.get("total_amount") + ",");
                    //退款成功进行自己的业务逻辑处理
                } else {
                    stringBuffer.append("退款失败" + result.get("total_amount") + "请稍后继续退款");
                }
            }
            associatorAccountMapper.updateAccount(associatorId);
            lsResponse.setAsSuccess();
            lsResponse.setMessage(stringBuffer.toString());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.setAsFailure();
            log.error("退款失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAccountInfo(String associatorId) {
        log.info("按时间段查询会员的充值记录={}", JSON.toJSON(associatorId));
        LsResponse lsResponse = new LsResponse();
        try {
            AssociatorAccount associatorAccount = associatorAccountMapper.getAccountInfo(associatorId);
            lsResponse.setData(associatorAccount);
            return lsResponse;
        } catch (Exception e) {
            lsResponse.setAsFailure();
            log.error("会员账户信息查询失败={}", e.getMessage());
        }
        return null;
    }
}
