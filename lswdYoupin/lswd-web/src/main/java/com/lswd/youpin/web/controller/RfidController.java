package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.lsyp.DiskRecipe;
import com.lswd.youpin.model.vo.LabelVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RfidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liruilong on 2017/11/24.
 * 读写器控层
 */
@Api(value = "reader", tags = "reader", description = "读写器读取标签")
@Controller
@RequestMapping(value = "/rfid")
public class RfidController {
    @Autowired
    private RfidService rfidService;

    @ApiOperation(value = "标签录入", notes = "获取天线上的芯片信息", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getLabel() {
        return rfidService.getLabel();
    }

    @ApiOperation(value = "餐具信息添加", notes = "餐盘录入", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody LabelVO labelVO) {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.add(labelVO);
    }

    @ApiOperation(value = "餐具信息", notes = "获取单个餐具对应的菜品信息", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse get() {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.getLabelInfoByUid();
    }

    @ApiOperation(value = "修改单个餐具信息", notes = "修改单个餐具对应的菜品信息", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/updateSingle", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse updateSingle(@ApiParam(value = "uId", required = true) @RequestParam("uId") String uId,
                                   @ApiParam(value = "recipeId", required = true) @RequestParam("recipeId") String recipeId) {
        //TODO 此处是否需要选择餐厅
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.updateSingleDisk(uId, recipeId);
    }

    @ApiOperation(value = "修改餐具菜品的对应关系", notes = "修改餐具菜品的对应关系", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody DiskRecipe diskRecipe) {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.update(diskRecipe);
    }

    @ApiOperation(value = "删除餐具菜品的对应关系", notes = "删除餐具菜品的对应关系", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/deleteDiskRecipe", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteDiskRecipe(@ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId,
                                       @ApiParam(value = "diskTypeId", required = true) @RequestParam("diskTypeId") Integer diskTypeId) {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.deleteDiskRecipe(canteenId, diskTypeId);
    }

    @ApiOperation(value = "餐具菜品的对应关系列表", notes = "餐具菜品的对应关系列表", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getDiskRecipe(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                    @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                    @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                    @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.getDiskRecipe(keyword, canteenId, pageNum, pageSize);
    }

    @ApiOperation(value = "获取餐具类型对应的餐具数量", notes = "芯片数量统计", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getLabels", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getLabelStatistics(
            @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
            @ApiParam(value = "diskTypeId") @RequestParam(value = "diskTypeId", required = false) Integer diskTypeId) {
//        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        return rfidService.getLabelStatistics(canteenId, diskTypeId);
    }
}
