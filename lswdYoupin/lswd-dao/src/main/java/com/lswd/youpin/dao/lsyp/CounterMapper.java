package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterMapperGen;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.model.lsyp.CounterOrder;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterMapper extends CounterMapperGen {

    List<Counter> getCounterListAll();

    List<Counter> getCounterByCanteenIds(@Param(value = "canteenIds")String[] canteenIds);

    int getCounterListCount(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId, @Param(value = "canteenIds") String[] canteenIds);

    List<Counter> getCounterList(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId,
                                 @Param(value = "canteenIds") String[] canteenIds, @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);


    Integer getMaxId();

    Counter getCounterByCounterId(@Param(value = "counterId") String counterId);

    TotalCountMoney getBTOrderListWebCount(@Param(value = "counterId")String counterId, @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate,
                                           @Param(value = "payType")Integer payType);

    List<CounterOrder> getBTOrderListWeb(@Param(value = "counterId")String counterId,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,
                                         @Param(value = "payType")Integer payType, @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    List<CounterOrder> exportBTOrderListWeb(@Param(value = "counterId")String counterId,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,
                                            @Param(value = "payType")Integer payType);
}