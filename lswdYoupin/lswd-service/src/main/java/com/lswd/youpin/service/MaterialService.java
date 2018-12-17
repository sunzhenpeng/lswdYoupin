package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.model.lsyp.Unit;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by liuhao on 2017/6/20.
 */
public interface MaterialService {
    LsResponse addMaterial(Material material,User user);

    LsResponse deleteMaterial(Integer id,User user);

    LsResponse update(Material material,User user);

    LsResponse getMaterialList(User user, String keyword, Integer pageNum, Integer pageSize,String cantenId,Integer categoryId,String supplierId);

    LsResponse lookMaterial(Integer id);

    LsResponse categoryList(String canteenId,Integer pageNum,Integer pageSize,String keyword);

    LsResponse categoryAll(String canteenId,User user);

    List<Unit> getUnits();

    LsResponse addCategory(MaterialCategory category,User user);

    LsResponse categoryName(String name,String canteenId);

    LsResponse delCategory(Integer id);
}
