package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.GoodOrderThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodOrderService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenguanqi on 2017/6/22.
 */

@Api(value = "goodOrder", tags = "goodOrder", description = "商品订单管理")
@Controller
@RequestMapping(value = "/goodOrder")
public class GoodOrderController {

    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodOrderThin goodOrderThin;

    @ApiOperation(value = "商品订单列表", notes = "商品订单列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderList(@ApiParam(value = "keyword") @RequestParam(value = "keyword",required = false) String keyword,
                                       @ApiParam(value = "associatorId") @RequestParam(value = "associatorId",required = false) String associatorId,
                                       @ApiParam(value = "canteenId") @RequestParam(value = "canteenId",required = false) String canteenId,
                                       @ApiParam(value = "orderTime") @RequestParam(value = "orderTime",required = false) String orderTime,
                                       @ApiParam(value = "dateflag") @RequestParam(value = "dateflag",required = false) Integer dateflag,
                                       @ApiParam(value = "payType") @RequestParam(value = "payType",required = false) Integer payType,
                                       @ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                       @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                       @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodOrderThin.getGoodOrderList(user,keyword,associatorId,canteenId,orderTime,dateflag,payType,flag,pageNum,pageSize);
        return lsResponse;
    }


    @ApiOperation(value = "商品订单统计", notes = "商品订单统计", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderStatistics", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderStatistics(@ApiParam(value = "startTime") @RequestParam(value = "startTime",required = false) String startTime,
                                             @ApiParam(value = "endTime") @RequestParam(value = "endTime",required = false) String endTime,
                                             @ApiParam(value = "canteenId") @RequestParam(value = "canteenId",required = false) String canteenId) {
        Date startDate = null;
        Date endDate = null;
        if (startTime != null && endTime != null){
             startDate = Dates.toDate(startTime,"yyyy-MM-dd hh:mm:ss");
             endDate = Dates.toDate(endTime,"yyyy-MM-dd hh:mm:ss");
        }
        LsResponse lsResponse = goodOrderService.getGoodOrderStatistics(startDate,endDate,canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "立即付款，暂时没用，组长写", notes = "立即付款，暂时没用，组长写", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/paidNow", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse paidNow(@ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        LsResponse lsResponse = goodOrderService.paidNowH5(goodOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "根据订单编号得到订单详情", notes = "根据订单编号得到订单详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderDetails", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderDetails(
            @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId,HttpServletRequest request) {
        LsResponse lsResponse = goodOrderService.getGoodOrderDetails(goodOrderId);
        return lsResponse;
    }

/*-----------------------------------------------------------H5页面展示部分----------------------------------------------------------------------*/
    @ApiOperation(value = "商品订单列表H5页面显示", notes = "商品订单列表H5页面显示", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderListH5Show", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderListH5Show(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                             @ApiParam(value = "canteenId",required = true) @RequestParam(value = "canteenId") String canteenId,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        Associator associator = (Associator)request.getAttribute("associator");
        canteenId = request.getHeader(ConstantsCode.CANTEEN_ID);
        LsResponse lsResponse = goodOrderThin.getGoodOrderListH5Show(associator.getAssociatorId(),flag,canteenId,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "根据订单编号得到订单详情", notes = "根据订单编号得到订单详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderDetailsH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderDetailsH5(@ApiParam(value = "status",required = true) @RequestParam(value = "status") Integer status,
                                            @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        LsResponse lsResponse = goodOrderThin.getGoodOrderDetailsH5(status,goodOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "点击--》立即付款", notes = "点击--》立即付款", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/paidNowH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse paidNowH5(@ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        Associator associator = (Associator)request.getAttribute("associator");
        LsResponse lsResponse = goodOrderThin.paidNowH5(associator,goodOrderId);
        return lsResponse;
    }


    @ApiOperation(value = "点击 -- 》申请退款", notes = "点击 -- 》申请退款", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/applyRefundGorRH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse applyRefundGorRH5(
            @ApiParam(value = "orderId",required = true) @RequestParam(value = "orderId") String orderId,HttpServletRequest request) {
        String canteenId = request.getHeader(ConstantsCode.CANTEEN_ID);
        LsResponse lsResponse = goodOrderThin.applyRefundGorRH5(orderId,canteenId);
        return lsResponse;
    }


    @ApiOperation(value = "根据订单编号打开评论页面", notes = "根据订单编号打开评论页面", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/openGoodCommentH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse openGoodCommentH5(@ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        //DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = goodOrderThin.openGoodCommentH5(goodOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "删除订单", notes = "删除订单", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteGoodOrderH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteGoodOrderH5(@ApiParam(value = "status",required = true) @RequestParam(value = "status") Integer status,
                                        @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        LsResponse lsResponse = goodOrderService.deleteGoodOrderH5(status,goodOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "取消订单", notes = "取消订单", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/cancelGoodOrderH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse cancelGoodOrderH5(
            @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId,HttpServletRequest request) {
        LsResponse lsResponse = goodOrderService.cancelGoodOrderH5(goodOrderId);
        return lsResponse;
    }

    /*暂时没有用到*/
    @ApiOperation(value = "扫码确认收货", notes = "扫码确认收货", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/confirmGoodOrderH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse confirmGoodOrderH5(
            @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId,HttpServletRequest request) {
        LsResponse lsResponse = goodOrderService.confirmGoodOrderH5(goodOrderId);
        return lsResponse;
    }

    /*----------------------------------------------------------WX小程序页面部分------------------------------------------------------------*/
    @ApiOperation(value = "商品订单列表小程序页面显示", notes = "商品订单列表小程序页面显示", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderListWxShow", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderListWxShow(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                             @ApiParam(value = "canteenId") @RequestParam(value = "canteenId",required = false) String canteenId,
                                             @ApiParam(value = "orderTime") @RequestParam(value = "orderTime",required = false) String orderTime,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        TenantAssociator tenantAssociator = (TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse = goodOrderThin.getGoodOrderListWxShow(tenantAssociator.getAssociatorId(),flag,canteenId,orderTime,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "扫码确认收货", notes = "扫码确认收货", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/confirmOrderWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse confirmOrderWx(
            @ApiParam(value = "orderId",required = true) @RequestParam(value = "orderId") String orderId,HttpServletRequest request) {
        //DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = goodOrderService.confirmOrderWx(orderId);
        return lsResponse;
    }

    @ApiOperation(value = "扫码之后，页面显示的信息", notes = "扫码之后，页面显示的信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/confirmOrderInfoWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse confirmOrderInfoWx(@ApiParam(value = "orderId",required = true) @RequestParam(value = "orderId") String orderId, HttpServletRequest request) {
        TenantAssociator tenantAssociator = (TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse = goodOrderThin.confirmOrderInfoWx(tenantAssociator,orderId);
        return lsResponse;
    }

    @ApiOperation(value = "Wx小程序获取日期格式", notes = "Wx小程序获取日期格式", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getDateWx", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getDateWx() {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String,String> dateMap = new HashMap<>();
        Date startTime = Dates.getBeforeDate(new Date(),90);//获取90天之前的日期
        Date endTime = Dates.getAfterDate(new Date(),90);//获取90天之后的日期
        dateMap.put("startDate", Dates.format(startTime,"yyyy-MM-dd"));
        dateMap.put("endDate", Dates.format(endTime,"yyyy-MM-dd"));
        dateMap.put("date", Dates.now("yyyy-MM-dd"));
        lsResponse.setData(dateMap);
        return lsResponse;
    }


    @ApiOperation(value = "根据订单编号得到订单详情", notes = "根据订单编号得到订单详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrderDetailsWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrderDetailsWx(@ApiParam(value = "status",required = true) @RequestParam(value = "status") Integer status,
                                            @ApiParam(value = "goodOrderId",required = true) @RequestParam(value = "goodOrderId") String goodOrderId, HttpServletRequest request) {
        LsResponse lsResponse = goodOrderThin.getGoodOrderDetailsWx(status,goodOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "Wx小程序获取日期格式", notes = "Wx小程序获取日期格式", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getDateMonthWx", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getDateMonthWx() {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String,String> dateMap = new HashMap<>();
        Date startTime = Dates.getBeforeDate(new Date(),15);//获取15天之前的日期
        Date endTime = Dates.getAfterDate(new Date(),15);//获取15天之后的日期
        dateMap.put("startDate", Dates.format(startTime,"yyyy-MM-dd"));
        dateMap.put("endDate", Dates.format(endTime,"yyyy-MM-dd"));
        dateMap.put("date", Dates.now("yyyy-MM-dd"));
        lsResponse.setData(dateMap);
        return lsResponse;
    }


}
