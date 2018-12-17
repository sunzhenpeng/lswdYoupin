package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.AppResourceMapper;
import com.lswd.youpin.lsy.AppResourceService;
import com.lswd.youpin.model.lsy.AppResource;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by sunzhenpeng on 20180612.
 */
@Api(value = "appResource", tags = "appResource", description = "APP菜单管理-app123")
@Controller
@RequestMapping(value = "/appResource")
public class AppResourceController {

    @Autowired
    AppResourceService appResourceService;
    @Autowired
    AppResourceMapper appResourceMapper;
    @ApiOperation(value = "菜单列表", notes = "菜单列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getAppResourceList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getAppResourceList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                         @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
        return appResourceService.getAppResourceList(machineId,keyword);
    }

    @ApiOperation(value = "菜单详情", notes = "菜单详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getAppResourceById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getAppResourceById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
        return appResourceService.getAppResourceById(id);
    }
    

    @ApiOperation(value = "APP展示端", notes = "根据设备编号获取菜单列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getAppResourceByMachineNo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getAppResourceListByMachineNo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return appResourceService.getAppResourceByMachineNo(machineNo);
    }

    @ApiOperation(value = "新建菜单", notes = "新建菜单", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateAppResource", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateAppResource(@RequestBody AppResource appResource, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = appResourceService.addOrUpdateAppResource(appResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除菜单", notes = "删除菜单", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteAppResource/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteAppResource(@PathVariable Integer id, HttpServletRequest request) {
        return appResourceService.delAppResource(id);
    }

/*
    @ApiOperation(value = "新建或者修改菜单", notes = "新建或者修改菜单", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody·
    public LsResponse addAppResource(@RequestBody AppResource appResource, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return appResourceService.addOrUpdateAppResource(machine,u);

    }*/






}
