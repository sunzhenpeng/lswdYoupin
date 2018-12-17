package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.InstitutionThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Institution;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.InstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/6/7.
 */
@Api(value = "institution", tags = "institution", description = "区域管理")
@Controller
@RequestMapping(value = "/institution")
public class InstitutionController {
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private InstitutionThin institutionThin;

    @ApiOperation(value = "查询商家下所有区域", notes = "查询商家下所有区域", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get/{tenantId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getInstitutionByTenantId(@PathVariable String tenantId) {
        return institutionService.getInstitutionByTenantId(tenantId);
    }

    @ApiOperation(value = "新建或者修改区域", notes = "新建或者修改区域", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addInstitution(@RequestBody Institution institution, HttpServletRequest request) {
        User u = (User) request.getAttribute("user");
        return institutionService.addOrUpdateInstitution(institution, u);
    }

    @ApiOperation(value = "删除区域", notes = "删除区域", nickname = "lrl", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{institutionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteInstitution(@PathVariable String institutionId, HttpServletRequest request) {
        return institutionThin.deleteInstitution(institutionId, request);
    }

    @ApiOperation(value = "区域列表", notes = "获取所有区域", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getAllInstitutions(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                         HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return institutionService.getAllInstitutions(u,keyword, pageNum, pageSize);
    }

    @ApiOperation(value = "区域详情", notes = "区域详情", nickname = "lrl", httpMethod = "GET")
    @RequestMapping(value = "/getDetail/{institutionId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAllInstitutions(@PathVariable String institutionId) {
        return institutionService.getInstitutionByInstitutionId(institutionId);
    }
}
