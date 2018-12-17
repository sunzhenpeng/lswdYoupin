package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodAttributeMapperGen;
import com.lswd.youpin.model.lsyp.GoodAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodAttributeMapper extends GoodAttributeMapperGen {

    GoodAttribute getAttributeByGoodId(@Param(value = "goodId") String goodId);

    int updateByGoodId(GoodAttribute goodAttribute);

}