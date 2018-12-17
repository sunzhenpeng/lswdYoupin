package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecordInfoService;
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
@Api(value = "RecordInfo", tags = "RecordInfo", description = "消费机消费记录")
@Controller
@RequestMapping(value = "/recordInfo")
public class RecordInfoController {
    @Autowired
    private RecordInfoService recordInfoService;

    @ApiOperation(value = "消费机消费记录,所有的消费记录", notes = "消费机消费记录", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse get() {
        DataSourceHandle.setDataSourceType("DSSS");
        LsResponse lsResponse = new LsResponse();
        lsResponse.setData(recordInfoService.getAll());
        return lsResponse;
    }
}
