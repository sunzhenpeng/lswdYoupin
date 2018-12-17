package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.RecipeCategoryThin;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.model.lsyp.RecipeCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.RecipeCategoryService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zhenguanqi on 2017/6/30.
 */
@Service
public class RecipeCategoryThinImpl implements RecipeCategoryThin {
    @Autowired
    private RecipeCategoryService recipeCategoryService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse getRecipeCategoryWeb(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            lsResponse = recipeCategoryService.getRecipeCategoryWeb(keyword,canteenId,pageNum,pageSize);
            if (lsResponse.getData() != null){
                List<RecipeCategory> recipeCategories = (List<RecipeCategory>)lsResponse.getData();
                if (recipeCategories.size() > 0 && recipeCategories != null){
                    if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                    }
                    for (RecipeCategory recipeCategory : recipeCategories){
                        Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(recipeCategory.getCanteenId()).getData();
                        if (canteen != null){
                            recipeCategory.setCanteenName(canteen.getCanteenName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
}
