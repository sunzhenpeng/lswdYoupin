package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.BannerMapper;
import com.lswd.youpin.lsy.BannerService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Banner;
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
 * Created by sunzhenpeng on 20180707.
 */
@Api(value = "banner", tags = "banner", description = "banner管理")
@Controller
@RequestMapping(value = "/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @Autowired
    BannerMapper bannerMapper;
    @ApiOperation(value = "banner列表", notes = "banner列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getBannerList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getBannerList(@ApiParam(value = "machineId") @RequestParam(value = "machineId", required = false) Integer machineId,
                                    @ApiParam(value = "pageId") @RequestParam(value = "pageId", required = false) Integer pageId,
                                    @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                    @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                    @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
       User u = (User) request.getAttribute("user");
         // User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return bannerService.getBannerList(u,keyword,pageId,machineId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "banner详情", notes = "banner详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getBannerById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getBannerById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return bannerService.getBannerById(id);
    }
*/
    @ApiOperation(value = "banner详情", notes = "banner详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getBannerById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getBannerById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return bannerService.getBannerById(id);
    }

    @ApiOperation(value = "新建banner", notes = "新建banner", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateBanner", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateBanner(@RequestBody Banner banner, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = bannerService.addOrUpdateBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除banner", notes = "删除banner", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteBanner(@PathVariable Integer id, HttpServletRequest request) {
        return bannerService.delBanner(id);
    }

/*
    @ApiOperation(value = "新建或者修改banner", notes = "新建或者修改banner", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addBanner(@RequestBody Banner banner, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return bannerService.addOrUpdateBanner(banner,u);

    }*/






}
