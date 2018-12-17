package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.UserLogMapperGen;
import com.lswd.youpin.model.UserLog;

public interface UserLogMapper extends UserLogMapperGen {
    Integer insertUserLog(UserLog userLog);
}