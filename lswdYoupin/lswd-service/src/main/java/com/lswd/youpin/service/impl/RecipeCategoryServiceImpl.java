package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.RecipeCategoryMapper;
import com.lswd.youpin.dao.lsyp.RecipeMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.model.lsyp.RecipeCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeCategoryService;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.weixin.core.Datas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/30.
 */
@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {
    private final Logger log = LoggerFactory.getLogger(RecipeCategoryServiceImpl.class);
    @Autowired
    private RecipeCategoryMapper recipeCategoryMapper;
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public LsResponse insertOrUpdateRecipeCategory(RecipeCategory recipeCategory, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipeCategory.getId() == null){//表示新增
                recipeCategory.setIsDelete(false);
                recipeCategory.setParentId(0);
                recipeCategory.setLevel((short)0);
                recipeCategory.setCreateUser(user.getUsername());
                recipeCategory.setCreateTime(Dates.now());
                recipeCategory.setUpdateUser(user.getUsername());
                recipeCategory.setUpdateTime(Dates.now());
                int insertflag = recipeCategoryMapper.insert(recipeCategory);
                if (insertflag > 0){
                    lsResponse.setMessage("分类添加成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("分类添加失败");
                }
            }else{//表示修改
                recipeCategory.setUpdateTime(Dates.now());
                recipeCategory.setUpdateUser(user.getUsername());
                int updateflag = recipeCategoryMapper.updateByPrimaryKey(recipeCategory);
                if (updateflag > 0){
                    lsResponse.setMessage("分类修改成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("分类修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜品分类新增或者修改时，出现异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteRecipeCategoryById(Integer id, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null){
                log.info(user.getUsername()+"deleteRecipeCategoryById() method is being executed.", "deleteRecipeCategoryById", JSON.toJSON(id));
//                首先需要根据分类的id判断是否还存在菜品，如果存在，提示不能删除
                List<Recipe> recipes = recipeMapper.getRecipeByCategoryId(id);
                if (recipes != null && recipes.size() > 0){
                    lsResponse.setMessage("该分类中还存在菜品，不能删除");
                    lsResponse.setAsFailure();
                    return lsResponse;
                }
                int delflag = recipeCategoryMapper.deleteByPrimaryKey(id);
                if (delflag > 0){
                    lsResponse.setMessage("删除成功");
                    log.info("删除成功");
                }else{
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("删除失败");
                    log.info("菜品分类删除失败");
                }
            }else{
                lsResponse.setAsFailure();
                lsResponse.setMessage("id为null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除菜品分类时，出现异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeCategoryList(String keyword,Integer secondflag, Integer parentId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        StringBuilder sb  = new StringBuilder();
        try {
            if(keyword!=null&&!"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
            }else{
                keyword = "";
            }
            if (secondflag == null){
                secondflag = 1;
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeCategoryMapper.getGoodCategoryCount(keyword,secondflag,parentId);
            List<RecipeCategory> recipeCategories = recipeCategoryMapper.getRecipeCategoryList(keyword,secondflag,parentId,offSet,pageSize);
            if (recipeCategories != null && recipeCategories.size() > 0 ){
                lsResponse.setData(recipeCategories);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("列表查询成功");
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage("列表查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeCategoryWeb(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(keyword!=null&&!"".equals(keyword)){
                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
            }else{
                keyword = "";
            }
            if(canteenId!=null&&!"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeCategoryMapper.getRecipeCategoryWebCount(keyword,canteenId);
            List<RecipeCategory> recipeCategories = recipeCategoryMapper.getRecipeCategoryWeb(keyword,canteenId,offSet,pageSize);
            if (recipeCategories != null && recipeCategories.size() > 0 ){
                lsResponse.setTotalCount(total);
                lsResponse.setData(recipeCategories);
                lsResponse.setMessage("列表查询成功");
            }else{
                lsResponse.setSuccess(false);
                if(keyword!=null&&!"".equals(keyword)){
                    lsResponse.setMessage("该分类不存在");
                }else {
                    lsResponse.setMessage("菜品列表查询失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.info("web端菜品分裂列表查询失败",e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeCategoryListAll(String canteenId,User user) {
        LsResponse lsResponse=new LsResponse();
        try {
            List<RecipeCategory> recipeCategories = recipeCategoryMapper.getRecipeCategoryAll(canteenId);
            if (recipeCategories != null && recipeCategories.size() > 0 ){
                lsResponse.setData(recipeCategories);
                lsResponse.setMessage("查询成功");
                log.info(user.getUsername() + "getRecipeCategoryListAll is being executed,查询成功", "getRecipeCategoryListAll", JSON.toJSON(user.getUsername()));
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage("查询失败,数据为0");
                log.info("菜品分类列表查询失败,没有查到数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("getRecipeCategoryListAll(),查询菜品分类时发生异常，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse checkOutName(String name, String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if(name!=null && !"".equals(name)){
                name = new String(name.getBytes("iso8859-1"),"utf-8");
            }else{
                name = "";
            }
            if(canteenId!=null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            RecipeCategory recipeCategory = recipeCategoryMapper.checkOutName(name,canteenId);
            if (recipeCategory != null){//如果有同名的，设置date为false
                lsResponse.setData(false);
            }else {//如果没有同名的，设置date为true
                lsResponse.setData(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("校验分类名是否重复",e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------H5页面需要全部分类-------------------------------------------------------------------*/
    @Override
    public LsResponse getRecipeCategoryListH5(String canteenId) {
        LsResponse lsResponse=new LsResponse();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (dataSource != null && ("").equals(dataSource)){
                log.info("查询商品分类进的是-----------------------------------------"+dataSource+"库");
            }
            List<RecipeCategory> recipeCategories = recipeCategoryMapper.getRecipeCategoryListH5(canteenId);
            if (recipeCategories != null && recipeCategories.size() > 0 ){
                log.info("H5菜品分类列表查询成功，共"+recipeCategories.size()+"条");
//                recipeCategories.get(0).setCheck(true);
                lsResponse.setData(recipeCategories);
                lsResponse.setMessage("查询成功");
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage("查询失败,没有查到数据");
                log.info("H5菜品分类列表列表查询失败,没有查到数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("H5查询菜品分类时发生异常，异常信息为："+e.toString());
        }
        return lsResponse;
    }
}
