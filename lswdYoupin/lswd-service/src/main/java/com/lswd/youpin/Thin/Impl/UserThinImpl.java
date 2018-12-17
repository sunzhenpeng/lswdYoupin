package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.UserThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.UserService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2017/7/12.
 */
@Component
public class UserThinImpl implements UserThin {
    private final Logger log = LoggerFactory.getLogger(UserThinImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse getUsers(User user, Integer pageNum, Integer pageSize, String keyword) {
        LsResponse lsResponse = new LsResponse();
        try {
            List<User> userList = new ArrayList<>();
            lsResponse = userService.getUsers(user, pageNum, pageSize, keyword);
            if (lsResponse.getSuccess()) {
                List<User> users = (List<User>) lsResponse.getData();
                for (int i = 0; i < users.size(); i++) {
                    User u = users.get(i);
                    String[] canteenIds = u.getCanteenIds().split(",");
                    List<Canteen> canteenList = new ArrayList<>();
                    for (String canteenId : canteenIds) {
                        Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
                        if (canteen != null) {
                            canteenList.add(canteen);
                        }
                    }
                    u.setRestaurants(canteenList);
                    userList.add(u);
                }
            }
            lsResponse.setData(userList);
        } catch (Exception e) {
            log.error("获取用户信息" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getUserByName(String username) {
        LsResponse lsResponse=new LsResponse();
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            User user=userService.getUserByName(username);
            if(user==null)
            {
                lsResponse.setSuccess(true);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.USER_YE_LOGIN_ERR.name());
            }
        } catch (Exception e) {
            log.error("根据用户名查找用户信息"+e.toString());
        }
        return lsResponse;
    }
}
