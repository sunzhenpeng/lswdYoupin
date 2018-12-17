package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.CounterUserMapperGen;
import com.lswd.youpin.model.CounterUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CounterUserMapper extends CounterUserMapperGen {

    List<CounterUser> getCounterUserListByCounterId(@Param(value = "counterId") String counterId);

    Integer getCounterUserListCount(@Param(value = "keyword") String keyword);

    List<CounterUser> getCounterUserList(@Param(value = "keyword") String keyword, @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    Integer getMAXID();

    Integer deleteByPrimaryKeyUpdate(@Param(value = "id")Integer id);

    Integer updateCounterUserStatus(@Param(value = "id")Integer id,@Param(value = "status")Integer status);

    CounterUser loginCounterUserBT(@Param(value = "username")String username,@Param(value = "password")String password);

    List<CounterUser> getBingCounterUserList(@Param(value = "keyword") String keyword);

    CounterUser getCounterUserByUserId(@Param(value = "userId")String userId);

    Integer resetCounterUserPass(@Param(value = "id")Integer id,@Param(value = "pass")String  pass,@Param(value = "updateUser")String updateUser,@Param(value = "updateTime")Date updateTime);

    Integer updateCounterUserPass(@Param(value = "userId")String userId,@Param(value = "pass")String pass);

    CounterUser getCounterUserByName(@Param(value = "username")String username);
}