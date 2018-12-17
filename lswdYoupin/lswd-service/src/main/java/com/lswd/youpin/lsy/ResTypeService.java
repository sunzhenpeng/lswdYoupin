package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.ResType;
import com.lswd.youpin.response.LsResponse;

public interface ResTypeService {
    LsResponse getResTypeList(User u, String keyword,String type, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize);

    LsResponse getResTypeById(Integer id);

    LsResponse addOrUpdateResType(ResType machine);

    LsResponse delResType(Integer id);

}
