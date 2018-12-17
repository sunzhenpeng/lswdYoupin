package com.lswd.youpin.service.impl;

import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.NutritionMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Nutrition;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.NutritionService;
import com.lswd.youpin.utils.PoiExcelExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/2.
 */
@Service
public class NutritionServiceImpl implements NutritionService{
    private final Logger logger = LoggerFactory.getLogger(NutritionServiceImpl.class);

    @Autowired
    private NutritionMapper nutritionMapper;

    @Override
    public LsResponse getNutritionList(String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
//        Integer total = 0;
        Integer offSet = 0;
        try{
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (keyword != null && !("").equals(keyword)){
                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
            }else {
                keyword = "";
            }
        }catch (UnsupportedEncodingException e){
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            logger.error("查询营养成分列表失败，失败原因为："+ e.toString());
        }
        try {
            Integer total = nutritionMapper.getNutritionListCount(keyword);
            List<Nutrition> nutritions= nutritionMapper.getNutritionList(keyword,offSet,pageSize);
            if (nutritions.size() > 0 && nutritions != null){
                lsResponse.setData(nutritions);
                if (total != null){
                    lsResponse.setTotalCount(total);
                }
            }else {
                lsResponse.setAsFailure();
                if (keyword != null && !("").equals(keyword)){
                    lsResponse.setMessage("该营养成分不存在");
                }else {
                    lsResponse.setMessage("营养成分数据列表为空");
                }
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("查询营养成分列表失败，失败原因为："+ e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getNutritionListAll() {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            List<Nutrition> nutritions= nutritionMapper.getNutritionListAll();
            if (nutritions.size() > 0 && nutritions != null){
                lsResponse.setData(nutritions);
            }else {
                lsResponse.setAsFailure();
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("查询所有的----------营养成分列表失败，失败原因为："+ e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addNutrition(Nutrition nutrition, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            int insertFlag = nutritionMapper.insertSelective(nutrition);
            if (insertFlag > 0){
                lsResponse.setMessage("营养成分新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("营养成分新增失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("营养成分新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateNutrition(Nutrition nutrition, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            int updateFlag = nutritionMapper.updateByPrimaryKeySelective(nutrition);
            if (updateFlag > 0){
                lsResponse.setMessage("营养成分修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("营养成分修改失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("营养成分修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteNutrition(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null){
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = nutritionMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0){
                lsResponse.setMessage("营养成分删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("营养成分删除失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("营养成分删除失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getNutritionByName(String name) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
            if (name != null && !("").equals(name)){
                name = new String(name.getBytes("utf-8"),"utf-8");
            }else {
                name = "";
            }
        }catch (UnsupportedEncodingException e){
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            logger.error("根据name检索营养成分失败，失败原因为："+ e.toString());
        }
        try {
            List<Nutrition> nutritions= nutritionMapper.getNutritionByName(name);
            if (nutritions.size() > 0 && nutritions != null){
                lsResponse.setData(nutritions);
            }else {
                lsResponse.setAsFailure();
                if (name != null && !("").equals(name)){
                    lsResponse.setMessage("营养成分不存在");
                }else {
                    lsResponse.setMessage("营养成分数据列表为空");
                }
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("查询营养成分列表失败，失败原因为："+ e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse exportnNutritionList(User user, HttpServletResponse response) {
        LsResponse lsResponse = LsResponse.newInstance();
        String fileName = "营养成分导出表";
        PoiExcelExport pee = new PoiExcelExport(response,fileName,"sheet1");


//        String titleColumn[] = {"recipeId","recipeName","guidePrice","marketPrice","cookTypeName","categoryName","cookDetail","createUser","updateUser","createTimeString","updateTimeString"};
//        String titleName[] = {"菜品编号","菜品名称","指导价格","市场价格","烹饪类型","菜品分类名称","制作步骤","创建人","更新人","创建时间","更新时间"};
//        int titleSize[] = {13,18,10,10,12,16,40,15,15,18,18};
//        pee.wirteExcelList(flag,"菜谱资料详情导出预览表",titleColumn, titleName, titleSize, recipes);
        return lsResponse;
    }
}
