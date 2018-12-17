package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MaterialMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.model.lsyp.Unit;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MaterialService;
import com.lswd.youpin.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2017/6/20.
 */
@Service
public class MaterialServiceImpl implements MaterialService{
    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);
    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public LsResponse addMaterial(Material material,User user) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}","addMaterial", JSON.toJSON(user.getUsername() + "准备添加新的食材信息")
                + material.getMaterialName());
        try {
            if (material != null) {
                Integer id=materialMapper.getLastId();
                if(id==null)
                {
                    id=0;
                }
                String  materialId="m"+String.valueOf(100001+id);
                material.setMaterialId(materialId);
                material.setCreateTime(Dates.now());
                material.setUpdateTime(Dates.now());
                material.setUpdateUser(user.getUsername());
                material.setDelete(false);
                if (material.getPriceAll()==null||material.getPriceAll() == 0) {
                    material.setPriceAll(new BigDecimal(material.getPrice()).multiply(new BigDecimal(material.getNumber())).doubleValue());
                }
                Integer b = materialMapper.insertMaterial(material);
                if (b!=null&&b > 0) {
                    materialMapper.insertMaterialSupplier(material.getMaterialId(),material.getSupplier().getSupplierId(),material.getPrice());
                    lsResponse.setMessage(CodeMessage.MATERIAL_ADD_SUCCESS.name());
                    log.info("{} is being executed. User = {}","addMaterial", JSON.toJSON(user.getUsername() + "添加了新的食材信息")
                            + material.getMaterialName());
                } else {
                 lsResponse.checkSuccess(false,CodeMessage.MATERIAL_ADD_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false,CodeMessage.MATERIAL_NO_ERR.name());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("addMaterial"+e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse deleteMaterial(Integer id,User user) {

        LsResponse lsResponse=new LsResponse();
        Material material=new Material();
        material.setUpdateUser(user.getUsername());
        material.setUpdateTime(Dates.now());
        material.setId(id);
        material.setDelete(true);
        try {
            Integer b=materialMapper.deleteById(material);
            if(b!=null&&b>0)
            {
                lsResponse.setMessage(CodeMessage.MATERIAL_DELETE_SUCCESS.name());
            }else{
              lsResponse.checkSuccess(false,CodeMessage.MATERIAL_DELETE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse update(Material material,User user) {
        LsResponse lsResponse=new LsResponse();
        log.info("{} is being executed. User = {}","addMaterial", JSON.toJSON(user.getUsername() + "准备修改食材信息")
                + material.getMaterialName());
        try {
            if(material!=null)
            {
                material.setUpdateTime(Dates.now());
                material.setUpdateUser(user.getUsername());
                Integer b=materialMapper.updateById(material);
                if(b!=null&&b>0)
                {
                    materialMapper.updateMaterialSupplier(material.getMaterialId(),material.getSupplier().getSupplierId(),material.getPrice());
                    lsResponse.setMessage(CodeMessage.MATERIAL_UPDATE_SUCCESS.name());
                    log.info("{} is being executed. User = {}","addMaterial", JSON.toJSON(user.getUsername() + "修改了食材信息")+material.getMaterialId());
                }else{
                  lsResponse.checkSuccess(false,CodeMessage.MATERIAL_UPDATE_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false,CodeMessage.MATERIAL_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("updateMaterial"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMaterialList(User user, String keyword, Integer pageNum, Integer pageSize,String cantenId,Integer categoryId,String supplierId) {
            LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (keyword != null&&!"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            List<String>canteenIds=new ArrayList<>();
            if(cantenId!=null&&!"".equals(cantenId))
            {
                canteenIds.add(cantenId);
            }else{
                String canteenId=user.getCanteenIds();
                String [] canIds=canteenId.split(",");
                for(String can:canIds){
                   canteenIds.add(can);
                }
            }
            if(categoryId==null)
            {
                categoryId=0;
            }
            if(supplierId==null)
            {
               supplierId="";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            Integer total = materialMapper.getMaterialCount(keyword,canteenIds,categoryId,supplierId);
            List<Material> materialList = materialMapper.selectMaterials(keyword, offSet, pageSize,canteenIds,categoryId,supplierId);
            if (materialList != null && materialList.size() > 0) {
                lsResponse.setData(materialList);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.MATERIAL_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("materialList"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookMaterial(Integer id) {
        LsResponse lsResponse=new LsResponse();
        try {
            Material material=materialMapper.selectById(id);
            if(material!=null)
            {
                lsResponse.setData(material);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.MATERIAL_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("lookMaterial"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse categoryList(String canteenId,Integer pageNum,Integer pageSize,String keyword) {

        LsResponse lsResponse=new LsResponse();
        try {
            int offSet = 0;
            if (keyword != null&&!"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            Integer total=materialMapper.getCategoryCount(canteenId,keyword);
            List<MaterialCategory>categoryList=materialMapper.selectCategoryList(canteenId,pageSize,offSet,keyword);
            if(categoryList!=null&&categoryList.size()>0)
            {
                lsResponse.setTotalCount(total);
                lsResponse.setData(TreeUtil.getMaterialToTree(categoryList));
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage("分类信息为空");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("materialCategoryList"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse categoryAll(String canteenId,User user) {
        LsResponse lsResponse=new LsResponse();
        try {
            canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            log.info(user.getUsername() + "categoryAll is being executed.","categoryAll", JSON.toJSON(user.getUsername() + "正在查看材料分类信息"));
            List<MaterialCategory>categoryList=materialMapper.categoryAll(canteenId);
            if (categoryList != null && categoryList.size() > 0){
                lsResponse.setMessage("分类查询成功");
                lsResponse.setData(categoryList);
                log.info("web端查看材料分类成功");
            }else {
                lsResponse.setMessage("分类查询失败");
                log.info("web端分类查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("categoryAll"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public List<Unit> getUnits() {
        return materialMapper.getUnits();
    }


    @Override
    public LsResponse addCategory(MaterialCategory category,User user) {
        LsResponse lsResponse=new LsResponse();
        try {
            if(category!=null)
            {
                category.setCreateTime(Dates.now());
                category.setCreateUser(user.getUsername());
                category.setUpdateTime(Dates.now());
                category.setUpdateUser(user.getUsername());
                category.setParentId(0);
                category.setLevel((short) 0);
                Integer b=materialMapper.addCategory(category);
                if(b!=null&&b>0)
                {
                    lsResponse.setMessage("添加成功");
                }else{
                    lsResponse.setSuccess(false);
                    lsResponse.setMessage("添加失败");
                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("addCategory{}"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse categoryName(String name,String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (name != null && canteenId != null&&!"".equals(name)&&!"".equals(canteenId)) {
                name = new String(name.getBytes("iso8859-1"), "utf-8");
                MaterialCategory materialCategory = materialMapper.selectCategoryByName(name, canteenId);
                if (materialCategory != null) {
                    lsResponse.checkSuccess(false, CodeMessage.MATERIAL_YES_CATEGORY.name());
                }
            }else{
                lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
            }
        } catch (UnsupportedEncodingException e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("categoryName{}" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse delCategory(Integer id) {
        LsResponse lsResponse=new LsResponse();
        try {
            Integer b=materialMapper.delCategoryById(id);
            if(b!=null&&b>0)
            {
                materialMapper.deleteMaterialByCategoryId(id);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.MATERIAL_DELETE_CATEGORY.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("delCategory{}" + e.toString());
        }
        return lsResponse;
    }


}
