package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyKitchenService {

    LsyResponse getLiveshowList(String url, String machineNo);

    LsyResponse getLiveshowInfo(String url, String machineNo,Integer id);

    LsyResponse getLiveshowInfoTemp(String url, String machineNo,Integer id);


}
