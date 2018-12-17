package com.lswd.youpin.web.controller;


import com.lswd.youpin.Thin.AssociatorPayBillThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorPayBillService;
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

/*该Controller取消，没有任何用，没有任何请求，改为业务库中的memberPayBill表*/

/**
 * Created by zhenguanqi on 2017/11/22.
 */
@Api(value = "associatorPayBill", tags = "associatorPayBill", description = "会员冲值列表")
@Controller
@RequestMapping(value = "/associatorPayBill")
public class AssociatorPayBillController {

    @Autowired
    private AssociatorPayBillThin associatorPayBillThin;
    @Autowired
    private AssociatorPayBillService associatorPayBillService;

    @ApiOperation(value = "会员充值列表WEB端", notes = "会员消费充值WEB端", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getAssociatorPayBillList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAssociatorPayBillList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                               @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                               @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                               @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                               @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                               HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=associatorPayBillThin.getAssociatorPayBillList(keyword,canteenId,date,pageNum,pageSize,user);
        return  lsResponse;
    }

}
