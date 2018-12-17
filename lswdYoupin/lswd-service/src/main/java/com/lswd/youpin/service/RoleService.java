package com.lswd.youpin.service;

import com.lswd.youpin.model.Role;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/5.
 */
public interface RoleService {

    LsResponse addOrUpdateRole(Role role, HttpServletRequest request);

    LsResponse deleteRole(Integer id);

    LsResponse getAllRoles(String keyword, Integer pageNum, Integer pageSize,String tenantId);

    LsResponse getRoleById(Integer id);

    LsResponse addRoleResource(Map<String, String> map);

    LsResponse getRoleResourceByUserId(Integer userId);

    /*LsResponse getRolePowerById(Integer id);*/

    LsResponse getRolePart(String tenantId);

}
