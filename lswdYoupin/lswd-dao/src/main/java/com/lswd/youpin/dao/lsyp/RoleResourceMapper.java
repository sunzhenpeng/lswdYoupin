package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.RoleResourceMapperGen;
import org.apache.ibatis.annotations.Param;

public interface RoleResourceMapper extends RoleResourceMapperGen {

    void addRoleRourcesLink(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId);

    int deleteLinkByRoleId(@Param("roleId") Integer roleId);

  /*  int insertRoleResourceByList(@Param(value = "appResourceMachines") List<RoleResource> roleResources);

    int deleteRoleResourceByRoleId(@Param(value = "roleId") Integer roleId);

    Integer getRoleResourceCount(@Param(value = "roleId") Integer roleId);*/

}