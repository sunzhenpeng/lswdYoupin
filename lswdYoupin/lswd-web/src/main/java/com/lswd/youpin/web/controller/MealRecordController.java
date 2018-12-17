package com.lswd.youpin.web.controller;


import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MealRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/12/11.
 */
@Api(value = "mealRecord", tags = "mealRecord", description = "结算台就餐记录管理")
@Controller
@RequestMapping(value = "/mealRecord")
public class MealRecordController {
    @Autowired
    private MealRecordService mealRecordService;


    @ApiOperation(value = "查看会员的就餐记录", notes = "查看会员的就餐记录", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberMealRecordList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberMealRecordList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                              @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                              @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                              HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = mealRecordService.getMemberMealRecordList(user, keyword, date, canteenId, pageNum, pageSize);
        return lsResponse;
    }


    @ApiOperation(value = "查看菜品的销售记录", notes = "查看菜品的销售记录", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeMealRecordList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeMealRecordList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                              @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                              @ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                              @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                              HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = mealRecordService.getRecipeMealRecordList(user, keyword, categoryId, date, canteenId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "查看菜品在某个时间段内的销售数量", notes = "查看菜品在某个时间段内的销售数量", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeSaleSpeed", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeSaleSpeed(@ApiParam(value = "date") @RequestParam(value = "date", required = false) String date,
                                         @ApiParam(value = "startTime") @RequestParam(value = "startTime", required = false) String startTime,
                                         @ApiParam(value = "endTime") @RequestParam(value = "endTime", required = false) String endTime,
                                         @ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                         HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = mealRecordService.getRecipeSaleSpeed(user, date, startTime, endTime, canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "结算台流水统计", notes = "统计在结算台会员的就餐消费记录", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getMealRecords", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMealRecords(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                     @ApiParam(value = "leaveId", required = true) @RequestParam(value = "leaveId") Integer leaveId,
                                     @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "startTime", required = true) @RequestParam(value = "startTime") String startTime,
                                     @ApiParam(value = "endTime", required = true) @RequestParam(value = "endTime") String endTime,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                     HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return mealRecordService.getMealRecords(user, canteenId, leaveId,keyword, startTime, endTime, pageNum, pageSize);
    }

    @ApiOperation(value = "结算台营业额统计", notes = "统计在结算台早餐，午餐，晚餐的营业额", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getSales", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getSales(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                               @ApiParam(value = "deviceId", required = true) @RequestParam(value = "deviceId") Integer deviceId,
                              /* @ApiParam(value = "leaveId") @RequestParam(value = "leaveId", required = false) Integer leaveId,*/
                               @ApiParam(value = "startTime", required = true) @RequestParam(value = "startTime") String startTime,
                               @ApiParam(value = "endTime", required = true) @RequestParam(value = "endTime") String endTime,
                               @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                               @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        return mealRecordService.getSales(canteenId, deviceId,startTime, endTime, pageNum, pageSize);
    }


    @ApiOperation(value = "结算台营业额统计,导出功能", notes = "结算台营业额统计,导出功能", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/exportJSTSales", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportJSTSales(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                     @ApiParam(value = "deviceId") @RequestParam(value = "deviceId", required = false) Integer deviceId,
                                     @ApiParam(value = "startTime", required = true) @RequestParam(value = "startTime") String startTime,
                                     @ApiParam(value = "endTime", required = true) @RequestParam(value = "endTime") String endTime, HttpServletResponse response) {
        return mealRecordService.exportJSTSales(canteenId, deviceId, startTime, endTime,response);
    }

    @ApiOperation(value = "个人就餐流水统计", notes = "统计个人当天的刷卡记录", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getPersonalMealRecord", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getPersonalMealRecord(@ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                            @ApiParam(value = "memberId", required = true) @RequestParam(value = "memberId") Integer memberId,
                                            @ApiParam(value = "eatTime", required = true) @RequestParam(value = "eatTime") String eatTime
    ) {
        return mealRecordService.getPersonMealRecords(canteenId, memberId, eatTime);
    }

    @ApiOperation(value = "得到会员的营养成分，折线图", notes = "得到会员的营养成分，折线图", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberNutrition", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberNutrition(@ApiParam(value = "canteeenId") @RequestParam(value = "canteeenId", required = true) String canteeenId,
                                         @ApiParam(value = "memberId") @RequestParam(value = "memberId", required = true) Integer memberId,
                                         @ApiParam(value = "nutritionFlag") @RequestParam(value = "nutritionFlag", required = true) Integer nutritionFlag,
                                         @ApiParam(value = "timeFlag") @RequestParam(value = "timeFlag", required = true) Integer timeFlag,
                                         HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = mealRecordService.getMemberNutrition(user, canteeenId, memberId, nutritionFlag, timeFlag);
        return lsResponse;
    }

}
