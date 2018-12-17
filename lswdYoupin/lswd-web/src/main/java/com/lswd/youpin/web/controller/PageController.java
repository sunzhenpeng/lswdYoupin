package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.PageMapper;
import com.lswd.youpin.lsy.PageService;
import com.lswd.youpin.model.lsy.Page;
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
 * Created by sunzhenpeng on 20180716.
 */
@Api(value = "page", tags = "page", description = "页面管理")
@Controller
@RequestMapping(value = "/page")
public class PageController {

    @Autowired
    PageService pageService;
    @Autowired
    PageMapper pageMapper;
    @ApiOperation(value = "页面列表", notes = "页面列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                  @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                  @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                  @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                  HttpServletRequest request) throws UnsupportedEncodingException {

        return pageService.getPageList(keyword,machineId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "页面详情", notes = "页面详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return pageService.getPageById(id);
    }
*/
    @ApiOperation(value = "页面详情", notes = "页面详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                  HttpServletRequest request) throws UnsupportedEncodingException {
        return pageService.getPageById(id);
    }

    @ApiOperation(value = "新建页面", notes = "新建页面", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdatePage", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdatePage(@RequestBody Page page, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = pageService.addOrUpdatePage(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除页面", notes = "删除页面", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deletePage(@PathVariable Integer id, HttpServletRequest request) {
        return pageService.delPage(id);
    }

/*
    @ApiOperation(value = "新建或者修改页面", notes = "新建或者修改页面", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addPage(@RequestBody Page page, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return pageService.addOrUpdatePage(page,u);

    }*/






}
