package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterTypeRecipeCategoryMapperGen;
import com.lswd.youpin.model.lsyp.CounterTypeRecipeCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterTypeRecipeCategoryMapper extends CounterTypeRecipeCategoryMapperGen {

    List<CounterTypeRecipeCategory> getCounterTypeRecipeCateList(@Param(value = "counterTypeId")Integer counterTypeId,@Param(value = "canteenId")String canteenId);

    int deleteCounterTypeRecipeCate(@Param(value = "counterTypeId")Integer counterTypeId,@Param(value = "recipeCategoryId")Integer recipeCategoryId);

}