package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberPayBillService;
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

/**
 * Created by zhenguanqi on 2017/12/08.
 */
@Api(value = "memberPayBill", tags = "memberPayBill", description = "结算台的会员充值管理")
@Controller
@RequestMapping(value = "/memberPayBill")
public class MemberPayBillController {

    @Autowired
    private MemberPayBillService memberPayBillService;

    @ApiOperation(value = "结算台会员充值列表WEB端", notes = "结算台会员消费充值WEB端", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberPayBillList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberPayBillList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                           @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                           @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                           @ApiParam(value = "payType") @RequestParam(value = "payType", required = false) Integer payType,
                                           @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                           @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                           HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = memberPayBillService.getMemberPayBillList(keyword, canteenId, date, payType,pageNum, pageSize, user);
        return lsResponse;
    }


    @ApiOperation(value = "简简单单读卡", notes = "简简单单读卡", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/readCardUid", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse readCardUid(HttpServletRequest request) {
        LsResponse lsResponse = memberPayBillService.readCardUid();
        return lsResponse;
    }
}
