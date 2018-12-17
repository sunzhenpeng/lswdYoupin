package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.AssociatorThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/10.
 */
@Api(value = "associator", tags = "associator", description = "会员管理")
@Controller
@RequestMapping(value = "/associator")
public class AssociatorController {

    @Autowired
    private AssociatorThin associatorThin;

    @Autowired
    private AssociatorService associatorService;

    @ApiOperation(value = "会员注册", notes = "会员通过手机号注册", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addAssociator(@RequestBody String data) {
        return associatorService.registerAssociator(data);
    }

    @ApiOperation(value = "会员登录", notes = "会员通过手机号登录", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse login(@RequestBody Associator associator) {
        return associatorService.login(associator);
    }

    @ApiOperation(value = "会员锁定(解锁)", notes = "会员锁定(解锁)", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lock(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id,
                           @ApiParam(value = "lock", required = true) @RequestParam("lock") Boolean lock) {
        LsResponse lsResponse = associatorThin.lock(id, lock);
        return lsResponse;
    }

    @ApiOperation(value = "会员绑定餐厅列表", notes = "会员绑定餐厅列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/canteenList/{associatorId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse canteenList(@PathVariable(value = "associatorId") String associatorId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = associatorThin.canteenList(associatorId, user);
        return lsResponse;
    }

    @ApiOperation(value = "删除会员", notes = "删除会员", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteAssociator(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = associatorThin.deleteAssociator(id);
        return lsResponse;
    }

    @ApiOperation(value = "会员信息修改", notes = "会员信息修改", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateAssociator(@RequestBody Associator associator) {
        LsResponse lsResponse = associatorThin.updateAssociator(associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员列表(搜索)", notes = "会员列表(搜索)", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/associatorList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAssociatorList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                        @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                        @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                        @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                        HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = associatorThin.getAssociatorList(keyword, pageNum, pageSize, user, canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "查看会员详细信息", notes = "会员列表(查看会员详细信息)", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/look/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookAssociator(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = associatorThin.lookAssociator(id);
        return lsResponse;
    }

    @ApiOperation(value = "会员绑定餐厅", notes = "会员绑定餐厅", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse bindCanteen(@RequestBody String data, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.bindCanteen(data, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员充值记录", notes = "会员充值记录", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse pay(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                          @ApiParam(value = "associatorId") @RequestParam(value = "associatorId", required = false) String associatorId,
                          @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                          @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        LsResponse lsResponse = associatorThin.pay(associatorId, keyword, pageSize, pageNum);
        return lsResponse;
    }

    @ApiOperation(value = "会员信息", notes = "会员信息", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/see", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse seeAssociator(HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.seeAssociator(associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员找回密码", notes = "会员找回密码", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updatePassword(@RequestBody String data) {
        LsResponse lsResponse = associatorService.updatePassword(data);
        return lsResponse;
    }

    @ApiOperation(value = "获取会员电话号码", notes = "获取会员电话号码", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getPhone", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getTelePhone(HttpServletRequest request) {
        LsResponse lsResponse = new LsResponse();
        Associator associator = (Associator) request.getAttribute("associator");
        if (associator != null) {
            lsResponse.setData(associator.getTelephone());
        } else {
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_YE_LOGIN.name());
        }
        return lsResponse;
    }

    @ApiOperation(value = "会员修改登录密码", notes = "会员修改登录密码", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateLoginPwd(@RequestBody String data, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.updateLoginPwd(data, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员修改支付密码", notes = "会员修改支付密码", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updatePayPwd(@RequestBody String data, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.updatePayPwd(data, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员修改绑定手机号", notes = "会员修改绑定手机号", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updatePhone(@RequestBody String data, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.updatePhone(data, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员账户余额", notes = "会员账户余额", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMoney(HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.getMoney(associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员解除绑定餐厅", notes = "会员解除绑定餐厅", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/removeCanteen/{canteenId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse removeCanteen(@PathVariable(value = "canteenId") String canteenId, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.removeCanteen(canteenId, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员是否绑定该餐厅", notes = "会员是否绑定该餐厅", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/isBindCanteen/{canteenId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse isBindCanteen(@PathVariable(value = "canteenId") String canteenId, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.isBindCanteen(canteenId, associator);
        return lsResponse;
    }

    @ApiOperation(value = "追加手机号", notes = "追加手机号", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/addPhone", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addPhone(@RequestBody String tel, HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = associatorThin.addPhone(tel, associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员绑卡", notes = "会员绑卡", nickname = "liruilong", httpMethod = "POST")
    @RequestMapping(value = "/bindCard", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse bindCard(@RequestBody String data, HttpServletRequest request) {
        return associatorThin.bindCard(data, request);
    }
    @ApiOperation(value = "获取会员绑定的餐余额", notes = "通过会员绑定的member_id 获取餐卡的余额", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getBalance(HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        return associatorService.getBalance(associator.getMemberId());
    }
}
