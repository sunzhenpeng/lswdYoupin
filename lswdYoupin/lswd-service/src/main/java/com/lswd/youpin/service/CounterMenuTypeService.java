package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MenuType;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/24.
 */
public interface CounterMenuTypeService {

    LsResponse getcounterMenuTypeList(String name,Integer pageNum,Integer pageSize);

    LsResponse addCounterMenuType(MenuType menuType, User user);

    LsResponse updateCounterMenuType(MenuType menuType, User user);

    LsResponse deleteCounterMenuType(Integer id);
}
