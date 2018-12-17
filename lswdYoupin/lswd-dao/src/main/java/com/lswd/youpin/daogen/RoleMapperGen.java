package com.lswd.youpin.daogen;

import com.lswd.youpin.model.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapperGen {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}