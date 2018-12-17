package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.ImgMapper;
import com.lswd.youpin.lsy.ImgService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Img;
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
 * Created by sunzhenpeng on 20180703.
 */
@Api(value = "img", tags = "img", description = "图片管理")
@Controller
@RequestMapping(value = "/img")
public class ImgController {

    @Autowired
    ImgService imgService;
    @Autowired
    ImgMapper imgMapper;
    @ApiOperation(value = "图片列表", notes = "图片列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getImgList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
        public LsResponse getImgList(@ApiParam(value = "imgTypeId") @RequestParam(value = "resTypeId", required = false) Integer resTypeId,
                                     @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
          //User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return imgService.getImgList(u,keyword,resTypeId,updateTime,pageNum,pageSize);
    }

   /* @ApiOperation(value = "图片详情", notes = "图片详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getImgById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getImgById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return imgService.getImgById(id);
    }
*/
    @ApiOperation(value = "图片详情", notes = "图片详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getImgById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getImgById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        return imgService.getImgById(id);
    }

    @ApiOperation(value = "新建图片", notes = "新建图片", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateImg", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateImg(@RequestBody Img img, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = imgService.addOrUpdateImg(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除图片", notes = "删除图片", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteImg(@PathVariable Integer id, HttpServletRequest request) {
        return imgService.delImg(id);
    }

/*
    @ApiOperation(value = "新建或者修改图片", notes = "新建或者修改图片", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addImg(@RequestBody Img img, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return imgService.addOrUpdateImg(img,u);

    }*/






}
