package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.DiskType;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.DiskTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/15
 */
@Api(value = "diskType", tags = "diskType", description = "餐盘类型管理")
@Controller
@RequestMapping(value = "/diskType")
public class DiskTypeController {

    @Autowired
    private DiskTypeService diskTypeService;

    @ApiOperation(value = "餐盘类型列表", notes = "餐盘类型列表", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getDiskTypeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getDiskTypeList(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                      @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        LsResponse lsResponse = diskTypeService.getDiskTypeList(name, canteenId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "餐盘类型新增", notes = "餐盘类型新增", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/addDiskType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addDiskType(@RequestBody DiskType diskType, HttpServletRequest request) {
        User user =(User) request.getAttribute("user");
        return diskTypeService.addDiskType(diskType, user);
    }

    @ApiOperation(value = "餐盘类型修改", notes = "餐盘类型修改", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/updateDiskType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateDiskType(@RequestBody DiskType diskType, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = diskTypeService.updateDiskType(diskType, user);
        return lsResponse;
    }

    @ApiOperation(value = "餐盘类型删除", notes = "餐盘类型删除", nickname = "liruilong", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteDiskType/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteDiskType(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = diskTypeService.deleteDiskType(id);
        return lsResponse;
    }

    @ApiOperation(value = "餐盘类型列表，获取所有的", notes = "餐盘类型列表，获取所有的", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getDiskTypeListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getDiskTypeListAll(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId", required = true) String canteenId) {
        LsResponse lsResponse = diskTypeService.getDiskTypeListAll(canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "根据餐盘类型列表获取所有的标签信息", notes = "根据餐盘类型列表获取所有的标签信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getLabelByTypeId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getLabelByTypeId(@ApiParam(value = "typeId", required = true) @RequestParam(value = "typeId") Integer typeId,
                                       @ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                       @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                       @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        LsResponse lsResponse = diskTypeService.getLabelByTypeId(typeId,keyword,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "根据餐盘类型id清空所有的标签信息", notes = "根据餐盘类型id清空所有的标签信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteLabelByTypeId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteLabelByTypeId(@ApiParam(value = "typeId", required = true) @RequestParam(value = "typeId") Integer typeId) {
        LsResponse lsResponse = diskTypeService.deleteLabelByTypeId(typeId);
        return lsResponse;
    }

}
