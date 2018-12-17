package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterType;
import com.lswd.youpin.model.lsyp.CounterTypeRecipeCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/15.
 * 该模块暂时没有用到！！！！
 */
@Api(value = "counterType", tags = "counterType", description = "吧台类型管理")
@Controller
@RequestMapping(value = "/counterType")
public class CounterTypeController {

    @Autowired
    private CounterTypeService counterTypeService;

    @ApiOperation(value = "吧台类型列表", notes = "吧台类型列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterTypeList(@ApiParam(value = "name", required = false) @RequestParam(value = "name",required = false) String name)
    {
        LsResponse lsResponse=counterTypeService.getCounterTypeList(name);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台类型新增", notes = "吧台类型新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounterType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounterType(@RequestBody CounterType counterType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=counterTypeService.addCounterType(counterType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台类型修改", notes = "吧台类型修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterType(@RequestBody CounterType counterType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=counterTypeService.updateCounterType(counterType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台类型删除", notes = "吧台类型删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterType/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterType(@PathVariable(value = "id")Integer id)
    {
        LsResponse lsResponse=counterTypeService.deleteCounterType(id);
        return  lsResponse;
    }


    @ApiOperation(value = "吧台类型绑定菜品类型列表展示", notes = "吧台类型绑定菜品类型列表展示", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterTypeRecipeCateList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterTypeRecipeCateList(@ApiParam(value = "counterTypeId", required = false) @RequestParam(value = "counterTypeId",required = false) Integer counterTypeId,
                                                   @ApiParam(value = "canteenId", required = false) @RequestParam(value = "canteenId",required = false) String canteenId)
    {
        LsResponse lsResponse=counterTypeService.getCounterTypeRecipeCateList(counterTypeId,canteenId);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台类型绑定菜品类型", notes = "吧台类型绑定菜品类型", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/bindingCounterTypeRecipeCate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse bindingCounterTypeRecipeCate(@RequestBody CounterTypeRecipeCategory counterTypeRecipeCategory, HttpServletRequest request)
    {
        User user = (User)request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("null null 3 null null");
        }
        LsResponse lsResponse=counterTypeService.bindingCounterTypeRecipeCate(counterTypeRecipeCategory,user);
        return  lsResponse;
    }

    @ApiOperation(value = "删除吧台类型绑定菜品类型", notes = "删除吧台类型绑定菜品类型", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterTypeRecipeCate/{counterTypeId}/{recipeCategoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterTypeRecipeCate(@PathVariable(value = "counterTypeId")Integer counterTypeId, @PathVariable(value = "recipeCategoryId")Integer recipeCategoryId)
    {
        LsResponse lsResponse=counterTypeService.deleteCounterTypeRecipeCate(counterTypeId,recipeCategoryId);
        return  lsResponse;
    }


}
