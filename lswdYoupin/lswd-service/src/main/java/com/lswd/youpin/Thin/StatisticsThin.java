package com.lswd.youpin.Thin;

import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import java.util.Date;

/**
 * Created by liuhao on 2017/7/31.
 */
public interface StatisticsThin {
    LsResponse getCanteenOrders(User user);

    LsResponse getSalesCount(String startDate, String endDate, String canteenId,TenantAssociator tenantAssociator);

    LsResponse goodSalesCount(String date, Integer pageSize, Integer pageNum, String canteenId, TenantAssociator tenantAssociator);

    LsResponse recipeSalesCount(String date, Integer pageSize, Integer pageNum, String canteenId, TenantAssociator tenantAssociator);

    LsResponse payTypeMoney(String canteenId, User user, String startDate,String endDate);

    LsResponse payTypeOrder(String canteenId, User user,String startDate,String endDate);

    LsResponse goodSaleCount(User user, String canteenId, String date);
}
