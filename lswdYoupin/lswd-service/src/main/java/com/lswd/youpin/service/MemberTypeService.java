package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberType;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
public interface MemberTypeService {

    LsResponse getMemberTypeList(String name);

    LsResponse addMemberType(MemberType MemberType, User user);

    LsResponse updateMemberType(MemberType MemberType, User user);

    LsResponse deleteMemberType(Integer id);
}
