package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/12/11.
 */
public interface ResourceService {
    LsResponse getResourceByParentId(Integer parentId,User u);

    LsResponse getResourceListAll();
}
