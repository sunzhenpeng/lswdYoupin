package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.GoodPlanThin;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodPlan;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.GoodPlanService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/8/1.
 */
@Service
public class GoodPlanThinImpl implements GoodPlanThin{
    @Autowired
    private GoodPlanService goodPlanService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse getGoodPlanListWebPageShow(User user, String canteenId, String dinnerTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodPlanService.getGoodPlanListWebPageShow(user,canteenId,dinnerTime,pageNum,pageSize);
            List<GoodPlan> planList = (List<GoodPlan>)lsResponse.getData();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            if (planList != null && planList.size() > 0){
                for (GoodPlan plan : planList){
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
    public LsResponse getGoodPlanDetails(String goodPlanId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodPlanService.getGoodPlanDetails(goodPlanId);
            GoodPlan goodPlan = (GoodPlan)lsResponse.getData();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            if (goodPlan != null){
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodPlan.getCanteenId()).getData();
                if (canteen != null){
                    goodPlan.setCanteenName(canteen.getCanteenName());
                }
                lsResponse.setData(goodPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
}
