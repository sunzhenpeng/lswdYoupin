package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/12/11.
 */
public interface MealRecordService {

    LsResponse getMemberMealRecordList(User user, String keyword, String date, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse getRecipeMealRecordList(User user, String keyword, Integer categoryId, String date, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse getRecipeSaleSpeed(User user, String date, String startTime,String endTime, String canteenId);

    LsResponse getMealRecords(User u, String canteenId, Integer leaveId,String memberName, String startTime, String endTime, Integer pageNum, Integer pageSize);

    LsResponse getSales(String canteenId, Integer deviceId, String startTime, String endTime, Integer pageNum, Integer pageSize);

    LsResponse exportJSTSales(String canteenId, Integer deviceId, String startTime, String endTime, HttpServletResponse response);

    LsResponse getPersonMealRecords(String canteenId, Integer memberId, String eatTime);

    LsResponse getMemberNutrition(User user, String canteenId, Integer memberId, Integer nutritionFlag, Integer timeFlag);
}
