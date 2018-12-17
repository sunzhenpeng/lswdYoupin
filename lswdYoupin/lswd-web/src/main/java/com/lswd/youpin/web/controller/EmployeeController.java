package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.EmployeeThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Employee;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/6.
 */
@Api(value = "employee", tags = "employee", description = "员工管理")
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeThin employeeThin;


    @ApiOperation(value = "新建员工", notes = "新家员工", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Employee employee, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = employeeService.addEmployee(employee, user);
        return lsResponse;
    }
    @ApiOperation(value = "删除员工", notes = "删除员工", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{employeeid}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "employeeid") String employeeId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = employeeThin.deleteEmployee(employeeId,user);
        return lsResponse;
    }


    @ApiOperation(value = "修改员工", notes = "修改员工", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody Employee employee, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = employeeService.updateEmployee(employee, user);
        return lsResponse;
    }



    @ApiOperation(value = "获取员工列表", notes = "获取员工列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getemployeeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getEmployees(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                   @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                   @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                   @ApiParam(value = "regionId") @RequestParam(value = "regionId",required = false) String regionId,
                                   HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = employeeService.getEmployees(regionId, pageNum, pageSize, keyword, user);
        return lsResponse;

    }

    @ApiOperation(value = "查看员工详情", notes = "查看员工详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/lookEmployee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookEmployee(@PathVariable(value = "id") Integer id) {
        LsResponse lsResponse = employeeService.lookEmployee(id);
        return lsResponse;
    }

}
