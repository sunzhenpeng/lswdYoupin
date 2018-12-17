package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.ResTypeMapper;
import com.lswd.youpin.lsy.ResTypeService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.ResType;
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
 * Created by sunzhenpeng on 20180704.
 */
@Api(value = "resType", tags = "resType", description = "资源类型管理")
@Controller
@RequestMapping(value = "/resType")
public class ResTypeController {

    @Autowired
    ResTypeService resTypeService;
    @Autowired
    ResTypeMapper resTypeMapper;
    @ApiOperation(value = "资源类型列表", notes = "资源类型列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getResTypeList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getResTypeList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                     @ApiParam(value = "pageId") @RequestParam(value = "pageId", required = false) Integer pageId,
                                     @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "type") @RequestParam(value = "type", required = false) String type,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
       // User u = (User) request.getAttribute("user");
        User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return resTypeService.getResTypeList(u,keyword,type,pageId,machineId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "资源类型详情", notes = "资源类型详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getResTypeById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getResTypeById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return resTypeService.getResTypeById(id);
    }
*/
    @ApiOperation(value = "资源类型详情", notes = "资源类型详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getResTypeById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getResTypeById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        return resTypeService.getResTypeById(id);
    }

    @ApiOperation(value = "新建资源类型", notes = "新建资源类型", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateResType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateResType(@RequestBody ResType resType, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = resTypeService.addOrUpdateResType(resType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除资源类型", notes = "删除资源类型", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteResType(@PathVariable Integer id, HttpServletRequest request) {
        return resTypeService.delResType(id);
    }

/*
    @ApiOperation(value = "新建或者修改资源类型", notes = "新建或者修改资源类型", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addResType(@RequestBody ResType resType, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return resTypeService.addOrUpdateResType(resType,u);

    }*/






}
