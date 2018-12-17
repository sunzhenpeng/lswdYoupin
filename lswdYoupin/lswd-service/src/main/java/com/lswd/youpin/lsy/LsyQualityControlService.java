package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyQualityControlService {

    LsyResponse getQualityControllMainInfo(String url, String machineNo);

    LsyResponse getWorkLogList(String url, String machineNo);

    LsyResponse getWorkLogDetailInfo(String url, String machineNo,Integer id,String updateTime);

    LsyResponse getQualityInnerControllInfo(String url, String machineNo,String updateTime);

    LsyResponse getQualityOutControllInfo(String url, String machineNo,String updateTime);

    LsyResponse getSupplierList(String url, String machineNo);

    LsyResponse getSupplierDetail(String url, String machineNo,Integer id);

    LsyResponse getQualityRegulatoryInfo(String url, String machineNo);



}
