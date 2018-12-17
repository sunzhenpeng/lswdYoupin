package com.lswd.youpin.service;

import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/7/17.
 */
public interface RegionService {
    LsResponse addOrUpdateRegion(Region region,User u);

    LsResponse getAllRegion(String keyword, Integer pageNum, Integer pageSize);

    LsResponse getRegionById(Integer id);

    LsResponse getRegionAll();
}
