package com.lswd.youpin.service;

import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.lsyp.CounterOrder;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/07.
 */
public interface CounterOrderService {

    LsResponse generateCounterOrder(CounterOrder counterOrder, CounterUser counterUser);

    LsResponse readCardUid(Float receivedAmount);

    LsResponse getMemberTypeListAllBT();

    LsResponse readCardBackUid();

    LsResponse getMemberListBT(CounterUser counterUser, String memberName, String memberTel, String memberCardUid, Integer typeId, String counterId, Integer pageNum, Integer pageSize);

    LsResponse getMemberOrderListBT(CounterUser counterUser, String counterId, String memberName, String memberCardUid, Integer pageNum, Integer pageSize);

    LsResponse getMemberOrderItemsBT(CounterUser counterUser, String orderId);

    LsResponse getOrderListBT(CounterUser counterUser, String counterId, String date, Integer payType, Integer pageNum, Integer pageSize);

}
