package com.lswd.youpin.web.controller;


import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipeSecKill;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeSecKillService;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/8/19.
 */
@Api(value = "recipeSecKill", tags = "recipeSecKill", description = "菜谱秒杀管理")
@Controller
@RequestMapping(value = "recipeSecKill")
public class RecipeSecKillController {

    @Autowired
    private RecipeSecKillService recipeSecKillService;

    @ApiOperation(value = "Web端，秒杀菜品列表", notes = "Web端，秒杀菜品列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeSecKillListWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeSecKillListWeb(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                              @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                              @ApiParam(value = "dinnerTime") @RequestParam(value = "dinnerTime", required = false) String dinnerTime,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        User user = (User) request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("zhenguanqi");
            user.setCanteenIds("can001,can002");
        }
        LsResponse lsResponse = recipeSecKillService.getRecipeSecKillListWeb(user,keyword,canteenId,dinnerTime,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "Web端，秒杀菜品，删除", notes = "Web端，秒杀菜品，删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse delete(@ApiParam(value = "id")Integer id, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        User user = (User) request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("zhenguanqi");
            user.setCanteenIds("can001,can002");
        }
        LsResponse lsResponse = recipeSecKillService.delete(user,id);
        return lsResponse;
    }

    @ApiOperation(value = "Web端，秒杀菜品，新增或修改", notes = "Web端，秒杀菜品，新增或修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdate(@RequestBody RecipeSecKill recipeSecKill, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        User user = (User) request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("zhenguanqi");
            user.setCanteenIds("can001,can002");
        }
        LsResponse lsResponse = recipeSecKillService.addOrUpdate(user,recipeSecKill);
        return lsResponse;
    }

    @ApiOperation(value = "H5端，秒杀菜品列表", notes = "H5端，秒杀菜品列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeSecKillListH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeSecKillListH5(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                             @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                             @ApiParam(value = "dinnerTime") @RequestParam(value = "dinnerTime", required = false) String dinnerTime,
                                             @ApiParam(value = "eatType") @RequestParam(value = "eatType",required = false) Integer eatType,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        User user = (User) request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("zhenguanqi");
            user.setCanteenIds("can001,can002");
        }
        LsResponse lsResponse = recipeSecKillService.getRecipeSecKillListH5(user,keyword,canteenId,dinnerTime,eatType,pageNum,pageSize);
        return lsResponse;
    }
}
