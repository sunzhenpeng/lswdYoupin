package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyNutritionService {

    LsyResponse getNutritionMainInfo(String url, String machineNo);
    LsyResponse getRecipeList(String url,String machineNo,String updateTime);
    LsyResponse getRecipeInfo(String url, String machineNo,Integer id);
    LsyResponse getNutritionInfo(String url, String machineNo);
    LsyResponse getNutritionRecordInfo(String url, String machineNo);



}
