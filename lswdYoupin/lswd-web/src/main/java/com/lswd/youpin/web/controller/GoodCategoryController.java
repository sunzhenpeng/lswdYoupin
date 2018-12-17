package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.GoodCategoryThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zhenguanqi on 2017/6/22.
 */
@Api(value = "goodCategory", tags = "goodCategory", description = "商品分类管理")
@Controller
@RequestMapping(value = "/goodCategory")
public class GoodCategoryController {
    @Autowired
    private GoodCategoryService goodCategoryService;
    @Autowired
    private GoodCategoryThin goodCategoryThin;


    @ApiOperation(value = "新建或者修改商品分类", notes = "新建或者修改商品分类", nickname = "zgq", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateGoodCategory", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateGoodCategory(@RequestBody GoodCategory goodCategory, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return goodCategoryService.addOrUpdateGoodCategory(goodCategory,user);
    }

    @ApiOperation(value = "删除商品分类", notes = "删除商品分类", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/deleteGoodCategoryById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteGoodCategoryById(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return goodCategoryService.deleteGoodCategoryById(id,user);
    }

    @ApiOperation(value = "商品一级分类列表", notes = "商品一级分类列表", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/getFirstGoodCategory", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getFirstGoodCategory(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                           @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                           @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        return goodCategoryService.getFirstGoodCategory(name, pageNum, pageSize);
    }

    @ApiOperation(value = "商品二级分类列表", notes = "商品二级分类列表", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/getSecondGoodCategory", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getSecondGoodCategory(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                            @ApiParam(value = "id", required = true) @RequestParam("id") Integer id) {
        return goodCategoryService.getSecondGoodCategory(name, id);
    }

    @ApiOperation(value = "商品分类全部列表(商品列表页面，只有一级分类)", notes = "商品分类全部列表(商品列表页面，只有一级分类)", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/getGoodCategoryListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodCategoryListAll(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId, HttpServletRequest request){
        User user = (User)request.getAttribute("user");
        return goodCategoryService.getGoodCategoryListAll(canteenId,user);
    }

    @ApiOperation(value = "商品分类WEB列表展示", notes = "商品分类WEB列表展示", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/getGoodCategoryWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodCategoryWeb(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                         @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                         @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        return goodCategoryThin.getGoodCategoryWeb(keyword,canteenId, pageNum, pageSize);
    }

    @ApiOperation(value = "校验分类名是否重复", notes = "校验分类名是否重复", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/checkOutName", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse checkOutName(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                   @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId) {
        return goodCategoryService.checkOutName(name,canteenId);
    }

/*------------------------------------------H5页面需要全部分类-------------------------------------------------------------------*/
    @ApiOperation(value = "商品分类全部列表", notes = "商品分类全部列表", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/getGoodCategoryListH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodCategoryListH5(HttpServletRequest request){
        String canteenId = request.getHeader(ConstantsCode.CANTEEN_ID);
        return goodCategoryService.getGoodCategoryListH5(canteenId);
    }

}
