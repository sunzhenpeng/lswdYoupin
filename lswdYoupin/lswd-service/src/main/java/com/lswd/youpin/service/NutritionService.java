package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Nutrition;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/12/2.
 */
public interface NutritionService {

    LsResponse getNutritionList(String keyword, Integer pageNum, Integer pageSize);

    LsResponse getNutritionListAll();

    LsResponse addNutrition(Nutrition nutrition, User user);

    LsResponse updateNutrition(Nutrition nutrition, User user);

    LsResponse deleteNutrition(Integer id);

    LsResponse getNutritionByName(String name);

    LsResponse exportnNutritionList(User user, HttpServletResponse response);
}
