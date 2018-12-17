package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodAdditiveattributeMapperGen;
import com.lswd.youpin.model.lsyp.GoodAdditiveattribute;
import org.apache.ibatis.annotations.Param;


public interface GoodAdditiveattributeMapper extends GoodAdditiveattributeMapperGen {
    GoodAdditiveattribute getAdditiveattributeByGoodId(@Param(value = "goodId") String goodId);

    int deleteByGoodId(@Param(value = "goodId") String goodId);

    int insertGoodAdditiveattribute(GoodAdditiveattribute goodAdditiveattribute);

    int updateByGoodId(GoodAdditiveattribute goodAdditiveattribute);
}