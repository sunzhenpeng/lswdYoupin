package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.LeaveInfoService;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by H61M-K on 2018/3/12.
 */
@Api(value = "leaveInfo", tags = "leaveInfo", description = "级别管理")
@Controller
@RequestMapping(value = "/leaveInfo")
public class LeaveInfoController {
    @Autowired
    private LeaveInfoService leaveInfoService;
    @ApiOperation(value = "级别列表", notes = "级别列表", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    private LsResponse get(){
        DataSourceHandle.setDataSourceType("DSSS");
        return leaveInfoService.get();
    }
}
