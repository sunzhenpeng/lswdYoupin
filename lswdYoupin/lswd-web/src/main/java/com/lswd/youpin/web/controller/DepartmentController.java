package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Department;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by liuhao on 2017/6/5.
 */
@Api(value = "department", tags = "department", description = "部门管理")
@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "新建部门", notes = "新建部门", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Department department, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            User user = (User) request.getAttribute("user");
            lsResponse = departmentService.add(department, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @ApiOperation(value = "删除部门", notes = "删除部门", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            User user = (User) request.getAttribute("user");
            lsResponse = departmentService.delete(id, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "修改部门信息", notes = "修改部门信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody Department department, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            User user = (User) request.getAttribute("user");
            lsResponse = departmentService.update(department, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }



    @ApiOperation(value = "获取部门列表", notes = "获取部门列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getdepartmentList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getDepartments(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     @ApiParam(value = "institutionId", required = true) @RequestParam("institutionId") String institutionId) {

        LsResponse lsResponse = null;
        try {
            if (keyword != null) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            }
            lsResponse = departmentService.getDepartments(pageNum, pageSize, keyword, institutionId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "查看部门详情", notes = "查看部门详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/lookdepartment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookDepartment(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = null;
        try {
            lsResponse = departmentService.lookDepartment(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

}
