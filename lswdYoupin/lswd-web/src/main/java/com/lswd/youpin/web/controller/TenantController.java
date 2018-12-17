package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Tenant;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by liruilongs on 2017/6/7.
 */
@Api(value = "tenant", tags = "tenant", description = "商家管理")
@Controller
@RequestMapping(value = "/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @ApiOperation(value = "商家详情", notes = "商家详情", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/{tenantId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getTenantByTenantId(@PathVariable String tenantId) {
        return tenantService.getTenantByTenantId(tenantId);
    }

    @ApiOperation(value = "新建或者修改商家", notes = "新建或者修改商家", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addTenant(@RequestBody Tenant tenant, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return tenantService.addOrUpdateTenant(tenant,u);

    }

    @ApiOperation(value = "删除商家", notes = "删除商家", nickname = "lrl", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteTenant(@PathVariable Integer id, HttpServletRequest request) {
        return tenantService.deleteTenant(id);
    }

    @ApiOperation(value = "商家列表", notes = "商家列表", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getAllTenant(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                   @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                   @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
        return tenantService.getAllTenants(u.getTenantId(), keyword,pageNum, pageSize);
    }


}
