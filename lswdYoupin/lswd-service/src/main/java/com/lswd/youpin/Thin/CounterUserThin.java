package com.lswd.youpin.Thin;

import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/29.
 */
public interface CounterUserThin {
    LsResponse getCounterUserList(String keyword, Integer pageNum, Integer pageSize);

    LsResponse addCounterUser(CounterUser counterUser, User user);

    LsResponse updateCounterUser(CounterUser counterUser, User user);

    LsResponse deleteCounterUser(Integer id);

    LsResponse updateCounterUserStatus(Integer id, Boolean status);

    LsResponse resetCounterUserPass(Integer id,User user);

}
