package com.lswd.youpin.Thin.Impl;


import com.lswd.youpin.Thin.GoodCategoryThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.model.lsyp.GoodOrder;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.GoodCategoryService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/9/13.
 */
@Service
public class GoodCategoryThinImpl implements GoodCategoryThin{

    @Autowired
    private GoodCategoryService goodCategoryService;
    @Autowired
    private CanteenService canteenService;


    @Override
    public LsResponse getGoodCategoryWeb(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            lsResponse = goodCategoryService.getGoodCategoryWeb(keyword,canteenId,pageNum,pageSize);
            if(lsResponse.getData() != null){
                List<GoodCategory> goodCategories = (List<GoodCategory>)lsResponse.getData();
                if (goodCategories.size() > 0 && goodCategories != null){
                    if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                    }
                    for (GoodCategory goodCategory : goodCategories){
                        Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodCategory.getCanteenId()).getData();
                        if (canteen != null){
                            goodCategory.setCanteenName(canteen.getCanteenName());
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
