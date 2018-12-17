package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RechargeService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liruilong on 2017/7/5.
 */
@Api(value = "Recharge", tags = "Recharge", description = "个人账户充值退款操作")
@Controller
@RequestMapping(value = "/account")
public class RechargeController {
    @Autowired
    private RechargeService rechargeService;

    @ApiOperation(value = "个人账户充值", notes = "个人账户充值", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ResponseBody
    public String recharge(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        return rechargeService.recharge(map, request, response);
    }

    @ApiOperation(value = "会员充值记录查询", notes = "会员充值记录查询", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getRechargeLog", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecharge(@ApiParam(value = "associatorId", required = true) @RequestParam(value = "associatorId") String associatorId,
                                  @ApiParam(value = "startTime") @RequestParam(value = "startTime", required = false) String startTime,
                                  @ApiParam(value = "endTime") @RequestParam(value = "endTime", required = false) String endTime,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        return rechargeService.getRecharge(associatorId, startTime, endTime, pageNum, pageSize);
    }

    @ApiOperation(value = "会员账户退款", notes = "会员账户退款", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse refund(HttpServletRequest request) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        Associator associator = (Associator) request.getAttribute("associator");
        return rechargeService.refund(associator.getAssociatorId());
    }

    @ApiOperation(value = "会员账户明细", notes = "会员账户明细", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getAccountInfo/{associatorId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAccountInfo(@PathVariable String associatorId) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        return rechargeService.getAccountInfo(associatorId);
    }

}
