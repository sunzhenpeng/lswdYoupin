package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Orders;
import com.lswd.youpin.response.LsResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/6/26.
 */
public interface OrderService {
//    LsResponse addGenerateOrder(List<Map<String, Object>> carOrders, Associator associator,Integer time);
    LsResponse addGenerateOrder(Orders orders, Associator associator, Integer time,Double outFee);

    LsResponse timeOrder(List<String> carOrders, Associator associator);

    LsResponse removeOrder(List<String> carOrders);

    LsResponse getOrdersCount(Associator associator,Boolean flag);
}
