package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberCard;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import io.swagger.models.auth.In;

import java.lang.reflect.Member;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
public interface MembersService {

    LsResponse getMembersList(User user, String keyword, Integer pageNum, Integer pageSize, String canteenId, Integer typeId);

    LsResponse existCardUID(User user);

    LsResponse addMembersWeb(Members members, User user);

    LsResponse updateMembersWeb(Members members, User user);

    LsResponse deleteMembersWeb(Integer id);

    LsResponse getMembersInfoWeb(Integer id, User user);

    LsResponse giveMembersRecharge(String memberId, Float money, User user);

    LsResponse refundMembersMoney(String memberId, Float money, User user);

    LsResponse getMembersByUID(User user);

    LsResponse updateMemberStatus(Integer id, Boolean flag);

    Members getMemberByPhone(String phone,String username);

    /*---------------会员信息同步，用到的 start-----------------*/
    Integer deleteMembersAll();

    Integer deleteMemberCardAll();

    Integer insertMemberCard(MemberCard memberCard);

    Integer insertMember(Members members);

    /*---------------会员信息同步，用到的 end-----------------*/


}
