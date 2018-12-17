package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MemberPayBillMapperGen;
import com.lswd.youpin.model.lsyp.MemberPayBill;
import com.lswd.youpin.model.vo.TotalCountMoney;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberPayBillMapper extends MemberPayBillMapperGen {

    TotalCountMoney getMemberPayBillListCount(@Param(value = "keyword")String keyword, @Param(value = "startDate")String startDate,
                                              @Param(value = "endDate")String endDate, @Param(value = "canteenId")String canteenId,@Param(value = "canteenIds")String[] canteenIds,
                                              @Param(value = "payType")Integer payType);

    List<MemberPayBill> getMemberPayBillList(@Param(value = "keyword")String keyword, @Param(value = "startDate")String startDate,
                                             @Param(value = "endDate")String endDate, @Param(value = "canteenId")String canteenId,@Param(value = "canteenIds")String[] canteenIds,
                                             @Param(value = "payType")Integer payType,
                                             @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

}