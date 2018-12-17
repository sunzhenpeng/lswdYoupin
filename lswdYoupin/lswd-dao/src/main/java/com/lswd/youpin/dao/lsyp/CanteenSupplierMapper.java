package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CanteenSupplierMapperGen;
import org.apache.ibatis.annotations.Param;

public interface CanteenSupplierMapper extends CanteenSupplierMapperGen {
    int insertCanteenSupplierLink(@Param("canteenId") String canteenId, @Param("supplierId") String supplierId);
    int deleteCanteenSupplierLink(@Param("canteenId") String canteenId,@Param("supplierId") String supplierId);
}