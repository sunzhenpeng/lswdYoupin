package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.MaterialThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.MaterialService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2017/7/14.
 */
@Component
public class MaterialThinImpl implements MaterialThin {
    private final Logger log = LoggerFactory.getLogger(MaterialThinImpl.class);
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse lookMaterial(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            Material material = (Material) materialService.lookMaterial(id).getData();
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(material.getCanteenId()).getData();
            material.setCanteenName(canteen.getCanteenName());
            lsResponse.setData(material);
        } catch (Exception e) {
            log.error("lookMaterial{}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse categoryList(String canteenId, Integer pageNum, Integer pageSize, String keyword) {
        LsResponse lsResponse = new LsResponse();
        List<MaterialCategory> materialCategories = new ArrayList<>();
        try {
            lsResponse = materialService.categoryList(canteenId, pageNum, pageSize, keyword);
            if (lsResponse.getData() != null) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
                List<MaterialCategory> materialCategoryList = (List<MaterialCategory>) lsResponse.getData();
                for (MaterialCategory category : materialCategoryList) {
                    category.setCanteenName(canteen.getCanteenName());
                    if (category.getCreateUser() == null) {
                        category.setCreateUser("");
                    }
                    if (category.getUpdateUser() == null) {
                        category.setUpdateUser("");
                    }
                    if (category.getCreateTime() == null) {
                        category.setCreateTime(Dates.now());
                    }
                    if (category.getUpdateTime() == null) {
                        category.setUpdateTime(Dates.now());
                    }
                    if (category.getDescription() == null) {
                        category.setDescription("");
                    }
                    materialCategories.add(category);
                }
            }
            lsResponse.setData(materialCategories);
        } catch (Exception e) {
            log.error("categoryList{}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}
