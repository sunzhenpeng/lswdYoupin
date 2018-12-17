package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.JSPlatform;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/23.
 */
public interface JSPlatformService {

    LsResponse getJSPlatformListAll(User user, String canteenId);

    LsResponse getJSPlatformList(User user, String canteenId, String keyword, Integer pageNum, Integer pageSize);

    LsResponse addJSPlatform(JSPlatform jsPlatform, User user);

    LsResponse updateJSPlatform(JSPlatform jsPlatform, User user);

    LsResponse deleteJSPlatform(Integer id);
}
