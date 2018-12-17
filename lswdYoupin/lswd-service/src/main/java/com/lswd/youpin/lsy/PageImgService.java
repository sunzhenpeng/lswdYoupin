package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.PageImg;
import com.lswd.youpin.response.LsResponse;

public interface PageImgService {
    LsResponse getPageImgList(User u, String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize);

    LsResponse getPageImgById(Integer id);

    LsResponse addOrUpdatePageImg(PageImg machine);

    LsResponse delPageImg(Integer id);

}
