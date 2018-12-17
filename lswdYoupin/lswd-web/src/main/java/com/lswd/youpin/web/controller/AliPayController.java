package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.AliPayThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AliPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/4.
 */
@Api(value = "aliPay", tags = "aliPay", description = "支付宝支付")
@RestController
@RequestMapping(value = "/pay/aliPay")
public class AliPayController {
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private AliPayThin aliPayThin;

    @ApiOperation(value = "生成form表单", notes = "手机端调用支付宝返回完整的form表单", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/mobileCodePage", method = RequestMethod.POST)
    @ResponseBody
    public String appCodePage(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        try {
            map.put("tradeType", ConstantsCode.GOOD_PAY);
            return aliPayService.getAliPayForm(map, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "支付宝同步回调函数", notes = "支付宝同步回调函数，return_url", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public void returnUrl(HttpServletRequest request, HttpServletResponse response) {
        try {
            aliPayService.returnUrl(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "支付宝异步回调函数", notes = "支付宝异步回调函数，notify_url", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        try {
            aliPayThin.aliPayNotify(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "支付宝退款", notes = "支付宝退款", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public LsResponse refund(@RequestBody Map<String, String> data, HttpServletRequest request) {
        return aliPayService.refund(data,request);
    }

    @ApiOperation(value = "支付宝订单查询", notes = "支付宝订单查询", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public LsResponse query(@RequestBody Map<String, String> data) {
        String orderNos = data.get("out_trade_nos");
        return aliPayService.query(orderNos);
    }
}
