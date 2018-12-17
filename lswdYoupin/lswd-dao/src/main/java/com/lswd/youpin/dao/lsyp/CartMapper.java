package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.CartMapperGen;
import com.lswd.youpin.model.lsyp.Cart;
import org.apache.ibatis.annotations.Param;

public interface CartMapper extends CartMapperGen {

    Cart getCartByCanAndGoodIdAndAsso(@Param(value = "canteenId") String canteenId, @Param(value = "goodId") String goodId,
                                      @Param(value = "associatorId") String associatorId, @Param(value = "pickingTime") String pickingTime, @Param(value = "eatType") Integer eatType);
}