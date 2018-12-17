package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberType;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberTypeService;
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
@Api(value = "memberType", tags = "memberType", description = "结算台的结算台会员类型管理")
@Controller
@RequestMapping(value = "/memberType")
public class MemberTypeController {

    @Autowired
    private MemberTypeService memberTypeService;

    @ApiOperation(value = "结算台会员类型列表", notes = "结算台会员类型列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getMemberTypeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMemberTypeList(@ApiParam(value = "name", required = false) @RequestParam(value = "name",required = false) String name)
    {
        LsResponse lsResponse=memberTypeService.getMemberTypeList(name);
        return  lsResponse;
    }

    @ApiOperation(value = "结算台会员类型新增", notes = "结算台会员类型新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addMemberType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addMemberType(@RequestBody MemberType MemberType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=memberTypeService.addMemberType(MemberType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "结算台会员类型修改", notes = "结算台会员类型修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateMemberType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateMemberType(@RequestBody MemberType MemberType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=memberTypeService.updateMemberType(MemberType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "结算台会员类型删除", notes = "结算台会员类型删除", nickname = "zhenguanqi", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteMemberType/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteMemberType(@PathVariable(value = "id")Integer id)
    {
        LsResponse lsResponse=memberTypeService.deleteMemberType(id);
        return  lsResponse;
    }


}
