package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.EmployeesMapper;
import com.lswd.youpin.lsy.EmployeesService;
import com.lswd.youpin.model.lsy.Employees;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by sunzhenpeng on 20180811
 */
@Api(value = "employees", tags = "employees", description = "员工管理")
@Controller
@RequestMapping(value = "/employees")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;
    @Autowired
    EmployeesMapper employeesMapper;
    @ApiOperation(value = "员工列表", notes = "员工列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getEmployeesList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
        public LsResponse getEmployeesList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                           @ApiParam(value = "positionId") @RequestParam(value = "positionId", required = false) Integer positionId,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) throws UnsupportedEncodingException {

        return employeesService.getEmployeesList(keyword,positionId,pageNum,pageSize);
    }

    @ApiOperation(value = "员工详情", notes = "员工详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getEmployeesById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getEmployeesById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        return employeesService.getEmployeesById(id);
    }

    @ApiOperation(value = "新建员工", notes = "新建员工", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateEmployees", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateEmployees(@RequestBody Employees employees, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = employeesService.addOrUpdateEmployees(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除员工", notes = "删除员工", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteEmployees(@PathVariable Integer id, HttpServletRequest request) {
        return employeesService.delEmployees(id);
    }








}
