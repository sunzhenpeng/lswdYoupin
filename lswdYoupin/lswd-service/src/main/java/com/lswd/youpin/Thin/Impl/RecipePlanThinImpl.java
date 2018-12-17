package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.RecipePlanThin;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipePlan;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.RecipePlanService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/7/17.
 */
@Service
public class RecipePlanThinImpl implements RecipePlanThin{
    @Autowired
    private RecipePlanService recipePlanService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse getRecipePlanListWebPageShow(User user, String canteenId, String dinnerTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = null;
        try {
            lsResponse = recipePlanService.getRecipePlanListWebPageShow(user,canteenId,dinnerTime,pageNum,pageSize);
            List<RecipePlan> planList = (List<RecipePlan>)lsResponse.getData();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            if (planList != null && planList.size() > 0){
                for (RecipePlan plan : planList){
                    Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(plan.getCanteenId()).getData();
                    if (canteen != null){
                        plan.setCanteenName(canteen.getCanteenName());
                    }
                }
                lsResponse.setData(planList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipePlanDetails(String recipePlanId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = recipePlanService.getRecipePlanDetails(recipePlanId);
            RecipePlan recipePlan = (RecipePlan)lsResponse.getData();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            if (recipePlan != null){
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(recipePlan.getCanteenId()).getData();
                if (canteen != null){
                    recipePlan.setCanteenName(canteen.getCanteenName());
                }
                lsResponse.setData(recipePlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
}
