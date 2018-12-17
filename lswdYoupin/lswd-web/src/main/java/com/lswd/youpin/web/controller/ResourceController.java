package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/12/11.
 */
@Api(value = "resource", tags = "resource", description = "菜单管理11")
@Controller
@RequestMapping(value = "/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "查询子菜单111", notes = "查询子菜单", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getByParentId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getResourceByParentId(@PathVariable("id") Integer id, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return resourceService.getResourceByParentId(id,u);
    }

    @ApiOperation(value = "查询所有菜单", notes = "查询所有菜单", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getResourceAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getResource(HttpServletRequest request) {

        return resourceService.getResourceListAll();
    }

}
