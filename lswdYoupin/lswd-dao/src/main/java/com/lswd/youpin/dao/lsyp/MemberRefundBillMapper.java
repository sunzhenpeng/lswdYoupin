package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MemberRefundBillMapperGen;
import com.lswd.youpin.model.lsyp.MemberRefundBill;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberRefundBillMapper extends MemberRefundBillMapperGen {

    TotalCountMoney getMemberRefundBillListCount(@Param(value = "keyword")String keyword, @Param(value = "startDate")String startDate,
                                              @Param(value = "endDate")String endDate, @Param(value = "canteenId")String canteenId, @Param(value = "canteenIds")String[] canteenIds);

    List<MemberRefundBill> getMemberRefundBillList(@Param(value = "keyword")String keyword, @Param(value = "startDate")String startDate,
                                                   @Param(value = "endDate")String endDate, @Param(value = "canteenId")String canteenId, @Param(value = "canteenIds")String[] canteenIds,
                                                   @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

}