package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodOrder;
import com.lswd.youpin.response.LsResponse;

import java.util.Date;
import java.util.List;


/**
 * Created by zhenguanqi on 2017/6/22.
 */
public interface GoodOrderService {
    LsResponse getGoodOrderList(User user, String keyword, String associatorId, String canteenId, String orderTime,Integer dateflag, Integer payType,Integer flag, Integer pageNum, Integer pageSize);

    LsResponse getGoodOrderStatistics(Date startTime, Date endTime, String canteenId);

    LsResponse getGoodOrderDetails(String goodOrderId);
    /*-----------------------------------------------------------------*/
    LsResponse getGoodOrderListH5Show(String associatorId, Integer flag, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse getGoodOrderDetailsH5(Integer status, String goodOrderId);

    LsResponse paidNowH5(String goodOrderId);

    LsResponse cancelGoodOrderH5(String goodOrderId);

    LsResponse confirmGoodOrderH5(String goodOrderId);    /*暂时没有用到*/

    LsResponse deleteGoodOrderH5(Integer status, String goodOrderId);

    LsResponse openGoodCommentH5(String goodOrderId);

    LsResponse applyRefundGorRH5(String orderId,String deadLine);

    /*------------------------------小程序用到的方法-----------------------------------*/
    LsResponse getGoodOrderListWxShow(String[] canteenIds, Integer flag, String canteenId, String orderTime, Integer pageNum, Integer pageSize);

    LsResponse confirmOrderWx(String orderId);

    LsResponse confirmOrderInfoWx(String orderId);

    LsResponse getGoodOrderDetailsWx(Integer status, String goodOrderId);

    LsResponse getGoodOrderByGoodOrderId(String orderNo, String table);

    LsResponse insertGoodOrder(GoodOrder goodOrder, String table);

    LsResponse deleteGoodOrderByGoodOrderId(String orderNo, String table);

    Integer updateStatus(String orderId);

    List<GoodOrder> selectGoodPaidAllList(Date date);
}
