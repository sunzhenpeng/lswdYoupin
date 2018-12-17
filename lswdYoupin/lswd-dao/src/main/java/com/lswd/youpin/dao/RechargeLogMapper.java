package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.RechargeLogMapperGen;
import com.lswd.youpin.model.RechargeLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeLogMapper extends RechargeLogMapperGen {

    List<RechargeLog> getRechargeLog(
            @Param("associatorId") String associatorId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    Integer getTotalCount(@Param("associatorId") String associatorId,
                          @Param("startTime") String startTime,
                          @Param("endTime") String endTime);
    List<RechargeLog> getRechargeLogById(@Param("associatorId") String associatorId);

}