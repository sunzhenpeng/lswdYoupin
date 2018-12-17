package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.RecipeOrderThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeOrderService;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/21.
 */
@Api(value = "recipeOrder", tags = "recipeOrder", description = "菜谱订单管理")
@Controller
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "Content-Type",allowCredentials = "true")
@RequestMapping(value = "/recipeOrder")
public class RecipeOrderController {
    @Autowired
    private RecipeOrderService recipeOrderService;
    @Autowired
    private RecipeOrderThin recipeOrderThin;

    @ApiOperation(value = "菜谱订单列表", notes = "菜谱订单列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeOrderList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeOrderList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                         @ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                         @ApiParam(value = "payType") @RequestParam(value = "payType",required = false) Integer payType,
                                         @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId ,
                                         @ApiParam(value = "dataTime") @RequestParam(value = "dataTime", required = false) Integer dataTime,
                                         HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse = recipeOrderThin.getRecipeOrderList(keyword,pageNum,pageSize,flag,canteenId,dataTime,payType,user);
        return  lsResponse;
    }
    @ApiOperation(value = "查看菜谱订单详情", notes = "查看菜谱订单详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeOrder", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeOrder(@ApiParam(value = "status", required = true) @RequestParam("status") Integer status,
                                     @ApiParam(value = "orderId", required = true) @RequestParam("orderId") String orderId,
                                     HttpServletRequest request)
    {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse=recipeOrderThin.getRecipeOrder(associator,status,orderId);
        return lsResponse;
    }

    @ApiOperation(value = "会员查看菜谱订单列表", notes = "会员查看菜谱订单列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/recipeOrderList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse recipeOrderList(@ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                      @ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                      HttpServletRequest request)
    {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse=recipeOrderThin.getAsrecipeOrderList(associator,pageNum,pageSize,flag);
        return lsResponse;
    }

    @ApiOperation(value = "会员取消菜谱订单", notes = "会员取消菜谱订单", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/cancelOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse cancelOrder(@PathVariable(value = "orderId")String orderId, HttpServletRequest request)
    {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse=recipeOrderService.deleteOrder(associator,orderId);
        return lsResponse;
    }
    @ApiOperation(value = "会员删除菜谱订单", notes = "会员删除菜谱订单", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/delOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse dellOrder(@PathVariable(value = "orderId")String orderId, HttpServletRequest request)
    {
        LsResponse lsResponse= null;
        try {
            Associator associator=(Associator)request.getAttribute("associator");
            lsResponse = recipeOrderService.removeOrder(associator,orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @ApiOperation(value = "小程序菜谱订单列表", notes = "小程序菜谱订单列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getOrderListWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getOrderListWx(@ApiParam(value = "pageNum", required = true) @RequestParam("pageNum")Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize")Integer pageSize,
                                     @ApiParam(value = "canteenId") @RequestParam(value = "canteenId",required = false)String canteenId ,
                                     @ApiParam(value = "flag", required = true) @RequestParam("flag")Integer flag ,
                                     @ApiParam(value = "dataTime") @RequestParam(value = "dataTime",required = false)String dataTime,
                                     HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse = recipeOrderThin.getOrderListWx(pageNum,pageSize,canteenId,dataTime,tenantAssociator,flag);
        return  lsResponse;
    }

    @ApiOperation(value = "查看菜谱订单详情", notes = "查看菜谱订单详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeOrderWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeOrderWx(@ApiParam(value = "status", required = true) @RequestParam("status") Integer status,
                                       @ApiParam(value = "orderId", required = true) @RequestParam("orderId") String orderId)
    {
        LsResponse lsResponse=recipeOrderThin.getRecipeOrderWx(status,orderId);
        return lsResponse;
    }

    @ApiOperation(value = "根据订单编号打开评论页面", notes = "根据订单编号打开评论页面", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/openRecipeCommentH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse openRecipeCommentH5(@ApiParam(value = "recipeOrderId",required = true) @RequestParam(value = "recipeOrderId") String recipeOrderId, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = recipeOrderThin.openRecipeCommentH5(recipeOrderId);
        return lsResponse;
    }

    @ApiOperation(value = "查看点单信息进行支付", notes = "查看点单信息进行支付", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/lookOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookOrder(@PathVariable(value = "orderId") String orderId , HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = recipeOrderThin.lookOrder(orderId,associator);
        return lsResponse;
    }


}
