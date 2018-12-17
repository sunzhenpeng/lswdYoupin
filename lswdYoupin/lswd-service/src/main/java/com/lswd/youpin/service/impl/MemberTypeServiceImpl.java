package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MemberTypeMapper;
import com.lswd.youpin.dao.lsyp.MembersMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberType;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberTypeService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
@Service
public class MemberTypeServiceImpl implements MemberTypeService {
    private final Logger logger = LoggerFactory.getLogger(MemberTypeServiceImpl.class);

    @Autowired
    private MemberTypeMapper memberTypeMapper;
    @Autowired
    private MembersMapper membersMapper;

    @Override
    public LsResponse getMemberTypeList(String name) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            name = StringsUtil.encodingChange(name);
        } catch (UnsupportedEncodingException e) {
            lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
        }
        try {
            List<MemberType> MemberTypes = memberTypeMapper.getMemberTypeList(name);
            if (MemberTypes.size() > 0 && MemberTypes != null) {
                lsResponse.setData(MemberTypes);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addMemberType(MemberType memberType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<MemberType> memberTypes = memberTypeMapper.getMemberTypeList(memberType.getName());
        if (memberTypes != null && memberTypes.size() > 0) {
            return lsResponse.checkSuccess(false,"该类型已存在");
        }
        memberType.setCreateUser(user.getUsername());
        memberType.setCreateTime(Dates.now());
        memberType.setUpdateUser(user.getUsername());
        memberType.setUpdateTime(Dates.now());
        try {
            int insertFlag = memberTypeMapper.insertSelective(memberType);
            if (insertFlag > 0) {
                lsResponse.setMessage("结算台会员类型新增成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("结算台会员类型新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台会员类型新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateMemberType(MemberType memberType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<MemberType> memberTypes = memberTypeMapper.getMemberTypeList(memberType.getName());
        if (memberTypes != null && memberTypes.size() > 0) {
            lsResponse.setAsFailure();
            lsResponse.setMessage("该类型已存在");
            return lsResponse;
        }
        memberType.setUpdateTime(Dates.now());
        memberType.setUpdateUser(user.getUsername());
        try {
            int updateFlag = memberTypeMapper.updateByPrimaryKeySelective(memberType);
            if (updateFlag > 0) {
                lsResponse.setMessage("结算台会员类型修改成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("结算台会员类型修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台会员类型修改失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteMemberType(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null) {
                return lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
            }
            List<Members> members = membersMapper.getMemberByTypeId(id);
            if (members != null && members.size() > 0) {
                return lsResponse.checkSuccess(false,"该类型还有结算台会员存在，不能删除");
            }
            int deleteFlag = memberTypeMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0) {
                lsResponse.setMessage("结算台会员类型删除成功");
            } else {
                lsResponse.checkSuccess(false,"结算台会员类型删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台会员类型删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }
}
