package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorRefundBillMapperGen;
import com.lswd.youpin.model.AssociatorRefundBill;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorRefundBillMapper extends AssociatorRefundBillMapperGen {

    Float getAssociatorBalance(@Param(value = "associatorId")String associatorId);

    Integer updateCardBalance(@Param(value = "associatorId")String associatorId,@Param(value = "money")Float money);


    TotalCountMoney getAssociatorRefundBillListCount(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                                     @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate,
                                                     @Param(value = "user")User user, @Param(value = "canteenId")String canteenId);

    List<AssociatorRefundBill> getAssociatorRefundBillList(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                                                @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate, @Param(value = "user")User user,
                                                                @Param(value = "canteenId")String canteenId, @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    Integer getAssociatorRefundBillListAll(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                        @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate, @Param(value = "user")User user,
                                        @Param(value = "canteenId")String canteenId);

}