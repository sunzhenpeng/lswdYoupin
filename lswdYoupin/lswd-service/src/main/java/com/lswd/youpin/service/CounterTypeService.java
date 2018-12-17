package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterType;
import com.lswd.youpin.model.lsyp.CounterTypeRecipeCategory;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/15.
 */
public interface CounterTypeService {

    LsResponse getCounterTypeList(String name);

    LsResponse addCounterType(CounterType counterType, User user);

    LsResponse updateCounterType(CounterType counterType, User user);

    LsResponse deleteCounterType(Integer id);

    LsResponse getCounterTypeRecipeCateList(Integer counterTypeId,String canteenId);

    LsResponse bindingCounterTypeRecipeCate(CounterTypeRecipeCategory counterTypeRecipeCategory,User user);

    LsResponse deleteCounterTypeRecipeCate(Integer counterTypeId,Integer recipeCategoryId);

}
