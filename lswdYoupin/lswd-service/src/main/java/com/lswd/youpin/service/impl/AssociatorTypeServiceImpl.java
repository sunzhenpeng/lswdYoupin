package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.AssociatorMapper;
import com.lswd.youpin.dao.AssociatorTypeMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorType;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorTypeService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class AssociatorTypeServiceImpl implements AssociatorTypeService{
    private final Logger log = LoggerFactory.getLogger(AssociatorTypeService.class);
    @Autowired
    private AssociatorTypeMapper associatorTypeMapper;
    @Autowired
    private AssociatorMapper associatorMapper;

    @Override
    public LsResponse getAssociatorTypeList(String name) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
           name= StringsUtil.encodingChange(name);
        }catch (UnsupportedEncodingException e){
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
        }
        List<AssociatorType> associatorTypes= associatorTypeMapper.getAssociatorTypeList(name);
        if (associatorTypes.size() > 0 && associatorTypes != null){
            lsResponse.setData(associatorTypes);
        }else {
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.EMPTY_DATA.getMsg());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addAssociatorType(AssociatorType associatorType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<AssociatorType> associatorTypes = associatorTypeMapper.getAssociatorTypeList(associatorType.getName());
        if (associatorTypes != null && associatorTypes.size() >0){
            lsResponse.setAsFailure();
            lsResponse.setMessage("该类型已存在");
            return lsResponse;
        }
        associatorType.setCreateUser(user.getUsername());
        associatorType.setCreateTime(Dates.now());
        try {
            int insertFlag = associatorTypeMapper.insertSelective(associatorType);
            if (insertFlag > 0){
                lsResponse.setMessage("会员类型新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("会员类型新增失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("会员类型新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAssociatorType(AssociatorType associatorType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<AssociatorType> associatorTypes = associatorTypeMapper.getAssociatorTypeList(associatorType.getName());
        if (associatorTypes != null && associatorTypes.size() >0){
            lsResponse.setAsFailure();
            lsResponse.setMessage("该类型已存在");
            return lsResponse;
        }
        associatorType.setUpdateTime(Dates.now());
        associatorType.setUpdateUser(user.getUsername());
        try {
            int updateFlag = associatorTypeMapper.updateByPrimaryKeySelective(associatorType);
            if (updateFlag > 0){
                lsResponse.setMessage("会员类型修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("会员类型修改失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("会员类型修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }
}
