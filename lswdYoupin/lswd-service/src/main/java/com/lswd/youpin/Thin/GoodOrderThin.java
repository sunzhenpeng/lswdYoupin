package com.lswd.youpin.Thin;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/7/17.
 */
public interface GoodOrderThin {
    LsResponse getGoodOrderList(User user, String keyword, String associatorId, String canteenId, String orderTime,Integer dateflag,Integer payType, Integer flag, Integer pageNum, Integer pageSize);

    LsResponse getGoodOrderListH5Show(String associatorId,Integer flag,String canteenId,Integer pageNum,Integer pageSize);

    LsResponse getGoodOrderDetailsH5(Integer status,String goodOrderId);

    LsResponse openGoodCommentH5(String goodOrderId);

    LsResponse applyRefundGorRH5(String orderId,String canteenId);

    LsResponse paidNowH5(Associator associator,String goodOrderId);

    LsResponse getGoodOrderListWxShow(String associatorId, Integer flag, String canteenId, String orderTime, Integer pageNum, Integer pageSize);

    LsResponse confirmOrderInfoWx(TenantAssociator tenantAssociator,String orderId);

    LsResponse getGoodOrderDetailsWx(Integer status,String goodOrderId);
}
