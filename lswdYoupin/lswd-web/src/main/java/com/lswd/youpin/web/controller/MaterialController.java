package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.MaterialThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/20.
 */
@Api(value = "material", tags = "material", description = "材料管理")
@Controller
@RequestMapping(value = "/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialThin materialThin;

    @ApiOperation(value = "新建材料", notes = "新建材料", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Material material, HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse = materialService.addMaterial(material,user);
        return  lsResponse;
    }

    @ApiOperation(value = "删除材料", notes = "删除材料", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id")Integer id, HttpServletRequest request)
    {
        User user=(User)request.getAttribute("user");
        LsResponse lsResponse = materialService.deleteMaterial(id,user);
        return lsResponse;
    }

    @ApiOperation(value = "修改材料信息", notes = "修改材料信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody Material material, HttpServletRequest request)
    {
        User user=(User) request.getAttribute("user");
        LsResponse lsResponse = materialService.update(material,user);
        return  lsResponse;
    }

    @ApiOperation(value = "获取材料列表", notes = "获取材料列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getMaterialList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getMaterialList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                      @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                      @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                      @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                      @ApiParam(value = "categoryId") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                      @ApiParam(value = "supplierId") @RequestParam(value = "supplierId", required = false) String supplierId,
                                      HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = materialService.getMaterialList(user,keyword,pageNum,pageSize,canteenId,categoryId,supplierId);
        return lsResponse;
    }

    @ApiOperation(value = "查看材料详情", notes = "查看材料详情", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/look/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse look(@PathVariable(value = "id")Integer id)
    {
        LsResponse lsResponse = materialThin.lookMaterial(id);
        return lsResponse;
    }

    @ApiOperation(value = "材料分类列表", notes = "材料分类列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/categoryList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse categoryList(@ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                   @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                   @ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                   @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword)
    {
        LsResponse lsResponse = materialThin.categoryList(canteenId,pageNum,pageSize,keyword);
        return lsResponse;
    }

    @ApiOperation(value = "新增材料分类", notes = "新增材料分类", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addCategory(@RequestBody MaterialCategory category, HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = materialService.addCategory(category,user);
        return lsResponse;
    }
    @ApiOperation(value = "删除材料分类", notes = "删除材料分类", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteCategory/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteCategory(@PathVariable(value = "id")Integer id, HttpServletRequest request)
    {
        System.out.println(id);
        LsResponse lsResponse = materialService.delCategory(id);
        return lsResponse;
    }
    @ApiOperation(value = "按材料分类名称查找", notes = "按材料分类名称查找", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/categoryName", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse categoryName(@ApiParam(value = "canteenId",required = true) @RequestParam(value = "canteenId") String canteenId,
                                   @ApiParam(value = "name",required = true) @RequestParam(value = "name") String name)
    {
        LsResponse lsResponse = materialService.categoryName(name,canteenId);
        return lsResponse;
    }


    /*-----------------------------------------菜品新增或者商品新增需要用到的材料分类    start-------------------------------------------------------------------------*/
    @ApiOperation(value = "查询所有的材料分类", notes = "查询所有的材料分类", nickname = "zhenguanqi", httpMethod = "GET")
    @RequestMapping(value = "/categoryAll/{canteenId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse categoryAll(@PathVariable(value = "canteenId")String canteenId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        LsResponse lsResponse = materialService.categoryAll(canteenId,user);
        return lsResponse;
    }
     /*-----------------------------------------菜品新增或者商品新增需要用到的材料分类    end-------------------------------------------------------------------------*/
}
