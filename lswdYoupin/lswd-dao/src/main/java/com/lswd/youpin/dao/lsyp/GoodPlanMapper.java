package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodPlanMapperGen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GoodPlanMapper extends GoodPlanMapperGen {
    int insertGoodPlan(GoodPlan goodPlan);

    int deleteGoodPlanByGoodPlanId(@Param(value = "goodPlanId") String goodPlanId);//修改状态

    int updateGoodPlanIsDelete(GoodPlan goodPlan);

    int updateGoodPlan(GoodPlan goodPlan);

    /*商品计划新增时需要执行的方法*/
    List<GoodPlan> getGoodPlanDetailsList(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId,
                                          @Param(value = "startdate") String startdate,@Param(value = "enddate") String enddate);

    GoodPlan getGoodPlanById(@Param(value = "id") Integer id);

    GoodPlan getGoodPlanByGoodPlanId(@Param(value = "goodplanid") String goodplanid);


    int getGoodPlanListCountWebPageShow(@Param(value = "now") String now,@Param(value = "canteenId")String canteenId,
                         @Param(value = "starttime")String starttime,@Param(value = "endtime")String endtime,@Param(value = "canteenIds") String[] canteenIds);
    List<GoodPlan> getGoodPlanListWebPageShow(@Param(value = "now") String now, @Param(value = "canteenId")String canteenId,
                                      @Param(value = "starttime")String starttime,@Param(value = "endtime")String endtime,@Param(value = "canteenIds") String[] canteenIds,
                                      @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);


    List<GoodPlan> getGoodPlanContent(@Param(value = "canteenId") String canteenId, @Param(value = "categoryId") Integer categoryId,
                                      @Param(value = "startSaleTime") String startSaleTime, @Param(value = "endSaleTime") String endSaleTime);

    Integer getMaxId();

    List<GoodPlan> getGoodPlanBeforeNow(@Param(value = "now")String now);

    int addCommentByGoodPlanId(@Param(value = "goodPlanId") String goodPlanId, @Param(value = "comment") String comment);


    /*下面是H5用到的方法！！！*/
    GoodPlan getGoodPlanContentByCanAndTime(@Param(value = "canteenId")String canteenId, @Param(value = "pickingTime")String pickingTime);

    Integer getGoodPlanCount(@Param(value = "planId") String planId, @Param(value = "goodId") String goodId);


    Integer updateGoodPlanCount(@Param(value = "planId") String planId,@Param(value = "goodId") String goodId,@Param(value = "num") Integer num);

    Float getPrice(@Param(value = "goodId") String goodId,@Param(value = "planId") String planId);

    Boolean getGoodFlag(@Param(value = "planId") String planId, @Param(value = "goodId") String goodId);


    List<GoodPlan> getGoodPlanContentByTime(@Param(value = "canteenIds") List<String> canteenIds,
                                            @Param(value = "startDate") Date startDate,@Param(value = "endDate") Date endDate);

}