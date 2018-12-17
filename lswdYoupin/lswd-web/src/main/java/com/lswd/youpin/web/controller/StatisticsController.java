package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.StatisticsThin;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/7/4.
 */
@Api(value = "statistics", tags = "statistics", description = "统计模块")
@Controller
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "Content-Type",allowCredentials = "true")
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private StatisticsThin statisticsThin;

    @ApiOperation(value = "销售统计", notes = "销售统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/salesCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse salesCount(@ApiParam(value = "startDate") @RequestParam(value = "startDate",required = false) String startDate,
                                 @ApiParam(value = "endDate") @RequestParam(value = "endDate",required = false) String endDate,
                                 @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId ,
                                 HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse=statisticsThin.getSalesCount(startDate,endDate,canteenId,tenantAssociator);
        return  lsResponse;
    }
    @ApiOperation(value = "商品订单统计", notes = "商品订单统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/goodSalesCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse goodSalesCount(@ApiParam(value = "date") @RequestParam(value = "date",required = false) String date,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                     @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId ,
                                     HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse=statisticsThin.goodSalesCount(date,pageSize,pageNum,canteenId,tenantAssociator);
        return  lsResponse;
    }

    @ApiOperation(value = "菜谱订单统计", notes = "菜谱订单统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/recipeSalesCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse recipeSalesCount(@ApiParam(value = "date") @RequestParam(value = "date",required = false) String date,
                                       @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                       @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                       @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                       HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse=statisticsThin.recipeSalesCount(date,pageSize,pageNum,canteenId,tenantAssociator);
        return  lsResponse;
    }

    @ApiOperation(value = "单品销量统计", notes = "单品销量统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/danPin", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse danPin(HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsService.getDanPin(user);
        return  lsResponse;
    }
    @ApiOperation(value = "餐厅销量统计", notes = "餐厅销量统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/canCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenOrders(HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsThin.getCanteenOrders(user);
        return  lsResponse;
    }
    @ApiOperation(value = "商品销量走势图", notes = "商品销量走势图", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/timeGoodOrder", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse timeGoodOrder(HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsService.timeGoodOrder(user);
        return lsResponse;
    }
    @ApiOperation(value = "菜品销量走势图", notes = "菜品销量走势图", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/timeRecipeOrder", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse timeRecipeOrder(HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsService.timeRecipeOrder(user);
        return lsResponse;
    }
    @ApiOperation(value = "按支付方式销售总额度", notes = "按支付方式销售总额度", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/payTypeMoney", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse payTypeMoney(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                   @ApiParam(value = "startDate") @RequestParam(value = "startDate", required = false) String startDate,
                                   @ApiParam(value = "endDate") @RequestParam(value = "endDate", required = false) String endDate
                                    , HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsThin.payTypeMoney(canteenId,user,startDate,endDate);
        return lsResponse;
    }
    @ApiOperation(value = "按支付方式查找订单数", notes = "按支付方式查找订单数", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/payTypeOrder", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse payTypeOrder(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                   @ApiParam(value = "startDate") @RequestParam(value = "startDate", required = false) String startDate,
                                   @ApiParam(value = "endDate") @RequestParam(value = "endDate", required = false) String endDate ,
                                   HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsThin.payTypeOrder(canteenId,user,startDate,endDate);
        return lsResponse;
    }

    @ApiOperation(value = "商品销量统计", notes = "商品销量统计", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/goodSaleCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse goodSaleCount(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                    @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                    HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse=statisticsThin.goodSaleCount(user,canteenId,date);
        return  lsResponse;
    }

}
