package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.UserMapperGen;
import com.lswd.youpin.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends UserMapperGen {

    User selectByName(@Param("username") String name);

    Integer insertUser(User user);

    Integer updateUserPasswordById(User user);

    User selectUserById(Integer id);

    User getUserByUserId(@Param(value = "userId") String userId);

    Integer getUserCount(@Param(value = "user") User user, @Param(value = "keyword") String keyword);

    List<User> selectUsers(@Param(value = "user") User user, @Param(value = "offSet") Integer offSet,
                           @Param(value = "pageSize") Integer pageSize, @Param(value = "keyword") String keyword);

    Integer getLastUserId();

    //String getCanteenIdsByMachineNo(@Param("machineNo") String machineNo);




}