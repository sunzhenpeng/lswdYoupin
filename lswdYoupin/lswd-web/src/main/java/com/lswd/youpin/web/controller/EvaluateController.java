package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Evaluate;
import com.lswd.youpin.model.lsyp.EvaluateAdd;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/7/1.
 */
@Api(value = "evaluate", tags = "evaluate", description = "用户评价")
@Controller
@RequestMapping(value = "/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;


    @ApiOperation(value = "添加评论", notes = "添加评论", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addEvaluate(@RequestBody EvaluateAdd evaluateAdd, HttpServletRequest request)
    {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse=evaluateService.addEvaluate(evaluateAdd,associator);
        return  lsResponse;
    }

    @ApiOperation(value = "获取评论列表", notes = "获取评论列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/get/evaluateList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getEvaluateList(@ApiParam(value = "evaluateId", required = true) @RequestParam(value = "evaluateId") String evaluateId,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize)
    {
        LsResponse lsResponse=evaluateService.getEvaluateList(evaluateId,pageNum,pageSize);
        return  lsResponse;
    }

    @ApiOperation(value = "追加评论", notes = "追加评论", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/additional", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse additional(@RequestBody Evaluate evaluate)
    {
        LsResponse lsResponse=evaluateService.additional(evaluate);
        return  lsResponse;
    }



}
