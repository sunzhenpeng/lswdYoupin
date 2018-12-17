package com.lswd.youpin.web.controller;

import com.lswd.youpin.lsy.AppResourceMachineService;
import com.lswd.youpin.response.LsResponse;
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
 * Created by sunzhenpeng on 20180626.
 */
@Api(value = "appResourceMachine", tags = "appResourceMachine", description = "设备绑定菜单11")
@Controller
@RequestMapping(value = "/appResourceMachine")
public class AppResourceMachineController {

    @Autowired
    AppResourceMachineService appResourceMachineService;

    @ApiOperation(value = "新建菜单", notes = "新建菜单", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/updateAppResourceMachine", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse updateAppResourceMachine(@ApiParam(value = "resourceIds") @RequestParam(value = "resourceIds", required = false) String resourceIds,
                                               @ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                               HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = appResourceMachineService.updateAppResourceMachine(resourceIds,machineId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }




}
