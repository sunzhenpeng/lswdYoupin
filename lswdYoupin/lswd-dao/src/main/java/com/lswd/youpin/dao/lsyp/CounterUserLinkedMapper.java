package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CounterUserLinkedMapperGen;
import com.lswd.youpin.model.lsyp.CounterUserLinked;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterUserLinkedMapper extends CounterUserLinkedMapperGen {

    List<CounterUserLinked> isBingCounterByUserId(@Param(value = "userId")String userId);

    List<CounterUserLinked> isBingCounterByCounterId(@Param(value = "counterId")String counterId);

    Integer deleteByTwoForigenKey(@Param(value = "counterId")String counterId,@Param(value = "userId")String userId);

    CounterUserLinked getCounterUserLinkedList(@Param(value = "counterId")String counterId, @Param(value = "keyword")String keyword);

    CounterUserLinked getCounterUserByUserId(@Param(value = "userId")String userId);

    String getCounterIdByUserId(@Param(value = "userId")String userId);

}