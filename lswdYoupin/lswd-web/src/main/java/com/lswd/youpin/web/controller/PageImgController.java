package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.PageImgMapper;
import com.lswd.youpin.lsy.PageImgService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.PageImg;
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
@Api(value = "pageImg", tags = "pageImg", description = "页面图片管理")
@Controller
@RequestMapping(value = "/pageImg")
public class PageImgController {

    @Autowired
    PageImgService pageImgService;
    @Autowired
    PageImgMapper pageImgMapper;
    @ApiOperation(value = "页面图片列表", notes = "页面图片列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageImgList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageImgList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                     @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "pageId") @RequestParam(value = "pageId", required = false) Integer pageId,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
         // User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return pageImgService.getPageImgList(u,keyword,pageId,machineId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "页面图片详情", notes = "页面图片详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageImgById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageImgById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return pageImgService.getPageImgById(id);
    }
*/
    @ApiOperation(value = "页面图片详情", notes = "页面图片详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPageImgById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPageImgById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        return pageImgService.getPageImgById(id);
    }

    @ApiOperation(value = "新建页面图片", notes = "新建页面图片", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdatePageImg", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdatePageImg(@RequestBody PageImg pageImg, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = pageImgService.addOrUpdatePageImg(pageImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除页面图片", notes = "删除页面图片", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deletePageImg(@PathVariable Integer id, HttpServletRequest request) {
        return pageImgService.delPageImg(id);
    }

/*
    @ApiOperation(value = "新建或者修改页面图片", notes = "新建或者修改页面图片", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addPageImg(@RequestBody PageImg pageImg, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return pageImgService.addOrUpdatePageImg(pageImg,u);

    }*/






}
