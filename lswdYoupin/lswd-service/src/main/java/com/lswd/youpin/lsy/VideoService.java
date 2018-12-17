package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Video;
import com.lswd.youpin.response.LsResponse;

public interface VideoService {
    LsResponse getVideoList(User u, String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize);

    LsResponse getVideoById(Integer id);

    LsResponse addOrUpdateVideo(Video machine);

    LsResponse delVideo(Integer id);

}
