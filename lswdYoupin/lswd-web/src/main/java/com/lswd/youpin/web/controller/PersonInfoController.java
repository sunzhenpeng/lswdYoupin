package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.PersonInfoService;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by H61M-K on 2018/2/7.
 */
@Api(value = "PersonInfo", tags = "PersonInfo", description = "个人开卡信息")
@Controller
@RequestMapping(value = "/personInfo")
public class PersonInfoController {
    @Autowired
    private PersonInfoService personInfoService;


    @ApiOperation(value = "开卡人员信息", notes = "开卡人员信息", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse get() {
        DataSourceHandle.setDataSourceType("DSSS");
        LsResponse lsResponse = new LsResponse();
        lsResponse.setData(personInfoService.getAll());
        return lsResponse;
    }

    @ApiOperation(value = "获取每个班级的人数", notes = "获取每个班级的人数", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getStuCountClass", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getStuCountClass() {
        DataSourceHandle.setDataSourceType("DSSS");
        LsResponse lsResponse = personInfoService.getStuCountClass();
        return lsResponse;
    }

    @ApiOperation(value = "获取每个区域的学生人数", notes = "获取每个区域的学生人数", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRegionStudentCount", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRegionStudentCount() {
        DataSourceHandle.setDataSourceType("DSSS");
        LsResponse lsResponse = personInfoService.getRegionStudentCount();
        return lsResponse;
    }


    @ApiOperation(value = "同步会员信息，测试调用", notes = "同步会员信息，测试调用", nickname = "zhengunanqi", httpMethod = "GET")
    @RequestMapping(value = "/synPersonInfo", method = RequestMethod.GET)
    @ResponseBody
    public void synPersonInfo() {
        personInfoService.synPersonInfo();
    }
}
