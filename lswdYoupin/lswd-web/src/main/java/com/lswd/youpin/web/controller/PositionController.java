package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.PositionMapper;
import com.lswd.youpin.lsy.PositionService;
import com.lswd.youpin.model.lsy.Position;
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
@Api(value = "position", tags = "position", description = "岗位管理")
@Controller
@RequestMapping(value = "/position")
public class PositionController {

    @Autowired
    PositionService positionService;
    @Autowired
    PositionMapper positionMapper;
    @ApiOperation(value = "岗位管理", notes = "岗位管理", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPositionList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
        public LsResponse getPositionList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) throws UnsupportedEncodingException {

        return positionService.getPositionList(keyword,pageNum,pageSize);
    }

    @ApiOperation(value = "员工详情", notes = "员工详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPositionById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPositionById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        return positionService.getPositionById(id);
    }

    @ApiOperation(value = "新建员工", notes = "新建员工", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdatePosition", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdatePosition(@RequestBody Position position, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = positionService.addOrUpdatePosition(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除员工", notes = "删除员工", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deletePosition(@PathVariable Integer id, HttpServletRequest request) {
        return positionService.delPosition(id);
    }








}
