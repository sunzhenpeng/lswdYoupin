package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.CounterUserThin;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/29.
 */
@Api(value = "counterUser", tags = "counterUser", description = "吧台用户管理")
@Controller
@RequestMapping(value = "/counterUser")
public class CounterUserController {

    @Autowired
    private CounterUserThin counterUserThin;
    @Autowired
    private CounterUserService counterUserService;

    @ApiOperation(value = "吧台用户管理列表", notes = "吧台用户管理列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getCounterUserList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCounterUserList(@ApiParam(value = "keyword", required = false) @RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        LsResponse lsResponse = counterUserThin.getCounterUserList(keyword, pageNum, pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户新增", notes = "吧台用户新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addCounterUser", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCounterType(@RequestBody CounterUser counterUser, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterUserThin.addCounterUser(counterUser, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户修改", notes = "吧台用户修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterUser", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterUser(@RequestBody CounterUser counterUser, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = counterUserThin.updateCounterUser(counterUser, user);
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户删除", notes = "吧台用户删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCounterUser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCounterUser(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = counterUserThin.deleteCounterUser(id);
        return lsResponse;
    }

    @ApiOperation(value = "修改用户的状态", notes = "修改用户的状态", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/updateCounterUserStatus", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse updateCounterUserStatus(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id,
                                              @ApiParam(value = "status", required = true) @RequestParam("status")Boolean status) {
        LsResponse lsResponse = counterUserThin.updateCounterUserStatus(id,status);
        return lsResponse;
    }

    @ApiOperation(value = "用户重置密码", notes = "用户重置密码", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/resetCounterUserPass", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse resetCounterUserPass(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = counterUserThin.resetCounterUserPass(id,user);
        return lsResponse;
    }

    /*--------------------------------------------------------吧台收银程序，所有用到的接口------------------------------------------------------*/

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateCounterUserPassBT", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateCounterUserPassBT(@RequestBody String passData, HttpServletRequest request) {
        CounterUser counterUser = (CounterUser)request.getAttribute("counterUser");
        LsResponse lsResponse = counterUserService.updateCounterUserPassBT(passData,counterUser);
        return lsResponse;
    }

    @ApiOperation(value = "吧台用户登陆", notes = "吧台用户登陆", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/loginCounterUserBT", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse loginCounterUserBT(@RequestBody String userData, HttpServletRequest request) {
        LsResponse lsResponse = counterUserService.loginCounterUserBT(userData,request);
        return lsResponse;
    }

}
