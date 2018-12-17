package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.RecipePlanThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipePlan;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenguanqi on 2017/6/20.
 */

@Api(value = "recipeplan", tags = "recipeplan", description = "菜谱计划管理")
@Controller
@RequestMapping(value = "/recipeplan")
public class RecipePlanController {
    @Autowired
    private RecipePlanService recipePlanService;
    @Autowired
    private RecipePlanThin recipePlanThin;

    @ApiOperation(value = "菜谱计划新增", notes = "菜谱计划新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(HttpServletRequest request, @RequestBody List<RecipePlan> recipePlan) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanService.insertRecipePlan(user,recipePlan);
        return lsResponse;
    }

    @ApiOperation(value = "删除菜谱计划", notes = "删除菜谱计划", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id")Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanService.deleteRecipePlan(id,user);
        return lsResponse;
    }

    @ApiOperation(value = "删除菜谱计划中的某个菜品", notes = "删除菜谱计划中的某个菜品", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteRecipePlanOneRecipe", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteRecipePlanOneRecipe(@ApiParam(value = "recipePlanId") @RequestParam(value = "recipePlanId", required = false) String recipePlanId,
                                                @ApiParam(value = "recipeId") @RequestParam(value = "recipeId", required = false) String recipeId,
                                                @ApiParam(value = "recipeType") @RequestParam(value = "recipeType", required = false) Integer recipeType,
                                                HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanService.deleteRecipePlanOneRecipe(recipePlanId,recipeId,recipeType,user);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱计划修改", notes = "菜谱计划修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody RecipePlan recipePlan, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanService.updateRecipePlan(recipePlan,user);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱计划修改(修改一天的)", notes = "菜谱计划修改(修改一天的)", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateOneDay", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateOneDay(@RequestBody RecipePlan recipePlan, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanService.updateRecipePlanOneDay(recipePlan,user);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱计划列表,WEB首页显示", notes = "菜谱计划列表，首页显示", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipePlanListWebPageShow", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipePlanListWebPageShow(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                                   @ApiParam(value = "dinnerTime") @RequestParam(value = "dinnerTime", required = false) String dinnerTime,
                                                   @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                                   @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipePlanThin.getRecipePlanListWebPageShow(user,canteenId,dinnerTime,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "点击菜品计划新增时，需要请求的接口", notes = "点击菜品计划新增时，需要请求的接口", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipePlanDetailsList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipePlanDetailsList(@ApiParam(value = "keyword") @RequestParam(value = "keyword",required = false) String keyword,
                                               @ApiParam(value = "canteenId",required = true) @RequestParam(value = "canteenId") String canteenId,
                                               @ApiParam(value = "dinnerTime") @RequestParam(value = "dinnerTime",required = false) String dinnerTime) {
        LsResponse lsResponse = recipePlanService.getRecipePlanDetailsList(keyword,canteenId,dinnerTime);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱计划列表添加备注", notes = "菜谱计划列表添加备注", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/addCommentByRecipePlanId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse addCommentByRecipePlanId(@ApiParam(value = "recipePlanId",required = true) @RequestParam(value = "recipePlanId") String recipePlanId,
                                               @ApiParam(value = "comment") @RequestParam(value = "comment", required = false) String comment) {
        LsResponse lsResponse = recipePlanService.addCommentByRecipePlanId(recipePlanId,comment);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱计划详情展示", notes = "菜谱计划详情展示", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipePlanDetails", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipePlanDetails(@ApiParam(value = "recipePlanId",required = true) @RequestParam(value = "recipePlanId") String recipePlanId){
        LsResponse lsResponse = recipePlanThin.getRecipePlanDetails(recipePlanId);
        return lsResponse;
    }

/*------------------------------------------------------------------以下是H5画面用到的方法------------------------------------------------------------------------------------*/
    /**
     * @param keyword       : 根据商品名称进行搜索
     * @param canteenId     : 餐厅编号
     * @param dinnerTime    : 就餐时间
     * @param categoryId    : 分类编号
     * @param eatType       : 早、午、晚
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "早午晚餐", notes = "早午晚餐", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getEatingDetailsH5Show", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getEatingDetailsH5Show(@ApiParam(value = "keyword") @RequestParam(value = "keyword",required = false) String keyword,
                                             @ApiParam(value = "canteenId",required = true) @RequestParam(value = "canteenId") String canteenId,
                                             @ApiParam(value = "dinnerTime") @RequestParam(value = "dinnerTime",required = false) String dinnerTime,
                                             @ApiParam(value = "categoryId") @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                             @ApiParam(value = "eatType") @RequestParam(value = "eatType",required = false) Integer eatType,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = recipePlanService.getEatingDetailsH5Show(associator,keyword,canteenId,dinnerTime,categoryId,eatType,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "获取明天的日期,改成今天的日期了！！！但是方法名没改", notes = "获取明天的日期,改成今天的日期了", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getTomorrowTime", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getTomorrowTime() {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String,String> timeMap = new HashMap<>();
//        timeMap.put("time",Dates.format(Dates.getAfterDate(new Date(),1),"yyyy-MM-dd"));
        timeMap.put("time", Dates.now("yyyy-MM-dd"));
        lsResponse.setData(timeMap);
        return lsResponse;
    }
}
