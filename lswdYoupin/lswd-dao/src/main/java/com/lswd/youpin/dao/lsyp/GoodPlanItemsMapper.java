package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodPlanItemsMapperGen;
import com.lswd.youpin.model.lsyp.GoodPlanItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodPlanItemsMapper extends GoodPlanItemsMapperGen {

    int updateById(GoodPlanItems goodPlanItems);

    int updatePlanItemsOneDay(GoodPlanItems goodPlanItems);

    int deleteByGoodPlanId(@Param(value = "goodPlanId")String goodPlanId);

    int deleteGoodPlanOneGood(@Param(value = "goodPlanId")String goodPlanId,@Param(value = "goodId")String goodId);

    int insertGoodPlanItems(GoodPlanItems goodPlanItems);

    int getGoodPlanDetailsCount(@Param(value = "goodPlanId") String goodPlanId);

    List<GoodPlanItems> getGoodPlanDetailsListByGoodPlanId(@Param(value = "goodPlanId") String goodPlanId);

    /*以下是H5画面需要用到的方法*/
    int getItemsByPlanIdAndCateCount(@Param(value = "keyword") String keyword,@Param(value = "goodPlanId") String goodPlanId,@Param(value = "categoryId") Integer categoryId);

    List<GoodPlanItems> getItemsByPlanIdAndCate(@Param(value = "keyword") String keyword,@Param(value = "goodPlanId") String goodPlanId,@Param(value = "categoryId") Integer categoryId,
                                        @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    int updateGoodPlanSurPlus(@Param(value = "goodPlanId")String goodPlanId,@Param(value = "goodId")String goodId,@Param(value = "surplus")Integer surplus);

    List<GoodPlanItems> getGoodPlanByPlanIds(@Param(value = "goodPlanIds") List<String> goodPlanIds);
}