package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.VideoMapper;
import com.lswd.youpin.lsy.VideoService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Video;
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
@Api(value = "video", tags = "video", description = "视频管理")
@Controller
@RequestMapping(value = "/video")
public class VideoController {

    @Autowired
    VideoService videoService;
    @Autowired
    VideoMapper videoMapper;
    @ApiOperation(value = "视频列表", notes = "视频列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getVideoList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getVideoList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                   @ApiParam(value = "pageId") @RequestParam(value = "pageId", required = false) Integer pageId,
                                   @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                   @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                   @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                   HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
         // User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return videoService.getVideoList(u,keyword,pageId,machineId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "视频详情", notes = "视频详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getVideoById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getVideoById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return videoService.getVideoById(id);
    }
*/
    @ApiOperation(value = "视频详情", notes = "视频详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getVideoById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getVideoById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                   HttpServletRequest request) throws UnsupportedEncodingException {
        return videoService.getVideoById(id);
    }

    @ApiOperation(value = "新建视频", notes = "新建视频", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateVideo", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateVideo(@RequestBody Video video, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = videoService.addOrUpdateVideo(video);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除视频", notes = "删除视频", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteVideo(@PathVariable Integer id, HttpServletRequest request) {
        return videoService.delVideo(id);
    }

/*
    @ApiOperation(value = "新建或者修改视频", notes = "新建或者修改视频", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addVideo(@RequestBody Video video, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return videoService.addOrUpdateVideo(video,u);

    }*/






}
