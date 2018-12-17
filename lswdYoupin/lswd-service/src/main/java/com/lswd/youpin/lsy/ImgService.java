package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Img;
import com.lswd.youpin.response.LsResponse;

public interface ImgService {
    LsResponse getImgList(User u, String keyword, Integer resTypeId, String updateTime, Integer pageNum, Integer pageSize);

    LsResponse getImgById(Integer id);

    LsResponse addOrUpdateImg(Img img);

    LsResponse delImg(Integer id);

}
