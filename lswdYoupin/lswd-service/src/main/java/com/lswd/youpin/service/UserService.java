package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.response.LsyResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/4/12.
 */
public interface UserService {
    User getUserByName(String name);

    LsResponse signUp(User user, User u);

    LsResponse login(String data,HttpServletRequest request);

    LsyResponse doLogin(String username,String password, HttpServletRequest request);

    LsResponse restPassword(Integer id, User u);

    LsResponse add(User user, User u);

    LsResponse updateUser(User user, User u);

    LsResponse deleteUser(int id, User user);

    LsResponse getUsers(User user, Integer pageNum, Integer pageSize, String keyword);

    User selectUserById(Integer id);

    LsResponse loginOut(String token);

    LsResponse add(User user);

    LsResponse updatePwd(String data, User user);

    LsResponse isToken(String token);

}
