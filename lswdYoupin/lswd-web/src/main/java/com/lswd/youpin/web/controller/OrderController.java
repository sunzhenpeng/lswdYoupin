package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.OrderThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Orders;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuhao on 2017/6/26.
 */
@Api(value = "order", tags = "order", description = "订单号管理")
@Controller
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "Content-Type",allowCredentials = "true")
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderThin orderThin;

    @ApiOperation(value = "订单生成", notes = "订单生成", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
//    public LsResponse GenerateOrder(@RequestBody List<Map<String,Object>> carOrders, HttpServletRequest request)
//    {
//        Associator associator = (Associator) request.getAttribute("associator");
//        LsResponse lsResponse = orderThin.addGenerateOrder(carOrders, associator);
//        return lsResponse;
//    }
    public LsResponse GenerateOrder(@RequestBody Orders orders, HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = orderThin.addGenerateOrder(orders, associator);
        return lsResponse;
    }

    @ApiOperation(value = "订单支付倒计时", notes = "订单支付倒计时", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/timeOrder", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse timeOrder(@RequestBody List<String> carOrders, HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = orderService.timeOrder(carOrders,associator);
        return lsResponse;
    }


    @ApiOperation(value = "取消订单", notes = "取消订单", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/removeOrder", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse removeOrder(@RequestBody List<String> carOrders)
    {
        LsResponse lsResponse = orderService.removeOrder(carOrders);
        return lsResponse;
    }

    @ApiOperation(value = "返回订单数量", notes = "返回订单数量", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getOrdersCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getOrdersCount(HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse =orderThin.getOrdersCount(associator);
        return lsResponse;
    }


}
