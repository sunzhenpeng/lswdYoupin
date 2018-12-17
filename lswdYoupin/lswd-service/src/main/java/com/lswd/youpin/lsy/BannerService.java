package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Banner;
import com.lswd.youpin.response.LsResponse;

public interface BannerService {
    LsResponse getBannerList(User u, String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize);

    LsResponse getBannerById(Integer id);

    LsResponse addOrUpdateBanner(Banner machine);

    LsResponse delBanner(Integer id);

}
