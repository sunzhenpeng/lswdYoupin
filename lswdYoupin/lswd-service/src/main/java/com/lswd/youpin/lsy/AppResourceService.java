package com.lswd.youpin.lsy;

import com.lswd.youpin.model.lsy.AppResource;
import com.lswd.youpin.response.LsResponse;

public interface AppResourceService {
    LsResponse getAppResourceList(Integer machineId, String keyword);


    LsResponse getAppResourceByMachineNo(String machineNo);

    LsResponse addOrUpdateAppResource(AppResource machine);

    LsResponse getAppResourceById(Integer id);

    LsResponse delAppResource(Integer id);

}
