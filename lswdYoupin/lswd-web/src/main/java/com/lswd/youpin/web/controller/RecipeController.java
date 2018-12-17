package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Ingredient;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/6/19.
 */
@Api(value = "recipe", tags = "recipe", description = "菜谱管理")
@Controller
@RequestMapping(value = "/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "菜谱新增", notes = "菜谱新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Recipe recipe, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.insertRecipe(user,recipe);
        return lsResponse;
    }

    @ApiOperation(value = "得到菜品详情", notes = "得到菜品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeDetails/{recipeId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getGoodDetails(@PathVariable(value = "recipeId")String recipeId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.getRecipeDetails(recipeId,user);//数据库的切换
        return lsResponse;
    }

    @ApiOperation(value = "添加菜品详情", notes = "添加菜品详情", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/addRecipeDetails", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addRecipeDetails(@RequestBody Recipe recipe, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.addRecipeDetails(recipe,user);//数据库的切换
        return lsResponse;
    }

    @ApiOperation(value = "删除菜品详情中的图片", notes = "删除菜品详情中的图片", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delRecipeDetailsImg", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delRecipeDetailsImg(@RequestParam(value = "recipeId") String recipeId, @RequestParam(value = "imageurl") String imageurl, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.delRecipeDetailsImg(recipeId,imageurl,user);
        return lsResponse;
    }

    @ApiOperation(value = "删除菜谱", notes = "删除菜谱", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id")Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.deleteRecipe(user,id);
        return lsResponse;
    }


    @ApiOperation(value = "修改菜谱", notes = "修改菜谱", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody Recipe recipe, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.updateRecipe(user,recipe);
        return lsResponse;
    }


    @ApiOperation(value = "菜谱列表", notes = "菜谱列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                    @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                    @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                    @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                    @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.getRecipeList(user,keyword,canteenId,categoryId,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "根据餐厅获取所有的菜品", notes = "根据餐厅获取所有的菜品", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeListAll", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeListAll(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = true) String canteenId) {
        LsResponse lsResponse = recipeService.getRecipeListAll(canteenId);
        return lsResponse;
    }

    @ApiOperation(value = "根据餐厅获取所有的菜品,带分页", notes = "根据餐厅获取所有的菜品,带分页", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeListAllPage", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeListAllPage(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                           @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = true) String canteenId,
                                           @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                           @ApiParam(value = "recipeId") @RequestParam(value = "recipeId", required = true) String recipeId,
                                           @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                           @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        LsResponse lsResponse = recipeService.getRecipeListAllPage(keyword,canteenId,categoryId,recipeId,pageNum,pageSize);
        return lsResponse;
    }


    @ApiOperation(value = "菜谱详情", notes = "菜谱详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeDetails(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.getRecipeInfo(user,id);
        return lsResponse;
    }


    @ApiOperation(value = "菜品计划新增时需要的数据", notes = "菜品计划新增时需要的数据", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeMINI", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeMINI(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                    @ApiParam(value = "recipePlanId") @RequestParam(value = "recipePlanId", required = false) String recipePlanId,
                                    @ApiParam(value = "recipeType") @RequestParam(value = "recipeType", required = false) Integer recipeType,
                                    @ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                    @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                    @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                    @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize) {
        //DataSourceHandle.setDataSourceType("LSYP");//切换成LSYP数据库
        LsResponse lsResponse = recipeService.getRecipeMINI(keyword,recipePlanId,recipeType,categoryId,canteenId,pageNum,pageSize);
        return lsResponse;
    }


    @ApiOperation(value = "UnitAndCookType", notes = "UnitAndCookType", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getUnitAndCookType", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getUnitAndCookType() {
        LsResponse lsResponse = recipeService.getUnitAndCookType();
        return lsResponse;
    }


    @ApiOperation(value = "获取系统编号(改接口未用到)", notes = "获取系统编号", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getAccessNumber", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getAccessNumber() {
        LsResponse lsResponse = recipeService.getAccessNumber();
        return lsResponse;
    }



    /*------------------------------------------------------------------H5用到的方法----------------------------------------------------------------------------------------*/

    @ApiOperation(value = "菜品详情H5", notes = "菜品详情H5", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeInfoByRecipeIdH5", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeInfoByRecipeIdH5(@ApiParam(value = "recipeId", required = true) @RequestParam(value = "recipeId") String recipeId,
                                                @ApiParam(value = "canteenId", required = true) @RequestParam(value = "canteenId") String canteenId,
                                                @ApiParam(value = "dinnerTime", required = true) @RequestParam(value = "dinnerTime") String dinnerTime,
                                                @ApiParam(value = "eatType", required = true) @RequestParam(value = "eatType") Integer eatType,
                                                @ApiParam(value = "recipePlanId", required = true) @RequestParam(value = "recipePlanId") String recipePlanId,
                                                HttpServletRequest request) {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = recipeService.getRecipeInfoByRecipeIdH5(associator,recipeId,canteenId,dinnerTime,eatType,recipePlanId);
        return lsResponse;
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    @ApiOperation(value = "菜品详情", notes = "菜品详情", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeInfoByRecipeIdWx", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeInfoByRecipeIdWx(@ApiParam(value = "recipeId", required = true) @RequestParam(value = "recipeId") String recipeId,
                                                HttpServletRequest request) {
        LsResponse lsResponse = recipeService.getRecipeInfoByRecipeIdWx(recipeId);
        return lsResponse;
    }




    /*-------------------------------------------------------------菜品营养成分管理部分-----------------------------------------------------*/
    @ApiOperation(value = "根据菜品编号，获取营养成分", notes = "根据菜品编号，获取营养成分", nickname = "zhenguanqi", httpMethod = "GET")
        @RequestMapping(value = "/getNutritionByRecipeId", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getNutritionByRecipeId(@ApiParam(value = "recipeId") @RequestParam(value = "recipeId", required = true) String recipeId) {
        LsResponse lsResponse = recipeService.getNutritionByRecipeId(recipeId);
        return lsResponse;
    }

    @ApiOperation(value = "菜品营养成分新增", notes = "菜品营养成分新增", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/recipeAddNutrition", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse recipeAddNutrition(@RequestBody Ingredient ingredient, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.recipeAddNutrition(user,ingredient);
        return lsResponse;
    }

    @ApiOperation(value = "删除菜品营养成分", notes = "删除菜品营养成分", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/recipeDeleteNutrition", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse recipeDeleteNutrition(@RequestParam(value = "ingredientid", required = true) String ingredientid, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.recipeDeleteNutrition(user,ingredientid);
        return lsResponse;
    }

    @ApiOperation(value = "获取菜品营养成分列表", notes = "获取菜品营养成分列表", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeNutritionList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getRecipeNutritionList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                             @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                             @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                             @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                             @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = recipeService.getRecipeNutritionList(user,keyword,canteenId,categoryId,pageNum,pageSize);
        return lsResponse;
    }

}
