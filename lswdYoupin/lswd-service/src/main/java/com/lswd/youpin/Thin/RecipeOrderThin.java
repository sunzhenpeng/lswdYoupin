package com.lswd.youpin.Thin;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import java.util.Date;

/**
 * Created by liuhao on 2017/7/18.
 */
public interface RecipeOrderThin {
    LsResponse getRecipeOrderList(String keyword, Integer pageNum, Integer pageSize, Integer flag, String canteenId,Integer dataTime,Integer payType
    ,User user);

    LsResponse getRecipeOrder(Associator associator, Integer status, String orderId);

    LsResponse getAsrecipeOrderList(Associator associator, Integer pageNum, Integer pageSize,Integer flag);

    LsResponse getOrderListWx(Integer pageNum, Integer pageSize, String canteenId, String dataTime, TenantAssociator tenantAssociator,Integer flag);

    LsResponse openRecipeCommentH5(String recipeOrderId);

    LsResponse getRecipeOrderWx(Integer status, String orderId);

    LsResponse lookOrder(String orderId,Associator associator);
}
