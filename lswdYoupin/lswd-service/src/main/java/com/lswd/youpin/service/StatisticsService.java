package com.lswd.youpin.service;

import com.lswd.youpin.model.AssociatorAccount;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/7/4.
 */
public interface StatisticsService {
    LsResponse getSalesCount(String startDate, String endDate,List<String> canteenIds);

    LsResponse goodSalesCount(String date,Integer pageSize,Integer pageNum,List<String> canteenIds) throws ParseException;

    LsResponse recipeSalesCount(String date, Integer pageSize, Integer pageNum,List<String> canteenIds) throws ParseException;

    LsResponse getDanPin(User user);

    LsResponse getCateenOrders(String canteenId);

    List<Map<String, Object>> payTypeMoney(List<String> canteenIds, Date startDate, Date endDate);

    List<Integer> payTypeOrder(List<String> canteenIds,Date startDate,Date endDate);

    LsResponse timeGoodOrder(User user);

    LsResponse timeRecipeOrder(User user);

    LsResponse goodSaleCount(User user,String canteenId,String date);
}
