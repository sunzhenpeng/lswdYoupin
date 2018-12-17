package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MealRecordMapperGen;
import com.lswd.youpin.model.lsyp.MealRecord;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.model.vo.MealRecordVO;
import com.lswd.youpin.model.vo.MealStatisticVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MealRecordMapper extends MealRecordMapperGen {

    Integer getMemberMealRecordListCount(@Param(value = "keyword") String keyword, @Param(value = "startDate") String startDate,
                                         @Param(value = "endDate") String endDate, @Param(value = "canteenId") String canteenId);

    List<MealRecord> getMemberMealRecordList(@Param(value = "keyword") String keyword, @Param(value = "startDate") String startDate,
                                             @Param(value = "endDate") String endDate, @Param(value = "canteenId") String canteenId,
                                             @Param(value = "offSet") Integer offSet,
                                             @Param(value = "pageSize") Integer pageSize);


    Integer getRecipeMealRecordListCount(@Param(value = "keyword") String keyword, @Param(value = "startDate") String startDate,
                                         @Param(value = "endDate") String endDate, @Param(value = "categoryId") Integer categoryId,
                                         @Param(value = "canteenId") String canteenId, @Param(value = "canteenIds") String[] canteenIds);

    List<Recipe> getRecipeMealRecordList(@Param(value = "keyword") String keyword, @Param(value = "startDate") String startDate,
                                         @Param(value = "endDate") String endDate, @Param(value = "categoryId") Integer categoryId,
                                         @Param(value = "canteenId") String canteenId, @Param(value = "canteenIds") String[] canteenIds,
                                         @Param(value = "offSet") Integer offSet,
                                         @Param(value = "pageSize") Integer pageSize);

    List<Recipe> getRecipeSaleSpeed(@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate, @Param(value = "canteenId") String canteenId,
                                    @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime);

    List<MealRecordVO> getMealRecords(@Param(value = "canteenId") String canteenId,
                                      @Param(value = "leaveId") Integer leaveId,
                                      @Param(value = "memberName") String memberName,
                                      @Param(value = "startTime") Date startTime,
                                      @Param(value = "endTime") Date endTime,
                                      @Param(value = "offSet") Integer offSet,
                                      @Param(value = "pageSize") Integer pageSize);

    Integer getTotalMembers(@Param(value = "canteenId") String canteenId,
                            @Param(value = "leaveId") Integer leaveId,
                            @Param(value = "startTime") Date startTime,
                            @Param(value = "endTime") Date endTime);

    List<String> getTotalCount(@Param(value = "canteenId") String canteenId,
                               @Param(value = "leaveId") Integer leaveId,
                               @Param(value = "memberName") String memberName,
                               @Param(value = "startTime") Date startTime,
                               @Param(value = "endTime") Date endTime);

    List<MealStatisticVO> getSalesAll(@Param(value = "canteenId") String canteenId,
                                   @Param(value = "deviceId") Integer deviceId,
                                   /*@Param(value = "leaveId") Integer leaveId,*/
                                   @Param(value = "startTime") Date startTime,
                                   @Param(value = "endTime") Date endTime,
                                   @Param(value = "offSet") Integer offSet,
                                   @Param(value = "pageSize") Integer pageSize);
    List<MealStatisticVO> getSalesBy(@Param(value = "canteenId") String canteenId,
                                      @Param(value = "deviceId") Integer deviceId,
                                     /* @Param(value = "leaveId") Integer leaveId,*/
                                      @Param(value = "startTime") Date startTime,
                                      @Param(value = "endTime") Date endTime,
                                      @Param(value = "offSet") Integer offSet,
                                      @Param(value = "pageSize") Integer pageSize);

    List<MealRecord> exportJSTSales(@Param(value = "canteenId") String canteenId,
                                    @Param(value = "deviceId") Integer deviceId,
                                    @Param(value = "startTime") Date startTime,
                                    @Param(value = "endTime") Date endTime);

    Integer getSalesAllCount(@Param(value = "canteenId") String canteenId,
                          @Param(value = "deviceId") Integer deviceId,
                          /*@Param(value = "leaveId") Integer leaveId,*/
                          @Param(value = "startTime") Date startTime,
                          @Param(value = "endTime") Date endTime);

    Float getSalesAllAmount(@Param(value = "canteenId") String canteenId,
                         @Param(value = "deviceId") Integer deviceId,
                         /*@Param(value = "leaveId") Integer leaveId,*/
                         @Param(value = "startTime") Date startTime,
                         @Param(value = "endTime") Date endTime);

    Integer getSalesCountBy(@Param(value = "canteenId") String canteenId,
                             @Param(value = "deviceId") Integer deviceId,
                           /*  @Param(value = "leaveId") Integer leaveId,*/
                             @Param(value = "startTime") Date startTime,
                             @Param(value = "endTime") Date endTime);

    Float getSalesAmountBy(@Param(value = "canteenId") String canteenId,
                            @Param(value = "deviceId") Integer deviceId,
                            /*@Param(value = "leaveId") Integer leaveId,*/
                            @Param(value = "startTime") Date startTime,
                            @Param(value = "endTime") Date endTime);

    List<MealRecord> getPersonMealRecords(@Param(value = "canteenId") String canteenId,
                                          @Param(value = "memberId") Integer memberId,
                                          @Param(value = "startTime") Date startTime,
                                          @Param(value = "endTime") Date endTime);

    List<MealRecord> getMemberNutrition(@Param(value = "canteenId") String canteenId, @Param(value = "memberId") Integer memberId,
                                        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);


}