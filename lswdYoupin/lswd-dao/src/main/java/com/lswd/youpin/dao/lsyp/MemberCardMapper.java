package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MemberCardMapperGen;
import com.lswd.youpin.model.lsyp.MemberCard;
import org.apache.ibatis.annotations.Param;

public interface MemberCardMapper extends MemberCardMapperGen {

    MemberCard getMembersByCardUid(@Param(value = "cardUid")String cardUid);

    Integer getCardidByCardUid(@Param(value = "cardUid")String cardUid);

    Integer updateeMemberCard(MemberCard memberCard);

    /*-----------------------------------下面这两个方法可以合并！！！start------------------------------------*/
    Integer addCardBalance(@Param(value = "memberId")String memberId,@Param(value = "money")Float money);

    Integer subCardBalance(@Param(value = "memberId")String memberId,@Param(value = "money")Float money);
    /*-----------------------------------下面这两个方法可以合并！！！end--------------------------------------*/

    Integer updateBalance(@Param("memberId") String memberId,@Param(value = "money")Float money);

    Float getBalance(@Param("memberId") String memberId);

}