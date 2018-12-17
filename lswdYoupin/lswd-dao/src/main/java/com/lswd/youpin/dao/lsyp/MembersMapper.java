package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.MembersMapperGen;
import com.lswd.youpin.model.lsyp.MemberCard;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.model.lsyp.MenuType;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Member;
import java.util.List;

public interface MembersMapper extends MembersMapperGen {

    List<Members> getMemberByTypeId(@Param(value = "typeId") Integer typeId);

    Integer getMembersListCount(@Param(value = "keyword") String keyword, @Param(value = "typeId") Integer typeId,
                                @Param(value = "canteenId") String canteenId, @Param(value = "canteenIds") String[] canteenIds);

    List<Members> getMembersList(@Param(value = "keyword") String keyword, @Param(value = "typeId") Integer typeId,
                                 @Param(value = "canteenId") String canteenId, @Param(value = "canteenIds") String[] canteenIds,
                                 @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    Members getBalanceById(@Param(value = "id") Integer id);

    Members getBalanceByMemberId(@Param(value = "memberId") String memberId);

    Integer deleteByPrimaryKeyUpdate(@Param(value = "id") Integer id);

    Members getMembersByUID(@Param(value = "cardUID") String cardUID);

    Integer getMembersMaxId();

    Integer updateMemberStatus(Members members);

    Members getMemberByPhoneAndUserName(@Param(value = "phone") String phone,@Param(value = "username") String username);


    Integer getMemberListBTCount(@Param(value = "memberName") String memberName,
                                 @Param(value = "memberTel") String memberTel,
                                 @Param(value = "memberCardUid") String memberCardUid,
                                 @Param(value = "typeId") Integer typeId,
                                 @Param(value = "canteenId") String canteenId);

    List<Members> getMemberListBT(@Param(value = "memberName") String memberName,
                                  @Param(value = "memberTel") String memberTel,
                                  @Param(value = "memberCardUid") String memberCardUid,
                                  @Param(value = "typeId") Integer typeId,
                                  @Param(value = "canteenId") String canteenId,
                                  @Param(value = "offSet") Integer offSet,
                                  @Param(value = "pageSize") Integer pageSize);

    Float getBalance(@Param(value = "memberId")String memberId);

    /*---------------会员信息同步，用到的 start-----------------*/
    Integer deleteMembersAll();
    Integer deleteMemberCardAll();
    Integer insertMemberCard(MemberCard memberCard);
    Integer insertMember(Members members);
    /*---------------会员信息同步，用到的 end-----------------*/



}