package com.lswd.youpin.service;

import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterMenu;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/24.
 */
public interface CounterMenuService {

    LsResponse getcounterMenuTypeListAll();

    LsResponse getCounterMenuList(String keyword, String counterId, Integer menutypeId, Integer pageNum, Integer pageSize);

    LsResponse addCounterMenu(CounterMenu counterMenu, User user);

    LsResponse updateCounterMenu(CounterMenu counterMenu, User user);

    LsResponse deleteCounterMenu(Integer id, User user);


    /* -------------------------------------------------吧台收银员 菜品信息 增删改查-----------------------------------------------------------*/

    LsResponse getCounterMenuListBTSY(String keyword, String counterId, Integer menutypeId, Integer pageNum, Integer pageSize);

    LsResponse addCounterMenuBTSY(CounterMenu counterMenu, CounterUser counterUser);

    LsResponse updateCounterMenuBTSY(CounterMenu counterMenu, CounterUser counterUser);

    LsResponse deleteCounterMenuBTSY(String id, CounterUser counterUser);
}
