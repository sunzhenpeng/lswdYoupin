package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodOrderItemsMapperGen;
import com.lswd.youpin.model.lsyp.GoodOrderItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodOrderItemsMapper extends GoodOrderItemsMapperGen {

    List<GoodOrderItems> getGoodOrderItemsList(@Param(value = "orderId")String orderId);

    int insertGoodOrderItems(GoodOrderItems goodOrderItems);

    int deleteGoodOrderItems(@Param(value = "goodOrderid")String goodOrderid);

    List<GoodOrderItems> getGoodItemss(@Param(value = "orderIds") List<String> orderIds);
}