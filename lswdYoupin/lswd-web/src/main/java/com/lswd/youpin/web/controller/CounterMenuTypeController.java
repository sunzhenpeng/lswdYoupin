package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MenuType;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterMenuTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/24.
 */
@Api(value = "counterMenuType", tags = "counterMenuType", description = "吧台菜品类型管理")
@Controller
@RequestMapping(value = "/counterMenuType")
public class CounterMenuTypeController {
    @Autowired
    private CounterMenuTypeService counterMenuTypeService;


    @ApiOperation(value = "吧台菜品类型列表", notes = "吧台菜品类型列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getcounterMenuTypeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getcounterMenuTypeList(@ApiParam(value = "name", required = false) @RequestParam(value = "name",required = false) String name,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum",required = true) Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize",required = true) Integer pageSize)
    {
        LsResponse lsResponse=counterMenuTypeService.getcounterMenuTypeList(name,pageNum,pageSize);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台菜品类型新增", notes = "吧台菜品类型新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounterMenuType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounterMenuType(@RequestBody MenuType menuType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=counterMenuTypeService.addCounterMenuType(menuType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台菜品类型修改", notes = "吧台菜品类型修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterMenuType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterMenuType(@RequestBody MenuType menuType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=counterMenuTypeService.updateCounterMenuType(menuType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "吧台菜品类型删除", notes = "吧台菜品类型删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterMenuType/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterMenuType(@PathVariable(value = "id")Integer id)
    {
        LsResponse lsResponse=counterMenuTypeService.deleteCounterMenuType(id);
        return  lsResponse;
    }


}
