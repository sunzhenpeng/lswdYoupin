package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterOrderMapperGen;
import com.lswd.youpin.model.lsyp.CounterOrder;
import com.lswd.youpin.model.lsyp.CounterOrderItems;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterOrderMapper extends CounterOrderMapperGen {

    TotalCountMoney getOrderListCountBT(@Param(value = "counterId")String counterId, @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate,
                                        @Param(value = "payType")Integer payType);

    List<CounterOrder> getOrderListBT(@Param(value = "counterId")String counterId,@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,
                                      @Param(value = "payType")Integer payType, @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    TotalCountMoney getMemberOrderListCount(@Param(value = "counterId")String counterId, @Param(value = "memberName")String memberName, @Param(value = "memberCardUid")String memberCardUid);

    List<CounterOrder> getMemberOrderList(@Param(value = "counterId")String counterId, @Param(value = "memberName")String memberName, @Param(value = "memberCardUid")String memberCardUid,
                                              @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    List<CounterOrderItems> getMemberOrderItems(@Param(value = "orderId")String orderId);



}