package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.JSPlatform;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.JSPlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/12/23.
 */
@Controller
@Api(value = "jsplatform", tags = "jsplatform", description = "结算台管理，增上该查")
@RequestMapping("jsplatform")
public class JSPlatformController {

    @Autowired
    private JSPlatformService jsPlatformService;

    @ApiOperation(value = "获取所有的结算台列表", notes = "获取所有的结算台列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getJSPlatformListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getJSPlatformListAll(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                           HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = jsPlatformService.getJSPlatformListAll(user,canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "结算台列表", notes = "结算台列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getJSPlatformList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getJSPlatformList(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                        @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                        @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                        @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                        HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = jsPlatformService.getJSPlatformList(user,canteenId, keyword, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "结算台新增", notes = "结算台新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addJSPlatform", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addJSPlatform(@RequestBody JSPlatform JSPlatform, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = jsPlatformService.addJSPlatform(JSPlatform, user);
        return lsResponse;
    }

    @ApiOperation(value = "结算台修改", notes = "结算台修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateJSPlatform", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateJSPlatform(@RequestBody JSPlatform JSPlatform, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = jsPlatformService.updateJSPlatform(JSPlatform, user);
        return lsResponse;
    }

    @ApiOperation(value = "结算台删除", notes = "结算台删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteJSPlatform/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteJSPlatform(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = jsPlatformService.deleteJSPlatform(id);
        return lsResponse;
    }

}
