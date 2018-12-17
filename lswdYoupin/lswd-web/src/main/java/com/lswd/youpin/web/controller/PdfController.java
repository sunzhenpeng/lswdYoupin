package com.lswd.youpin.web.controller;


import com.lswd.youpin.dao.lsy.PdfMapper;
import com.lswd.youpin.lsy.PdfService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Pdf;
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
@Api(value = "pdf", tags = "pdf", description = "pdf管理")
@Controller
@RequestMapping(value = "/pdf")
public class PdfController {
    @Autowired
    PdfService pdfService;
    @Autowired
    PdfMapper pdfMapper;
    @ApiOperation(value = "pdf列表", notes = "pdf列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPdfList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPdfList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                 @ApiParam(value = "resTypeId") @RequestParam(value = "resTypeId", required = false) Integer resTypeId,
                                 @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                 @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
       //User u = new User(22,"ceshi01","0a89fefcb2f5b429912e7b6a10776375",1,"LSYP100001","LSCT100022");
        return pdfService.getPdfList(u,keyword,resTypeId,pageNum,pageSize);
    }

   /* @ApiOperation(value = "pdf详情", notes = "pdf详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPdfById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPdfById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return pdfService.getPdfById(id);
    }
*/
    @ApiOperation(value = "pdf详情", notes = "pdf详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getPdfById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getPdfById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        return pdfService.getPdfById(id);
    }

    @ApiOperation(value = "新建pdf", notes = "新建pdf", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdatePdf", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdatePdf(@RequestBody Pdf pdf, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = pdfService.addOrUpdatePdf(pdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除pdf", notes = "删除pdf", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deletePdf(@PathVariable Integer id, HttpServletRequest request) {
        return pdfService.delPdf(id);
    }

/*
    @ApiOperation(value = "新建或者修改pdf", notes = "新建或者修改pdf", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addPdf(@RequestBody Pdf pdf, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return pdfService.addOrUpdatePdf(pdf,u);

    }*/






}
