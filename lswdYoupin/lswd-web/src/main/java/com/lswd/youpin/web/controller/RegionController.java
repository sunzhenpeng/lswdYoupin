package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/7/17.
 */
@Api(value = "region", tags = "region", description = "园区管理")
@Controller
@RequestMapping(value = "/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "新建或者修改园区", notes = "新建或者修改园区", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addRole(@RequestBody Region region, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return regionService.addOrUpdateRegion(region,u);
    }

    @ApiOperation(value = "园区列表", notes = "获取园区信息", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getRegionList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAllRoles(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest servletRequest) {
        return regionService.getAllRegion(keyword, pageNum, pageSize);
    }
    @ApiOperation(value = "园区详情", notes = "园区详情", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRegionById(@PathVariable Integer id) {
        return regionService.getRegionById(id);
    }

    @ApiOperation(value = "获取全部园区列表", notes = "获取全部园区列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getRegionAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRegionAll() {
        return regionService.getRegionAll();
    }
}
