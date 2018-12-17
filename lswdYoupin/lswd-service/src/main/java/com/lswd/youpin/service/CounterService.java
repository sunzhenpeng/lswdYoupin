package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.model.lsyp.CounterMenuLinked;
import com.lswd.youpin.model.lsyp.CounterUserLinked;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/11/15.
 */
public interface CounterService {

    LsResponse getCounterList(User user, String name, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse getCounterUserLinkedList(String counterId);

    LsResponse getCounterListAll();

    LsResponse addCounter(Counter counter, User user);

    LsResponse updateCounter(Counter counter, User user);

    LsResponse deleteCounter(Integer id);


    /*----------------------------------------------------WEB端  吧台统计部分接口 start--------------------------------------------------------------------------------*/

    LsResponse getCounterByCanteenIds(User user);

    LsResponse getBTOrderListWeb(User user, String counterId, String date, Integer payType, Integer pageNum, Integer pageSize);

    LsResponse exportBTOrderListWeb(User user, String counterId, String date, Integer payType, HttpServletResponse response);

    LsResponse getMemberBTOrderListWEB(User user, String counterId, String memberName, String memberCardUid, Integer pageNum, Integer pageSize);

    LsResponse getMemberBTOrderItemsWEB(User user,String orderId);

    /*----------------------------------------------------WEB端  吧台统计部分接口 end----------------------------------------------------------------------------------*/


    LsResponse getBingCounterUserList(String keyword, String counterId);

    LsResponse addCountBingUser(CounterUserLinked counterUserLinked, User user);

    LsResponse deleteCounterUserLinked(String counterId, String userId);

    LsResponse getCounterUserLinkedList(String counterId, String keyword);




    LsResponse getBingMenuList(String keyword, Integer menutypeId, String counterId, Integer pageNum, Integer pageSize);

    LsResponse addCountBingMenu(CounterMenuLinked counterMenuLinked, User user);

    LsResponse deleteCounterMenuLinked(String counterId, String menuId);

    LsResponse getCounterMenuLinkedList(String counterId, String keyword, Integer menutypeId, Integer pageNum, Integer pageSize);

    LsResponse getCounterMenuType(String counterId);

    LsResponse getCounterMenuByTypeId(Integer typeId, String counterId);
}
