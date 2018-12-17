package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyCanteenQualityService {

    LsyResponse getRestaurantManagementMainInfo(String url, String machineNo);
    LsyResponse getQualificationList(String url, String machineNo);
    LsyResponse getForbiddenList(String url, String machineNo);
    LsyResponse getStaffList(String url, String machineNo);
    LsyResponse getStaffInfo(String url,Integer id, String machineNo);
    LsyResponse getTrainList(String url, String machineNo);
    LsyResponse getTrainInfo(String url, String machineNo,String updateTime,Integer id);


}
