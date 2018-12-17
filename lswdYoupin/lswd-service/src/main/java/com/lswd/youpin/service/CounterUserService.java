package com.lswd.youpin.service;

import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.response.LsResponse;
import org.w3c.dom.ls.LSException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/29.
 */
public interface CounterUserService {

    LsResponse getCounterUserList(String keyword, Integer pageNum, Integer pageSize);

    LsResponse addCounterUser(CounterUser counterUser, User user);

    LsResponse updateCounterUser(CounterUser counterUser, User user);

    LsResponse deleteCounterUser(Integer id);

    LsResponse updateCounterUserStatus(Integer id,Boolean status);

    LsResponse resetCounterUserPass(Integer id,User user);

    CounterUser getCounterUserByUserId(String userId);

    LsResponse getCounterUserByName(String username);

    LsResponse updateCounterUserPassBT(String passData,CounterUser counterUser);

    LsResponse loginCounterUserBT(String userData, HttpServletRequest request);
}
