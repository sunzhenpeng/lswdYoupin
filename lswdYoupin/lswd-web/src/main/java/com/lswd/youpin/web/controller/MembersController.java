package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
@Api(value = "members", tags = "members", description = "结算台的会员管理")
@Controller
@RequestMapping(value = "/members")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @ApiOperation(value = "结算台会员列表(搜索)Web端，结算台会员列表数据", notes = "结算台会员列表(搜索)Web端，结算台会员列表数据", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMembersList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMembersList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                     @ApiParam(value = "typeId") @RequestParam(value = "typeId", required = false) Integer typeId,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.getMembersList(user,keyword, pageNum, pageSize, canteenId, typeId);
        return lsResponse;
    }

    @ApiOperation(value = "点击读卡触发的事件，结算台会员列表根据卡号进行搜索", notes = "点击读卡触发的事件，结算台会员列表根据卡号进行搜索", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMembersByUID", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMembersByUID(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.getMembersByUID(user);
        return lsResponse;
    }

    @ApiOperation(value = "Web端结算台会员添加事件", notes = "Web端结算台会员添加事件", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addMembersWeb", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addMembersWeb(@RequestBody Members Members, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.addMembersWeb(Members, user);
        return lsResponse;
    }

    @ApiOperation(value = "Web端结算台会员信息修改事件", notes = "Web端结算台会员信息修改事件", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateMembersWeb", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateMembersWeb(@RequestBody Members Members, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.updateMembersWeb(Members, user);
        return lsResponse;
    }

    @ApiOperation(value = "删除结算台会员", notes = "删除结算台会员", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteMembersWeb/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteMembersWeb(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = membersService.deleteMembersWeb(id);
        return lsResponse;
    }

    @ApiOperation(value = "得到结算台会员详情", notes = "得到结算台会员详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMembersInfoWeb/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMembersInfoWeb(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.getMembersInfoWeb(id, user);
        return lsResponse;
    }

    @ApiOperation(value = "WEB端，结算台会员充值", notes = "WEB端，结算台会员充值", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/giveMembersRecharge", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse giveMembersRecharge(@ApiParam(value = "memberId", required = true) @RequestParam(value = "memberId", required = true) String memberId,
                                          @ApiParam(value = "money", required = true) @RequestParam(value = "money", required = true) float money, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.giveMembersRecharge(memberId, money, user);
        return lsResponse;
    }

    @ApiOperation(value = "WEB端，结算台会员退款", notes = "WEB端，结算台会员退款", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/refundMembersMoney", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse refundMembersMoney(@ApiParam(value = "memberId", required = true) @RequestParam(value = "memberId", required = true) String memberId,
                                         @ApiParam(value = "money", required = true) @RequestParam(value = "money", required = true) float money, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.refundMembersMoney(memberId, money, user);
        return lsResponse;
    }

    @ApiOperation(value = "Web端结算台会员添加,点击读卡触发的事件", notes = "Web端结算台会员添加,点击读卡触发的事件", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/existCardUID", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse existCardUID(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = membersService.existCardUID(user);
        return lsResponse;
    }

    @ApiOperation(value = "修改会员状态，正常、禁用", notes = "修改会员状态，正常、禁用", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/updateMemberStatus", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lock(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id,
                           @ApiParam(value = "lock", required = true) @RequestParam("lock") Boolean lock) {
        LsResponse lsResponse = membersService.updateMemberStatus(id, lock);
        return lsResponse;
    }

}
