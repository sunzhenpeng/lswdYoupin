package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Good;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/14.
 */

@Api(value = "good", tags = "good", description = "商品管理")
@Controller
@RequestMapping(value = "/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "新建商品", notes = "新建商品", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Good good, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.addGood(good, user);
        return lsResponse;
    }

    @ApiOperation(value = "得到商品详情", notes = "得到商品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodDetails/{goodId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodDetails(@PathVariable(value = "goodId")String goodId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.getGoodDetails(goodId, user);
        return lsResponse;
    }

    @ApiOperation(value = "添加商品详情", notes = "添加商品详情", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addGoodDetails", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addGoodDetails(@RequestBody Good good, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.addGoodDetails(good, user);
        return lsResponse;
    }

    @ApiOperation(value = "删除商品详情中的图片", notes = "删除商品详情中的图片", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delGoodDetailsImg", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delGoodDetailsImg(@RequestParam(value = "goodId") String goodId, @RequestParam(value = "imageurl") String imageurl, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.delGoodDetailsImg(goodId,imageurl,user);
        return lsResponse;
    }

    @ApiOperation(value = "商品新建未保存，点击取消", notes = "商品新建未保存，点击取消", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delCancelImg", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delCancelImg(@RequestParam(value = "imageurls") List<String> imageurls, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.delCancelImg(imageurls,user);
        return lsResponse;
    }

    @ApiOperation(value = "商品下架", notes = "商品下架", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.deleteGood(id, user);
        return lsResponse;
    }

    @ApiOperation(value = "修改商品", notes = "修改商品", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody Good good, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.updateGood(good, user);
        return lsResponse;
    }


    @ApiOperation(value = "商品列表", notes = "商品列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getgoodList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                  @ApiParam(value = "supplierId") @RequestParam(value = "supplierId", required = false) String supplierId,
                                  @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodService.getGoodList(user,keyword,supplierId,categoryId,canteenId,pageNum,pageSize); //pageNum:表示显示第几页、pageSize:表示每页的条数
        return lsResponse;
    }

    @ApiOperation(value = "商品详情", notes = "商品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodInfoByGoodId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAdditiveattributeByGoodId(@ApiParam(value = "goodId", required = true) @RequestParam(value = "goodId") String goodId) {
        LsResponse lsResponse = goodService.getGoodInfoByGoodId(goodId);
        return lsResponse;
    }

    @ApiOperation(value = "商品计划新增时需要的数据", notes = "商品计划新增时需要的数据", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodMINI", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodMINI(@ApiParam(value = "goodPlanId") @RequestParam(value = "goodPlanId", required = false) String goodPlanId,
                                  @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                  @ApiParam(value = "supplierId") @RequestParam(value = "supplierId", required = false) String supplierId,
                                  @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  @ApiParam(value = "pickingTime",required = true) @RequestParam(value = "pickingTime") String pickingTime,
                                  @ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        LsResponse lsResponse = goodService.getGoodMINI(goodPlanId,keyword,supplierId,categoryId,pickingTime,canteenId,pageNum,pageSize);
        return lsResponse;
    }

    /*------------------------------------------------------------------H5用到的方法----------------------------------------------------------------------------------------*/

    @ApiOperation(value = "商品详情", notes = "商品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodInfoByGoodIdH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodInfoByGoodIdH5(@ApiParam(value = "goodId", required = true) @RequestParam(value = "goodId") String goodId,
                                            @ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                            @ApiParam(value = "pickingTime", required = true) @RequestParam(value = "pickingTime") String pickingTime,
                                            @ApiParam(value = "goodPlanId", required = true) @RequestParam(value = "goodPlanId") String goodPlanId,
                                            HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = goodService.getGoodInfoByGoodIdH5(associator,goodId,canteenId,pickingTime,goodPlanId);
        return lsResponse;
    }


    /*------------------------------------------------------------------微信小程序需要用到接口------------------------------------------------------------------------------------------------------*/
    @ApiOperation(value = "商品or菜品详情", notes = "商品or菜品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodOrRecipeByNoWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodOrRecipeByNoWx(@ApiParam(value = "goodOrRecipeId", required = true) @RequestParam(value = "goodOrRecipeId") String goodOrRecipeId, HttpServletRequest request) {
        LsResponse lsResponse = goodService.getGoodOrRecipeByNoWx(goodOrRecipeId);
        return lsResponse;
    }
}
