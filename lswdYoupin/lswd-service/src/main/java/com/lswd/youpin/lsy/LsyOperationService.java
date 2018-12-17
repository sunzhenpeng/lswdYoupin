package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyOperationService {

    LsyResponse getRulesList(String url, String machineNo);

    LsyResponse getRulesInfo(String url, String machineNo,Integer id);

    LsyResponse getDeviceControllList(String url, String machineNo);

    LsyResponse getDeviceDetailInfo(String url, String machineNo,Integer id);

    LsyResponse getOperationFlowList(String url, String machineNo);

    LsyResponse getOperationFlowInfo(String url, String machineNo,Integer id);

}
