package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.CounterTypeMapper;
import com.lswd.youpin.dao.lsyp.CounterTypeRecipeCategoryMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterType;
import com.lswd.youpin.model.lsyp.CounterTypeRecipeCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/11/15.
 */
@Service
public class CounterTypeServiceImpl implements CounterTypeService{
    private final Logger logger = LoggerFactory.getLogger(CounterTypeServiceImpl.class);
    @Autowired
    private CounterTypeMapper counterTypeMapper;
    @Autowired
    private CounterTypeRecipeCategoryMapper counterTypeRecipeCategoryMapper;

    @Override
    public LsResponse getCounterTypeList(String name) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
            if (!("").equals(name) && name != null){
                name = new String(name.getBytes("utf-8"),"utf-8");
//                name = new String(name.getBytes("iso8859-1"),"utf-8");
            }else {
                name = "";
            }
        }catch (UnsupportedEncodingException e){
            logger.error("查看吧台类型列表失败，失败原因为：" + CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try{
            List<CounterType> counterTypes= counterTypeMapper.getCounterTypeList(name);
            if (counterTypes.size() > 0 && counterTypes != null){
                lsResponse.setData(counterTypes);
            }else {
                lsResponse.setAsFailure();
                lsResponse.setData(CodeMessage.EMPTY_DATA.name());
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("查看吧台类型列表失败，失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterType(CounterType counterType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        counterType.setCreateUser(user.getUsername());
        counterType.setCreateTime(Dates.now());
        try {
            int insertFlag = counterTypeMapper.insertSelective(counterType);
            if (insertFlag > 0){
                lsResponse.setMessage("吧台类型新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台类型新增失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类型新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterType(CounterType counterType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        counterType.setUpdateUser(user.getUsername());
        counterType.setUpdateTime(Dates.now());
        try {
            int updateFlag = counterTypeMapper.updateByPrimaryKeySelective(counterType);
            if (updateFlag > 0){
                lsResponse.setMessage("吧台类型修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台类型修改失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类型修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterType(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null){
                return lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = counterTypeMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0){
                lsResponse.setMessage("吧台类型删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台类型删除失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类型删除失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterTypeRecipeCateList(Integer counterTypeId,String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
            if (!("").equals(canteenId) && canteenId != null){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else {
                canteenId = "";
            }
        }catch (UnsupportedEncodingException e){
            logger.error("查看吧台类型绑定菜品类型列表失败，失败原因为：" + CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
        }
        try{
            List<CounterTypeRecipeCategory> counterTypes = counterTypeRecipeCategoryMapper.getCounterTypeRecipeCateList(counterTypeId,canteenId);
            if (counterTypes.size() > 0 && counterTypes != null){
                lsResponse.setData(counterTypes);
            }else {
                lsResponse.setAsFailure();
                lsResponse.setData(CodeMessage.EMPTY_DATA.getMsg());
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.getMsg());
            logger.error("查看吧台类型绑定菜品类型列表失败，失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse bindingCounterTypeRecipeCate(CounterTypeRecipeCategory counterTypeRecipeCategory,User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        counterTypeRecipeCategory.setCreateUser(user.getUsername());
        counterTypeRecipeCategory.setCreateTime(Dates.now());
        try {
            if (counterTypeRecipeCategory.getCounterTypeId() != null && counterTypeRecipeCategory.getRecipeCategoryId() != null){
                int insertFlag = counterTypeRecipeCategoryMapper.insertSelective(counterTypeRecipeCategory);
                if (insertFlag > 0){
                    lsResponse.setMessage("绑定成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("绑定失败");
                }
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.getMsg());
            logger.error("吧台类型绑定菜品类型失败"+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterTypeRecipeCate(Integer counterTypeId,Integer recipeCategoryId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (counterTypeId != null && recipeCategoryId != null){
                int deleteFlag = counterTypeRecipeCategoryMapper.deleteCounterTypeRecipeCate(counterTypeId,recipeCategoryId);
                if (deleteFlag > 0){
                    lsResponse.setMessage("删除成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("绑定失败");
                }
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.getMsg());
            logger.error("吧台类型绑定菜品类型,删除失败"+e.toString());
        }
        return lsResponse;
    }
}
