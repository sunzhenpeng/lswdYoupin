package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Supplier;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/19.
 */
@Api(value = "supplier", tags = "supplier", description = "供应商管理")
@Controller
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "Content-Type",allowCredentials = "true")
@RequestMapping(value = "/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @ApiOperation(value = "新建供应商", notes = "新建供应商", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addSupplier(@RequestBody Supplier supplier, HttpServletRequest request)
    {
            User user=(User)request.getAttribute("user");
            LsResponse lsResponse = supplierService.addSupplier(supplier,user);
            return  lsResponse;
    }


    @ApiOperation(value = "删除供应商", notes = "删除供应商", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteSupplier(@PathVariable(value = "id")Integer id, HttpServletRequest request)
    {
        User user=(User) request.getAttribute("user");
        LsResponse lsResponse =supplierService.deleteSupplier(id,user);
        return lsResponse;

    }

    @ApiOperation(value = "修改供应商信息", notes = "修改供应商信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateSupplier(@RequestBody Supplier supplier, HttpServletRequest request) {
            User user = (User) request.getAttribute("user");
            LsResponse lsResponse = supplierService.updateSupplier(supplier,user);
            return lsResponse;
        }


    @ApiOperation(value = "获取供应商列表", notes = "获取供应商列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getSupplierList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getSupplierList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                      HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = supplierService.getSupplierList(user, pageNum, pageSize, keyword);
        return lsResponse;
    }

    @ApiOperation(value = "查看供应商详情", notes = "查看供应商详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/look/{supplierId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookSupplier(@PathVariable(value = "supplierId")String supplierId)
    {
        LsResponse lsResponse = supplierService.lookSupplier(supplierId);
        return lsResponse;
    }

    @ApiOperation(value = "根据餐厅编号获取全部的供应商", notes = "获取全部的供应商", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getSupplierListByCanteenId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getSupplierListByCanteenId(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId, HttpServletRequest request){
        LsResponse lsResponse = supplierService.getSupplierListByCanteenId(canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "供应商可供商品", notes = "供应商可供商品", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getSupplierMaterial", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getSuplierMaterials(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                          @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                          @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                          @ApiParam(value = "supplierId", required = true) @RequestParam("supplierId") String supplierId) {
        LsResponse lsResponse = supplierService.getSuplierMaterials(keyword, pageNum, pageSize, supplierId);
        return lsResponse;
    }
    @ApiOperation(value = "获取餐厅的供应商列表", notes = "获取餐厅的供应商列表", nickname = "lituilong", httpMethod = "GET")
    @RequestMapping(value = "/getCanteenSupplierList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCanteenSupplierList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                             @ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize){
        return supplierService.getCanteenSupplierList(keyword,canteenId,pageNum,pageSize);
    }


}
