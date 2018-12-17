package com.lswd.youpin.web.controller;


import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterMenu;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterMenuService;
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
@Api(value = "counterMenu", tags = "counterMenu", description = "吧台菜品信息管理")
@Controller
@RequestMapping(value = "/counterMenu")
public class CounterMenuController {

    @Autowired
    private CounterMenuService counterMenuService;

    @ApiOperation(value = "获取所有的吧台菜品分类", notes = "获取所有的吧台菜品分类", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getcounterMenuTypeListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getcounterMenuTypeListAll() {
        LsResponse lsResponse = counterMenuService.getcounterMenuTypeListAll();
        return lsResponse;
    }

    @ApiOperation(value = "吧台菜品信息列表", notes = "吧台菜品信息列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterMenuList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterMenuList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiParam(value = "counterId") @RequestParam(value = "counterId", required = false) String counterId,
                                         @ApiParam(value = "menutypeId") @RequestParam(value = "menutypeId", required = false) Integer menutypeId,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        LsResponse lsResponse = counterMenuService.getCounterMenuList(keyword, counterId, menutypeId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台菜品信息新增请求", notes = "吧台菜品信息新增请求", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounterMenu", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounterMenu(@RequestBody CounterMenu counterMenu, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterMenuService.addCounterMenu(counterMenu, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台菜品信息修改请求", notes = "吧台菜品信息修改请求", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterMenu", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterMenu(@RequestBody CounterMenu counterMenu, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterMenuService.updateCounterMenu(counterMenu, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台菜品信息删除请求", notes = "吧台菜品信息删除请求", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterMenu/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterMenu(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterMenuService.deleteCounterMenu(id, user);
        return lsResponse;
    }


    /* -------------------------------------------------吧台收银员 菜品信息 增删改查-----------------------------------------------------------*/

    @ApiOperation(value = "吧台菜品信息列表", notes = "吧台菜品信息列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterMenuListBTSY", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterMenuListBTSY(@ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                             @ApiParam(value = "menutypeId", required = false) @RequestParam(value = "menutypeId", required = false) Integer menutypeId,
                                             @ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId") String counterId,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        LsResponse lsResponse = counterMenuService.getCounterMenuListBTSY(keyword, counterId, menutypeId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台收银员菜品信息新增请求", notes = "吧台收银员菜品信息新增请求", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounterMenuBTSY", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounterMenuBTSY(@RequestBody CounterMenu counterMenu, HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterMenuService.addCounterMenuBTSY(counterMenu, counterUser);
        return lsResponse;
    }

    @ApiOperation(value = "吧台收银员菜品信息修改请求", notes = "吧台收银员菜品信息修改请求", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterMenuBTSY", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterMenuBTSY(@RequestBody CounterMenu counterMenu, HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterMenuService.updateCounterMenuBTSY(counterMenu, counterUser);
        return lsResponse;
    }

    @ApiOperation(value = "吧台收银员菜品信息删除请求", notes = "吧台收银员菜品信息删除请求", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterMenuBTSY/{menuId}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterTypeBTSY(@PathVariable(value = "menuId") String menuId, HttpServletRequest request) {
        CounterUser counterUser = (CounterUser) request.getAttribute("counterUser");
        LsResponse lsResponse = counterMenuService.deleteCounterMenuBTSY(menuId, counterUser);
        return lsResponse;
    }

}
