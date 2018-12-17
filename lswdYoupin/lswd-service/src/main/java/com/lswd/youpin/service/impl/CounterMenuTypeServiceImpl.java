package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MenuTypeMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MenuType;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterMenuTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/11/24.
 */
@Service
public class CounterMenuTypeServiceImpl implements CounterMenuTypeService{
    private  final Logger logger = LoggerFactory.getLogger(CounterMenuTypeServiceImpl.class);
    @Autowired
    private MenuTypeMapper menuTypeMapper;

    @Override
    public LsResponse getcounterMenuTypeList(String name, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offSet = 0;
        Integer total = 0;
        try{
            if (pageNum != null && pageSize != null){
                offSet = (pageNum - 1) * pageSize;
            }
            if (!("").equals(name) && name != null){
                name = new String(name.getBytes("utf-8"),"utf-8");
//                name = new String(name.getBytes("iso8859-1"),"utf-8");
            }else {
                name = "";
            }
        }catch (UnsupportedEncodingException e){
            logger.error("查看吧台菜品类型列表失败，失败原因为：" + CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try{
            total = menuTypeMapper.getcounterMenuTypeListCount(name);
            List<MenuType> menuTypes= menuTypeMapper.getcounterMenuTypeList(name,offSet,pageSize);
            if (menuTypes.size() > 0 && menuTypes != null){
                lsResponse.setTotalCount(total);
                lsResponse.setData(menuTypes);
            }else {
                lsResponse.checkSuccess(false,CodeMessage.EMPTY_DATA.name());
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("查看吧台类型列表失败，失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterMenuType(MenuType menuType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        menuType.setCreateUser(user.getUsername());
        menuType.setCreateTime(Dates.now());
        menuType.setUpdateUser(user.getUsername());
        menuType.setUpdateTime(Dates.now());
        try {
            int insertFlag = menuTypeMapper.insertSelective(menuType);
            if (insertFlag > 0){
                lsResponse.setMessage("吧台菜品类型新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品类型新增失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类菜品型新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterMenuType(MenuType menuType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        menuType.setUpdateUser(user.getUsername());
        menuType.setUpdateTime(Dates.now());
        try {
            int updateFlag = menuTypeMapper.updateByPrimaryKeySelective(menuType);
            if (updateFlag > 0){
                lsResponse.setMessage("吧台菜品类型修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品类型修改失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类菜品型修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterMenuType(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null){
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = menuTypeMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0){
                lsResponse.setMessage("吧台菜品类型删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品类型删除失败");
            }
        }catch (Exception e){
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台类菜品型删除失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }
}
