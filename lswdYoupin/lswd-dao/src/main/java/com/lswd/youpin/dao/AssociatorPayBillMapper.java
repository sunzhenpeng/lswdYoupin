package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorPayBillMapperGen;
import com.lswd.youpin.model.AssociatorPayBill;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorPayBillMapper extends AssociatorPayBillMapperGen {

    TotalCountMoney getAssociatorPayBillListCount(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                                  @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate,
                                                  @Param(value = "user")User user, @Param(value = "canteenId")String canteenId);

    List<AssociatorPayBill> getAssociatorPayBillList(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                                     @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate, @Param(value = "user")User user,
                                                     @Param(value = "canteenId")String canteenId,@Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    Integer getAssociatorPayBillListAll(@Param(value = "keyword")String keyword, @Param(value = "canteenIds")List<String> canteenIds,
                                                        @Param(value = "startDate")String startDate, @Param(value = "endDate")String endDate, @Param(value = "user")User user,
                                                        @Param(value = "canteenId")String canteenId);

    AssociatorPayBill getAssociatorByPayBillCardUid(@Param(value = "cardUid")String cardUid);

}