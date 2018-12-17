package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.WxThin;
import com.lswd.youpin.model.WeixinOauth2Token;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.service.WxService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.web.utils.SHA1;
import com.lswd.youpin.wxpay.WxApi.WxPayApi;
import com.lswd.youpin.wxpay.config.WxConfigInfo;
import com.lswd.youpin.wxpay.util.IpUtils;
import com.lswd.youpin.wxpay.util.core.GenerateQrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by liruilong on 2017/4/11.
 */
@Api(value = "wx", tags = "wx", description = "微信支付")
@Controller
@RequestMapping(value = "/pay/wxPay")
public class WxController {
    @Autowired
    private WxService wxService;

    @Autowired
    private WxThin wxThin;

    @Autowired
    private AssociatorService associatorService;

    @ApiOperation(value = "获取微信用户信息，使用微信授权登录", notes = "根据code获取微信用户的信息", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getWxUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCode( @ApiParam(value = "code", required = true) @RequestParam("code") String code,
                               @ApiParam(value = "associatorId", required = true) @RequestParam("associatorId") String associatorId){
        WeixinOauth2Token wat =  WxPayApi.getOpenId2(code);
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        return associatorService.add(wat,associatorId);
    }

    @ApiOperation(value = "获取统一下单接口返回的数据", notes = "获取统一下单接口返回的数据", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/getPackage", method = RequestMethod.POST)
    @ResponseBody
    public String getPackage(@RequestBody Map<String, String> data, HttpServletRequest servletRequest) {
        data.put("spbill_create_ip", IpUtils.getIpAddr(servletRequest));
        return wxService.getPackage(data, servletRequest);
    }

    @ApiOperation(value = "异步回调通知", notes = "异步回调通知", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        wxThin.wxNotify(request, response);
    }

    @ApiOperation(value = "退款申请", notes = "退款申请", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse goToRefund(@RequestBody Map<String, String> map, HttpServletRequest servletRequest) {
        return wxService.refund(map);
    }

    @ApiOperation(value = "微信订单查询", notes = "微信订单查询", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse query(@RequestBody Map<String, String> data) {
        String out_trade_nos = data.get("out_trade_nos");
        return wxService.query(out_trade_nos);
    }

    /**
     * 扫码支付接口 说明：一步直接生成微信二维码，不需要传入url,需要传入商品信息
     */
    @ApiOperation(value = "生成二维码接口", notes = "二维码生成", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/createQrcode/{orderNo}")
    @ResponseBody
    public void getQRCode(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) {
        GenerateQrCodeUtil.encodeQrcode(orderNo, response);
    }

    /**
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
     * 由于获取jsapi_ticket的api调用次数非常有限，
     * 频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket
     *
     * @return
     */
    @ApiOperation(value = "获取ticket", notes = "获取JSSDK的注入参数", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/jssdk")
    @ResponseBody
    public LsResponse getJSSDKConfig(@ApiParam(value = "url", required = true) @RequestParam("url") String url) {
        return wxService.getJSSDKConfig(url);
    }

    /**
     * 在微信公众平台基本配置中，配置服务器的地址，服务器中配置的地址必须和此处的url一直，TOKEN 值也必须一直，否则会提示TOKEN 验证失败
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "验证token", notes = "验证token", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/validateToken", method = RequestMethod.GET)
    @ResponseBody
    public void validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = {WxConfigInfo.TOKEN, timestamp, nonce};
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }
    }

    @ResponseBody
    @ApiOperation(value = "测试Code", notes = "测试Code", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public ModelAndView code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String data = request.getParameter("state");
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + WxConfigInfo.APPID + "&redirect_uri=" + "https%3a%2f%2fwww.u-meal.com.cn%2f%23lsyp%2fmember%2flogin"
                + "&response_type=code&scope=" + "snsapi_userinfo" + "&state=" + data + "#wechat_redirect";
        System.out.println(data + "-------------------");
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @ApiOperation(value = "获取code", notes = "获取code", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        System.out.println(code + "------------------");
    }

    @ApiOperation(value = "获取openid", notes = "获取openid", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getOpenId/{code}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getOpenId(@PathVariable String code){
        return wxService.getOpenId(code);
    }

}
