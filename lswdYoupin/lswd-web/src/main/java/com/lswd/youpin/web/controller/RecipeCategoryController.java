package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.RecipeCategoryThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipeCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/6/30.
 */


@Api(value = "recipeCategory", tags = "recipeCategory", description = "菜品分类管理")
@Controller
@RequestMapping(value = "/recipeCategory")
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryService recipeCategoryService;
    @Autowired
    private RecipeCategoryThin recipeCategoryThin;

    @ApiOperation(value = "菜品分类新增或者修改", notes = "菜品分类新增或者修改", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdate(@RequestBody RecipeCategory recipeCategory, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeCategoryService.insertOrUpdateRecipeCategory(recipeCategory,user);
        return lsResponse;
    }

    @ApiOperation(value = "删除菜品分类", notes = "删除菜品分类", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/deleteRecipeCategoryById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse deleteRecipeCategoryById(@PathVariable(value = "id")Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeCategoryService.deleteRecipeCategoryById(id,user);
        return lsResponse;
    }


    @ApiOperation(value = "菜谱分类列表", notes = "菜谱分类列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeCategoryList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeCategoryList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                            @ApiParam(value = "secondflag") @RequestParam(value = "secondflag", required = false) Integer secondflag,
                                            @ApiParam(value = "parentId") @RequestParam(value = "parentId", required = false) Integer parentId,
                                            @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                            @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        LsResponse lsResponse = recipeCategoryService.getRecipeCategoryList(keyword,secondflag,parentId,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "菜谱分类列表(web端列表，后来加)", notes = "菜谱分类列表(web端列表，后来加)", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeCategoryWeb", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeCategoryWeb(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                           @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                           @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                           @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        LsResponse lsResponse = recipeCategoryThin.getRecipeCategoryWeb(keyword,canteenId,pageNum,pageSize);
        return lsResponse;
    }


    @ApiOperation(value = "菜谱分类列表(菜品列表展示，只有一级分类)", notes = "菜谱分类列表(菜品列表展示，只有一级分类)", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeCategoryListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeCategoryListAll(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        LsResponse lsResponse = recipeCategoryService.getRecipeCategoryListAll(canteenId,user);
        return lsResponse;
    }

    @ApiOperation(value = "校验分类名是否重复", notes = "校验分类名是否重复", nickname = "zgq", httpMethod = "GET")
    @RequestMapping(value = "/checkOutName", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse checkOutName(@ApiParam(value = "name") @RequestParam(value = "name", required = false) String name,
                                   @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId) {
        return recipeCategoryService.checkOutName(name,canteenId);
    }


/*---------------------------------------------------------------H5页面请求需要的接口----------------------------------------------------------------*/
    @ApiOperation(value = "菜谱分类列表(全部)", notes = "菜谱分类列表(全部)", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeCategoryListH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeCategoryListH5(HttpServletRequest request) {
        String canteenId = request.getHeader(ConstantsCode.CANTEEN_ID);
        LsResponse lsResponse = recipeCategoryService.getRecipeCategoryListH5(canteenId);
        return lsResponse;
    }
}