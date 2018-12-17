package com.lswd.youpin.Thin;

import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/7/14.
 */
public interface MaterialThin {
    LsResponse lookMaterial(Integer id);

    LsResponse categoryList(String canteenId, Integer pageNum, Integer pageSize, String keyword);

}
