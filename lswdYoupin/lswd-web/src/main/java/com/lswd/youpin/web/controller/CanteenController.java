package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.CanteenThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.ShowImages;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.CanteenVO;
import com.lswd.youpin.model.vo.SupplierVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/7.
 */
@Api(value = "canteen", tags = "canteen", description = "餐厅管理")
@Controller
@RequestMapping(value = "/canteen")
public class CanteenController {
    @Autowired
    private CanteenService canteenService;

    @Autowired
    private CanteenThin canteenThin;

    @ApiOperation(value = "新建或者修改餐厅", notes = "新建或者修改餐厅", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateCanteen(@RequestBody CanteenVO canteenVO, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenService.addOrUpdateCanteen(canteenVO, u,request);
    }

    @ApiOperation(value = "餐厅详情", notes = "餐厅详情", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/{canteenId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteen(@PathVariable String canteenId, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenThin.getCanteenByCanteenId(canteenId, u);
    }

    @ApiOperation(value = "餐厅列表", notes = "餐厅列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getCanteenList", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public LsResponse getCanteen(@ApiParam(value = "institutionId") @RequestParam(value = "institutionId", required = false) String institutionId,
                                 @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                 @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                 @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenThin.getAllCanteen(u, institutionId, keyword, pageNum, pageSize);
    }

    @ApiOperation(value = "根据资源类型id获取餐厅id", notes = "根据资源类型id获取餐厅id", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/get/{resTypeId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenIdByRestypeId(@ApiParam(value = "resTypeId") @RequestParam(value = "resTypeId", required = true) Integer resTypeId,
                                              HttpServletRequest request) {
        //  User u = (User) request.getAttribute("user");
        return canteenService.getCanteenIdByResTypeId(resTypeId);
    }

    @ApiOperation(value = "根据地理位置查询餐厅", notes = "根据地理位置查询餐厅", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/get/ByLocation", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getCanteenLocation(@RequestBody Map<String, Double> map, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        Double longitude = map.get("longitude");
        Double latitude = map.get("latitude");
        return canteenService.getCanteenListByLocation(associator.getAssociatorId(), longitude, latitude);
    }

    @ApiOperation(value = "获取会员上次登录的餐厅编号", notes = "获取会员上次登录的餐厅编号", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/getNearestCanteen", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getNearestCanteen(HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        return canteenService.getNearestCanteen(associator.getAssociatorId());
    }

    @ApiOperation(value = "查询会员绑定的餐厅列表", notes = "会员绑定的餐厅列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getAssociatorCanteenList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAssociatorCanteenList(HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        return canteenService.getAssociatorCanteenList(associator.getAssociatorId());
    }

    @ApiOperation(value = "获取餐厅部分信息", notes = "获取餐厅餐厅id，餐厅名称", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/part/{canteenId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenNameAndCanteenId(@PathVariable String canteenId) {
        return canteenService.getCanteenPart(canteenId);
    }

    @ApiOperation(value = "获取用户绑定的餐厅列表", notes = "获取用户绑定的餐厅列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/user", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getUserCanteenList(HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenThin.getUserCanteenList(u.getCanteenIds());
    }

    @ApiOperation(value = "获取商家小程序用户绑定的餐厅列表", notes = "获取会员绑定的餐厅列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getTenantAssociatorCanteen", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getTenantAssociatorCanteenList(HttpServletRequest request) {
        TenantAssociator associator = (TenantAssociator) request.getAttribute("tenantAssociator");
        return canteenService.getTenantAssociatorCanteenList(associator.getAssociatorId());
    }

    @ApiOperation(value = "获取列表的部字段（id，餐厅编号，餐厅名称）", notes = "获取列表的部字段（id，餐厅编号，餐厅名称）", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/part", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenPartList(
            @ApiParam(value = "institutionId") @RequestParam(value = "institutionId", required = false) String institutionId,
            @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenThin.getAllCanteenPart(u, institutionId, keyword, pageNum, pageSize);
    }

    @ApiOperation(value = "为餐厅添加供应商", notes = "为餐厅添加供应商", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateSupplier", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCanteenSupplierLink(@RequestBody SupplierVO supplierVO, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenThin.addCanteenSupplierLink(supplierVO, u);
    }

    @ApiOperation(value = "根据餐厅名称查询", notes = "根据餐厅名称查询", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getByCanteenName", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenListByCanteenName(@ApiParam(value = "canteenName") @RequestParam(value = "canteenName", required = false) String canteenName) {
        return canteenService.getCanteenListByCanteenName(canteenName);
    }

    @ApiOperation(value = "修改或者餐厅的首页图片", notes = "修改或者餐厅的首页图片", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateShowImages", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateShowImages(@RequestBody List<ShowImages> showImages, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenService.addOrUpdateShowImages(showImages, u);
    }

    @ApiOperation(value = "删除首页图片", notes = "删除首页图片", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse deleteShowImages(@RequestBody ShowImages showImages, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return canteenService.deleteImage(showImages, u);
    }

    @ApiOperation(value = "web展示首页图片", notes = "获取用于首页展示的图片", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getImagesWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getImagesWeb(@ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId, HttpServletRequest request) {
        return canteenService.getImageWeb(canteenId);
    }

    @ApiOperation(value = "H5展示首页图片", notes = "获取用于首页展示的图片", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getImagesH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getImagesH5(@ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        return canteenService.getImageH5(canteenId);
    }

    @ApiOperation(value = "判断是否有卡支付", notes = "判断是否有卡支付", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getPayType", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getPayType(@ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        return canteenService.getPayType(canteenId);
    }
}
