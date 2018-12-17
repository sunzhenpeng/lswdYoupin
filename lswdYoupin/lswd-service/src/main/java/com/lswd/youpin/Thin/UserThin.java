package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/7/12.
 */
public interface UserThin {
    LsResponse getUsers(User user, Integer pageNum, Integer pageSize, String keyword);
    LsResponse getUserByName(String username);
}
