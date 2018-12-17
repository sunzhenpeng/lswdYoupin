package com.lswd.youpin.web.controller;
import com.lswd.youpin.Thin.AssociatorTypeThin;
import com.lswd.youpin.model.AssociatorType;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/*该Controller取消，没有任何用，没有任何请求，改为业务库中的memberType表*/

/**
 * Created by zhenguanqi on 2017/11/15.
 */
@Api(value = "associatorType", tags = "associatorType", description = "会员类型管理")
@Controller
@RequestMapping(value = "/associatorType")
public class AssociatorTypeController {
    @Autowired
    private AssociatorTypeThin associatorTypeThin;

    @ApiOperation(value = "会员类型列表", notes = "会员类型列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getAssociatorTypeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAssociatorTypeList(@ApiParam(value = "name", required = false) @RequestParam(value = "name",required = false) String name)
    {
        LsResponse lsResponse=associatorTypeThin.getAssociatorTypeList(name);
        return  lsResponse;
    }

    @ApiOperation(value = "会员类型新增", notes = "会员类型新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addAssociatorType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addAssociatorType(@RequestBody AssociatorType associatorType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=associatorTypeThin.addAssociatorType(associatorType,user);
        return  lsResponse;
    }

    @ApiOperation(value = "会员类型修改", notes = "会员类型修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/updateAssociatorType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateAssociatorType(@RequestBody AssociatorType associatorType, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse=associatorTypeThin.updateAssociatorType(associatorType,user);
        return  lsResponse;
    }

}
