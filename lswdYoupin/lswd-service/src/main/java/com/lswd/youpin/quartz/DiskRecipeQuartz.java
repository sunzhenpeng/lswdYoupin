package com.lswd.youpin.quartz;

import com.lswd.youpin.common.date.DateUtils;
import com.lswd.youpin.service.RfidService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liruilong on 2017/11/30.
 */
public class DiskRecipeQuartz {
    @Autowired
    private RfidService rfidService;

    public void update() {
        int nowHour=DateUtils.getHour();
        int mealType=0;
        switch(nowHour){
            case 6:mealType=0;break;
            case 10:mealType=1;break;
            case 16:mealType=2;break;
            default:break;
        }
       // DataSourceHandle.setDataSourceType("LSCT");
        //rfidService.updateDiskRecipe(mealType,"LSCT100026");
    }

}
