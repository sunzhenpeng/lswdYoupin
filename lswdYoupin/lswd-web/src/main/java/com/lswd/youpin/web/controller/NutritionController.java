package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Nutrition;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.NutritionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/12/2.
 */
@Api(value = "nutrition", tags = "nutrition", description = "营养成分管理")
@Controller
@RequestMapping(value = "/nutrition")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @ApiOperation(value = "营养成分列表", notes = "营养成分列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getNutritionList(@ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                       @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                       @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        LsResponse lsResponse = nutritionService.getNutritionList(keyword, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "营养成分列表,获取所有的营养成分", notes = "营养成分列表,获取所有的营养成分", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getNutritionListAll() {
        LsResponse lsResponse = nutritionService.getNutritionListAll();
        return lsResponse;
    }

    @ApiOperation(value = "营养成分新增", notes = "营养成分新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addNutrition", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addNutrition(@RequestBody Nutrition nutrition, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = nutritionService.addNutrition(nutrition, user);
        return lsResponse;
    }

    @ApiOperation(value = "营养成分修改", notes = "营养成分修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateNutrition", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateNutrition(@RequestBody Nutrition nutrition, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = nutritionService.updateNutrition(nutrition, user);
        return lsResponse;
    }

    @ApiOperation(value = "营养成分删除", notes = "营养成分删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteNutrition/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteNutrition(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = nutritionService.deleteNutrition(id);
        return lsResponse;
    }

    @ApiOperation(value = "根据name检索营养成分,菜品添加营养成分时", notes = "根据name检索营养成分,菜品添加营养成分时", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionByName", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getNutritionByName(@ApiParam(value = "name", required = false) @RequestParam(value = "name", required = false) String name) {
        LsResponse lsResponse = nutritionService.getNutritionByName(name);
        return lsResponse;
    }


    @ApiOperation(value = "导出Execl文件(数据库内容)", notes = "导出Execl文件(数据库内容)", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/exportnNutritionList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportnNutritionList(HttpServletResponse response, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = nutritionService.exportnNutritionList(user,response);
        return lsResponse;
    }
}
