package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.UnitService;
import com.lswd.youpin.wxpay.util.core.GenerateQrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/7/19.
 */
@Api(value = "unit", tags = "unit", description = "单位管理")
@Controller
@RequestMapping(value = "/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @ApiOperation(value = "获取全部的单位", notes = "获取全部的单位", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getUnitListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getUnitListAll(){
        //DataSourceHandle.setDataSourceType("LSYP");
        LsResponse lsResponse = unitService.getUnitListAll();
        return lsResponse;
    }

    /*自己测试，未用到*/
    /**
     * 扫码支付接口 说明：一步直接生成微信二维码，不需要传入url,需要传入商品信息
     */
    @ApiOperation(value = "生成二维码接口", notes = "二维码生成", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/createQRcode")
    @ResponseBody
    public void createQRcode(@ApiParam(value = "orderId",required = true) @RequestParam(value = "orderId") String orderId,
                             HttpServletRequest request, HttpServletResponse response) {
        //String result = "201708112112001000812";//调用订单查询接口，获取订单详情
        GenerateQrCodeUtil.encodeQrcode(orderId,response);
    }


}
