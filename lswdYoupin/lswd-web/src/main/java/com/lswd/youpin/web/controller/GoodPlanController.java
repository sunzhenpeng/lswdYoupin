package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.GoodPlanThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodPlan;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/15.
 */

@Api(value = "goodplan", tags = "goodplan", description = "商品计划管理")
@Controller
@RequestMapping(value = "/goodplan")
public class GoodPlanController {
    @Autowired
    private GoodPlanService goodPlanService;
    @Autowired
    private GoodPlanThin goodPlanThin;


    /*    @PostMapping(value = "/add")   */
    @ApiOperation(value = "新建商品计划", notes = "新建商品计划", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(HttpServletRequest request, @RequestBody List<GoodPlan> goodPlen) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanService.addGoodPlan(goodPlen,user);
        return lsResponse;
    }

    @ApiOperation(value = "删除商品计划", notes = "删除商品计划", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanService.deleteGoodPlan(id,user);
        return lsResponse;
    }

    @ApiOperation(value = "删除商品计划中的单个商品", notes = "删除商品计划中的单个商品", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteGoodPlanOneGood", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteGoodPlanOneGood(@ApiParam(value = "goodPlanId") @RequestParam(value = "goodPlanId", required = false) String goodPlanId,
                                            @ApiParam(value = "goodId") @RequestParam(value = "goodId", required = false) String goodId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanService.deleteGoodPlanOneGood(goodPlanId,goodId,user);
        return lsResponse;
    }

    @ApiOperation(value = "修改商品计划", notes = "修改商品计划", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(HttpServletRequest request, @RequestBody GoodPlan goodPlan) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanService.updateGoodPlan(goodPlan,user);
        return lsResponse;
    }

    @ApiOperation(value = "修改商品计划(修改一天的)", notes = "修改商品计划(修改一天的)", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateOneDay", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateOneDay(HttpServletRequest request, @RequestBody GoodPlan goodPlan) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanService.updateGoodPlanOneDay(goodPlan,user);
        return lsResponse;
    }

    @ApiOperation(value = "商品计划列表", notes = "商品计划列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodPlanListWebPageShow", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodPlanListWebPageShow(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                                 @ApiParam(value = "pickingTime") @RequestParam(value = "pickingTime", required = false) String pickingTime,
                                                 @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                                 @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = goodPlanThin.getGoodPlanListWebPageShow(user,canteenId,pickingTime,pageNum,pageSize); //pageNum:表示显示第几页、pageSize:表示每页的条数
        return lsResponse;
    }


    @ApiOperation(value = "点击新增时，需要请求的接口", notes = "点击新增时，需要请求的接口", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodPlanDetailsList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodPlanDetailsList(@ApiParam(value = "keyword") @RequestParam(value = "keyword",required = false) String keyword,
                                             @ApiParam(value = "canteenId",required = true) @RequestParam(value = "canteenId") String canteenId,
                                             @ApiParam(value = "pickingTime") @RequestParam(value = "pickingTime",required = false) String pickingTime) {
        LsResponse lsResponse = goodPlanService.getGoodPlanDetailsList(keyword,canteenId,pickingTime);
        return lsResponse;
    }

    @ApiOperation(value = "商品计划列表添加备注", notes = "商品计划列表添加备注", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/addCommentByGoodPlanId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse addCommentByGoodPlanId(@ApiParam(value = "goodPlanId",required = true) @RequestParam(value = "goodPlanId") String goodPlanId,
                                             @ApiParam(value = "comment") @RequestParam(value = "comment", required = false) String comment) {
        LsResponse lsResponse = goodPlanService.addCommentByGoodPlanId(goodPlanId,comment);
        return lsResponse;
    }


    @ApiOperation(value = "商品计划详情", notes = "商品计划详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodPlanDetails", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodPlanDetails(@ApiParam(value = "goodPlanId",required = true) @RequestParam(value = "goodPlanId") String goodPlanId){
        LsResponse lsResponse = goodPlanThin.getGoodPlanDetails(goodPlanId);
        return lsResponse;
    }


/*--------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*H5画面需要用到的方法！！！*/
    @ApiOperation(value = "商品计划中商品展现", notes = "商品计划中商品展现", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getGoodPlanContentH5Show", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodPlanContentH5Show(
                                        @ApiParam(value = "keyword") @RequestParam(value = "keyword",required = false) String keyword,
                                        @ApiParam(value = "canteenId" ,required = true) @RequestParam(value = "canteenId") String canteenId,
                                         @ApiParam(value = "pickingTime") @RequestParam(value = "pickingTime", required = false) String pickingTime,
                                         @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request){
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = goodPlanService.getGoodPlanContentH5Show(associator,keyword,canteenId,pickingTime,categoryId,pageNum,pageSize);
        return lsResponse;
    }
}
