package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.RoleMapperGen;
import com.lswd.youpin.model.Role;
import com.lswd.youpin.model.vo.Nodes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends RoleMapperGen {
    Integer getUsersByRoleId(@Param("roleId") Integer id);

    Integer deleteRoleById(@Param("id") Integer id);

    Integer getTotalCount(@Param("roleName") String roleName,@Param("tenantId") String tenantId);

    List<Role> getRoleList(@Param("roleName") String roleName, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize,@Param("tenantId") String tenantId);

    Role getRoleResourcesByUserId(@Param("userId") Integer userId);

    List<Role> getRolePartList(@Param("tenantId") String tenantId);

    Integer getMaxId();

    List<Nodes> getAllResources(@Param("roleId")Integer roleId);

    List<Nodes> getResource(@Param("roleId")Integer roleId,@Param("resourceType")Integer resourceType,@Param("level")Integer level);

}