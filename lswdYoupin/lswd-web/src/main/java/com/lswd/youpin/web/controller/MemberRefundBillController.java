package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberRefundBillService;
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
@Api(value = "memberRefundBill", tags = "memberRefundBill", description = "结算台的会员退款管理")
@Controller
@RequestMapping(value = "/memberRefundBill")
public class MemberRefundBillController {

    @Autowired
    private MemberRefundBillService memberRefundBillService;

    @ApiOperation(value = "结算台的会员退款列表WEB端", notes = "结算台的会员退款列表WEB端", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberRefundBillList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberRefundBillList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                              @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                              @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                              HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = memberRefundBillService.getMemberRefundBillList(keyword, canteenId, date, pageNum, pageSize, user);
        return lsResponse;
    }

}
