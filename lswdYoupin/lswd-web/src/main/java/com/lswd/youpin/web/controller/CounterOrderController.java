package com.lswd.youpin.web.controller;


import com.lswd.youpin.Thin.CounterOrderThin;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.lsyp.CounterOrder;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/12/07.
 */
@Api(value = "counterOrder", tags = "counterOrder", description = "吧台订单管理")
@Controller
@RequestMapping(value = "/counterOrder")
public class CounterOrderController {

    @Autowired
    private CounterOrderService counterOrderService;
    @Autowired
    private CounterOrderThin counterOrderThin;


    @ApiOperation(value = "吧台订单，订单生成", notes = "吧台订单，订单生成", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/generateCounterOrder", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse generateCounterOrder(@RequestBody CounterOrder counterOrder, HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterOrderService.generateCounterOrder(counterOrder, counterUser);
        return lsResponse;
    }

    @ApiOperation(value = "读卡，实收金额和会员卡的余额比较", notes = "读卡，实收金额和会员卡的余额比较", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/readCardUid", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse readCardUid(@ApiParam(value = "receivedAmount", required = true) @RequestParam(value = "receivedAmount", required = true) Float receivedAmount) {
        LsResponse lsResponse = counterOrderService.readCardUid(receivedAmount);
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户 查看会员的基本信息,获取所有的会员类型", notes = "吧台用户 查看会员的基本信息，获取所有的会员类型", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberTypeListAllBT", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberTypeListAllBT() {
        LsResponse lsResponse = counterOrderService.getMemberTypeListAllBT();
        return lsResponse;
    }

    @ApiOperation(value = "读卡，返回卡号", notes = "读卡，返回卡号", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/readCardBackUid", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse readCardBackUid() {
        LsResponse lsResponse = counterOrderService.readCardBackUid();
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户 查看会员的基本信息", notes = "吧台用户 查看会员的基本信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberListBT", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberListBT(@ApiParam(value = "memberName", required = false) @RequestParam(value = "memberName", required = false) String memberName,
                                      @ApiParam(value = "memberTel", required = false) @RequestParam(value = "memberTel", required = false) String memberTel,
                                      @ApiParam(value = "memberCardUid", required = false) @RequestParam(value = "memberCardUid", required = false) String memberCardUid,
                                      @ApiParam(value = "typeId", required = false) @RequestParam(value = "typeId", required = false) Integer typeId,
                                      @ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                      HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterOrderThin.getMemberListBT(counterUser, memberName, memberTel, memberCardUid, typeId, counterId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "会员消费记录查询", notes = "会员消费记录查询", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberOrderListBT", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberOrderListBT(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                           @ApiParam(value = "memberName", required = false) @RequestParam(value = "memberName", required = false) String memberName,
                                           @ApiParam(value = "memberCardUid", required = false) @RequestParam(value = "memberCardUid", required = false) String memberCardUid,
                                           @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                           @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                           HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterOrderService.getMemberOrderListBT(counterUser, counterId, memberName, memberCardUid, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "会员消费记录查询,根据订单号查到订单详情", notes = "会员消费记录查询，根据订单号查到订单详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberOrderItemsBT", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberOrderItemsBT(@ApiParam(value = "orderId", required = true) @RequestParam(value = "orderId", required = true) String orderId,
                                            HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterOrderService.getMemberOrderItemsBT(counterUser, orderId);
        return lsResponse;
    }

    @ApiOperation(value = "吧台订单统计", notes = "吧台订单统计", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getOrderListBT", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getOrderListBT(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                     @ApiParam(value = "date", required = false) @RequestParam(value = "date", required = false) String date,
                                     @ApiParam(value = "payType", required = false) @RequestParam(value = "payType", required = false) Integer payType,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                     HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
//        DataSourceHandle.setDataSourceType("LSCT");
        LsResponse lsResponse = counterOrderService.getOrderListBT(counterUser, counterId, date, payType, pageNum, pageSize);
        return lsResponse;
    }

}
