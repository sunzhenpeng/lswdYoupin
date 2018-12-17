package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsySupplyManageService {

    LsyResponse getSupplyControllMainInfo(String url, String machineNo);

    LsyResponse getSupplyMeatMenuMainInfo(String url, String machineNo);

    LsyResponse getSupplyOilMenuMainInfo(String url, String machineNo);

    LsyResponse getSupplyMeatInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyWaterSeafoodInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyVegetablesInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyFruitsInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyRiceInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyFlourInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyOilInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyHalfFoodInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyKitchenInfo(String url, String machineNo,String updateTime);
    LsyResponse getSupplyOtherFoodInfo(String url, String machineNo, String updateTime);
}
