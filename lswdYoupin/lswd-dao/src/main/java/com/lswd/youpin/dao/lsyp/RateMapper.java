package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RateMapperGen;
import com.lswd.youpin.model.lsyp.Rate;
import org.apache.ibatis.annotations.Param;

public interface RateMapper extends RateMapperGen {
    Rate getByCanteenId(@Param("canteenId") String canteenId);

}