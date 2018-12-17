package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.ImportOrExportThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.ImportOrExportService;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/6/30.
 */

@Api(value = "importOrExport", tags = "importOrExport", description = "导入导出EXECL文件")
@Controller
@RequestMapping(value = "/importOrExport")
public class ImportOrExportController {

    @Autowired
    private ImportOrExportService importOrExportService;

    @Autowired
    private ImportOrExportThin importOrExportThin;

    /*@ApiOperation(value = "导出Execl文件", notes = "导出Execl文件", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportExecl(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                  @ApiParam(value = "isModel", required = true) @RequestParam("isModel") Boolean isModel, HttpServletResponse response, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = importOrExportService.exportExecl(flag,isModel,response);
        return lsResponse;
    }*/

    @ApiOperation(value = "导出Execl模板文件", notes = "导出Execl模板文件", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/exportModel", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportExeclModel(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag, HttpServletResponse response, HttpServletRequest request) {
        //DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = importOrExportService.exportExeclModel(flag,response);
        return lsResponse;
    }

    @ApiOperation(value = "导出Execl文件(数据库内容)", notes = "导出Execl文件(数据库内容)", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/exportList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse exportExeclList(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag, HttpServletResponse response, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if (user == null){
            user = new User();
            user.setUsername("zgqExport");
            user.setCanteenIds("can001,can002");
        }
        LsResponse lsResponse = importOrExportThin.exportExeclList(user,flag,response);
        return lsResponse;
    }


    @ApiOperation(value = "导入Execl文件", notes = "导入Execl文件", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/improt", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse improtExecl(@ApiParam(value = "flag", required = true) @RequestParam("flag") Integer flag,
                                  @ApiParam(value = "canteenId", required = true) @RequestParam("canteenId") String canteenId,
                                  @ApiParam(value = "file", required = true) @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if(user == null){
            user = new User();
            user.setUsername("zhenguanqi");
        }
        //DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = importOrExportService.improtExecl(user,flag,canteenId,file);
        return lsResponse;
    }


    @ApiOperation(value = "导入Execl文件,艺校学生信息", notes = "导入Execl文件,艺校学生信息", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/importExcelYXStudent", method = RequestMethod.POST)
    @ResponseBody
    //@ApiParam(value = "file", required = true) @RequestParam(value = "file") MultipartFile file,
    public LsResponse importExcelYXStudent(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if(user == null){
            user = new User();
            user.setUsername("zhenguanqi");
        }
        DataSourceHandle.setDataSourceType("DSSS");//切换成DSSS数据库
        LsResponse lsResponse = importOrExportService.importExcelYXStudent(user);
        return lsResponse;
    }

    @ApiOperation(value = "导入Execl文件,艺校教师信息", notes = "导入Execl文件,艺校教师信息", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/importExcelYXTeacher", method = RequestMethod.POST)
    @ResponseBody
    //@ApiParam(value = "file", required = true) @RequestParam(value = "file") MultipartFile file,
    public LsResponse importExcelYXTeacher(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if(user == null){
            user = new User();
            user.setUsername("zhenguanqi");
        }
        DataSourceHandle.setDataSourceType("LSCT");//切换成DSSS数据库
        LsResponse lsResponse = importOrExportService.importExcelYXTeacher(user);
        return lsResponse;
    }

}
