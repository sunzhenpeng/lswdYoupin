package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.CounterThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.model.lsyp.CounterMenuLinked;
import com.lswd.youpin.model.lsyp.CounterUserLinked;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/11/15.
 */
@Api(value = "counter", tags = "counter", description = "吧台管理")
@Controller
@RequestMapping(value = "/counter")
public class CounterController {

    @Autowired
    private CounterService counterService;
    @Autowired
    private CounterThin counterThin;

    @ApiOperation(value = "获取所有的吧台信息", notes = "获取所有的吧台信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterListAll() {
        LsResponse lsResponse = counterService.getCounterListAll();
        return lsResponse;
    }

    @ApiOperation(value = "吧台列表", notes = "吧台列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterList(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                     @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                     HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.getCounterList(user,name, canteenId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台新增", notes = "吧台新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounter", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounter(@RequestBody Counter counter, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterService.addCounter(counter, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台修改", notes = "吧台修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounter", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounter(@RequestBody Counter counter, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterService.updateCounter(counter, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台删除", notes = "吧台删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounter/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounter(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = counterService.deleteCounter(id);
        return lsResponse;
    }

    /*----------------------------------------------------WEB端  吧台统计部分接口 start--------------------------------------------------------------------------------*/

    @ApiOperation(value = "根据登陆用户绑定的餐厅，获取餐厅下的吧台信息", notes = "根据登陆用户绑定的餐厅，获取餐厅下的吧台信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterByCanteenId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterByCanteenId(HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.getCounterByCanteenIds(user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台订单统计", notes = "吧台订单统计", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getBTOrderListWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getBTOrderListWeb(@ApiParam(value = "counterId", required = false) @RequestParam(value = "counterId", required = false) String counterId,
                                        @ApiParam(value = "date", required = false) @RequestParam(value = "date", required = false) String date,
                                        @ApiParam(value = "payType", required = false) @RequestParam(value = "payType", required = false) Integer payType,
                                        @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                        @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                        HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.getBTOrderListWeb(user, counterId, date, payType, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台订单统计，导出功能", notes = "吧台订单统计，导出功能", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/exportBTOrderListWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportBTOrderListWeb(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId") String counterId,
                                           @ApiParam(value = "date", required = false) @RequestParam(value = "date",required = false) String date,
                                           @ApiParam(value = "payType", required = false) @RequestParam(value = "payType",required = false) Integer payType,
                                           HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.exportBTOrderListWeb(user, counterId, date, payType, response);
        return lsResponse;
    }

    @ApiOperation(value = "会员消费记录查询", notes = "会员消费记录查询", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberBTOrderListWEB", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberBTOrderListWEB(@ApiParam(value = "counterId") @RequestParam(value = "counterId", required = false) String counterId,
                                              @ApiParam(value = "memberName") @RequestParam(value = "memberName", required = false) String memberName,
                                              @ApiParam(value = "memberCardUid") @RequestParam(value = "memberCardUid", required = false) String memberCardUid,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                              HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.getMemberBTOrderListWEB(user, counterId, memberName, memberCardUid, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "会员消费记录查询,根据订单号查到订单详情", notes = "会员消费记录查询，根据订单号查到订单详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberBTOrderItemsWEB", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberBTOrderItemsWEB(@ApiParam(value = "orderId", required = true) @RequestParam(value = "orderId", required = true) String orderId,
                                               HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterService.getMemberBTOrderItemsWEB(user, orderId);
        return lsResponse;
    }

    /*----------------------------------------------------WEB端  吧台统计部分接口 end----------------------------------------------------------------------------------*/


    /*-----------------------------------------------------吧台用户部分   start-----------------------------------------------------------------------*/

    @ApiOperation(value = "吧台绑定用户，右侧弹出用户列表，没有分页信息", notes = "吧台绑定用户，右侧弹出用户列表，没有分页信息", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getBingCounterUserList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getBingCounterUserList(@ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                             @ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId) {
        LsResponse lsResponse = counterThin.getBingCounterUserList(keyword, counterId);
        return lsResponse;
    }


    @ApiOperation(value = "吧台绑定用户，保存接口", notes = "吧台绑定用户，保存接口", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCountBingUser", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCountBingUser(@RequestBody CounterUserLinked counterUserLinked, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterService.addCountBingUser(counterUserLinked, user);
        return lsResponse;
    }


    @ApiOperation(value = "吧台用户列表", notes = "吧台用户列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterUserLinkedList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterUserLinkedList(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                               @ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword) {
        LsResponse lsResponse = counterThin.getCounterUserLinkedList(counterId, keyword);
        return lsResponse;
    }


    @ApiOperation(value = "吧台绑定用户,删除接口", notes = "吧台绑定用户,删除接口", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteCounterUserLinked", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteCounterUserLinked(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                              @ApiParam(value = "userId", required = true) @RequestParam(value = "userId", required = true) String userId) {
        LsResponse lsResponse = counterService.deleteCounterUserLinked(counterId, userId);
        return lsResponse;
    }
    /*-----------------------------------------------------吧台用户部分  end------------------------------------------------------------------------*/


    /*-----------------------------------------------------吧台菜品部分  start------------------------------------------------------------------------*/
    @ApiOperation(value = "吧台绑定菜品，右侧弹出菜品列表，有分页", notes = "吧台绑定菜品，右侧弹出菜品列表，有分页", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getBingMenuList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getBingMenuList(@ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                      @ApiParam(value = "menutypeId", required = false) @RequestParam(value = "menutypeId", required = false) Integer menutypeId,
                                      @ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        LsResponse lsResponse = counterService.getBingMenuList(keyword, menutypeId, counterId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台绑定菜品，保存接口", notes = "吧台绑定菜品，保存接口", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCountBingMenu", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCountBingMenu(@RequestBody CounterMenuLinked counterMenuLinked, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterService.addCountBingMenu(counterMenuLinked, user);
        return lsResponse;
    }

    @ApiOperation(value = "获取吧台绑定菜品列表", notes = "获取吧台绑定菜品列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterMenuLinkedList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterMenuLinkedList(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                               @ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                               @ApiParam(value = "menutypeId", required = false) @RequestParam(value = "menutypeId", required = false) Integer menutypeId,
                                               @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                               @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        LsResponse lsResponse = counterService.getCounterMenuLinkedList(counterId, keyword, menutypeId, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台绑定菜品,删除接口", notes = "吧台绑定菜品,删除接口", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteCounterMenuLinked", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteCounterMenuLinked(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId", required = true) String counterId,
                                              @ApiParam(value = "menuId", required = true) @RequestParam(value = "menuId", required = true) String menuId) {
        LsResponse lsResponse = counterService.deleteCounterMenuLinked(counterId, menuId);
        return lsResponse;
    }

    /*-----------------------------------------------------吧台菜品部分接口  end------------------------------------------------------------------------*/

    /*-----------------------------------------------------吧台收银程序部分接口------------------------------------------------------------------------*/
    @ApiOperation(value = "根据吧台绑定的菜品得到菜品分类", notes = "根据吧台绑定的菜品得到菜品分类", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterMenuType", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterMenuType(@ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId") String counterId) {
        LsResponse lsResponse = counterService.getCounterMenuType(counterId);
        return lsResponse;
    }

    @ApiOperation(value = "根据菜品分类得到吧台绑定的菜品", notes = "根据菜品分类得到吧台绑定的菜品", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterMenuByTypeId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterMenuByTypeId(@ApiParam(value = "typeId", required = true) @RequestParam(value = "typeId") Integer typeId,
                                             @ApiParam(value = "counterId", required = true) @RequestParam(value = "counterId") String counterId) {
        LsResponse lsResponse = counterService.getCounterMenuByTypeId(typeId, counterId);
        return lsResponse;
    }

}
