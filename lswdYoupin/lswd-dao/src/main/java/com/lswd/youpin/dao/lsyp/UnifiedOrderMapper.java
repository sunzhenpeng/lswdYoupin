package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.UnifiedOrderMapperGen;
import com.lswd.youpin.model.lsyp.UnifiedOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnifiedOrderMapper extends UnifiedOrderMapperGen {

    UnifiedOrder getUnifiedOrderByOrderNo(@Param("orderNo") String orderNo, @Param("table") String table);

    List<UnifiedOrder> getUnifiedOrderList(@Param("orderNo") String orderNo,@Param("table") String table);

}