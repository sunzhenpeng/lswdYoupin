package com.lswd.youpin.lsy;

import com.lswd.youpin.model.lsy.Page;
import com.lswd.youpin.response.LsResponse;

public interface PageService {
    LsResponse getPageList(String keyword, Integer machineId, Integer PageNum, Integer PageSize);

    LsResponse getPageById(Integer id);

    LsResponse addOrUpdatePage(Page machine);

    LsResponse delPage(Integer id);

}
