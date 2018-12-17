package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.lsyp.RecipeOrder;
import com.lswd.youpin.response.LsResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/6/21.
 */
public interface RecipeOrderService {
    LsResponse getRecipeOrderList(String keyword, Integer pageNum, Integer pageSize,
                                  Integer flag,List<String> canteenIds,Integer dataTime,Integer payType);


    LsResponse getRecipeOrder(Integer status, String orderId);

    LsResponse getAsrecipeOrderList(Associator associator, Integer pageNum, Integer pageSize,Integer flag);

    LsResponse deleteOrder(Associator associator,String orderId);

    LsResponse getOrderListWx(Integer pageNum, Integer pageSize, List<String> canteenIds, Date startDate,
                                     Date endDate, TenantAssociator tenantAssociator,Integer flag);

    LsResponse openRecipeCommentH5(String recipeOrderId);

    LsResponse deleteByOrderNo(String orderNo,String table);

    LsResponse insertOrderRefund(RecipeOrder recipeOrder);

    LsResponse getRecipeOrderByOrderNo(String orderNo,String table);

    Integer updateStatus(String orderId);

    LsResponse removeOrder(Associator associator, String orderId);
}
