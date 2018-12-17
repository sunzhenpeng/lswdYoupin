package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Role;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/5.
 */
@Api(value = "role", tags = "role", description = "角色管理")
@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新建或者修改角色", notes = "新家或者修改角色", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateRole", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addRole(@RequestBody Role role, HttpServletRequest request) {
        return roleService.addOrUpdateRole(role,request);
    }

    @ApiOperation(value = "删除角色", notes = "删除角色", nickname = "lrl", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }

    @ApiOperation(value = "角色列表", notes = "获取所有角色", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAllRoles(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                  HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return roleService.getAllRoles(keyword, pageNum, pageSize,u.getTenantId());
    }

    @ApiOperation(value = "角色详情", notes = "根据角色编号获取角色", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getRoleById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRoleById(@PathVariable("id") Integer id) {
        return roleService.getRoleById(id);
    }

    @ApiOperation(value = "角色赋权", notes = "角色赋权", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addRoleResource", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addRoleResource(@RequestBody Map<String, String> map) {
        return roleService.addRoleResource(map);
    }

    @ApiOperation(value = "获取所有角色的编号和名称", notes = "获取所有角色的编号和名称", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getRoleResource/part", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRolePart(HttpServletRequest request) {
        //从request 中获取用户信息
        User u=(User)request.getAttribute("user");
        return roleService.getRolePart(u.getTenantId());
    }
    @ApiOperation(value = "获取用户具有的角色权限", notes = "获取用户具有的角色权限", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getUserResource/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse geUserResources(@PathVariable Integer userId, HttpServletRequest request) {
        //从request 中获取用户信息
        return roleService.getRoleResourceByUserId(userId);
    }
}
