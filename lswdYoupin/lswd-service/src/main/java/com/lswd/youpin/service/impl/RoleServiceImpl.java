package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.RoleMapper;
import com.lswd.youpin.dao.lsyp.ResourceMapper;
import com.lswd.youpin.dao.lsyp.RoleResourceMapper;
import com.lswd.youpin.model.Role;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/5.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * 修改或者新建角色 ，通过id 是否为null 来判断是执行新建还是修改
     *
     * @param role
     * @return
     */
    @Override
    public LsResponse addOrUpdateRole(Role role, HttpServletRequest request) {
        log.info("{} is being executed. Role = {}", "addOrUpdateRole", JSON.toJSON(role));
        User u = (User) request.getAttribute("user");
        LsResponse lsResponse = new LsResponse();
        boolean b = false;
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setTenantId(u.getTenantId());
        role.setCreateUser(u.getUsername());
        role.setUpdateUser(u.getUsername());
        if (role.getId() == null) {
            try {
                //获取角色表的最大ID号，
                Integer maxId = roleMapper.getMaxId();
                if(maxId==null){
                    maxId=0;
                }
                String prefix = u.getTenantId().substring(0, 4);
                String suffix = String.valueOf(Integer.parseInt("1001") + maxId);
                role.setRoleId(prefix + suffix);
                b = roleMapper.insertSelective(role) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.ROLE_ADD_ERR.name());
                log.error("角色添加失败:{}", e.getMessage());
            }
        } else {
            try {
                b = roleMapper.updateByPrimaryKeySelective(role) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.ROLE_UPDATE_ERR.name());
                log.error("角色更新失败:{}", e.getMessage());
            }
        }
        return lsResponse;
    }

    /**
     * 删除角色，如果当前角色正在被用户使用则不能删除
     *
     * @param id
     * @return
     */
    @Override
    public LsResponse deleteRole(Integer id) {
        log.info("{} is being executed. Role = {}", "deleteRole", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        boolean b = false;
        int count;
        try {
            count = roleMapper.getUsersByRoleId(id);
            if (count > 0) {
                lsResponse.checkSuccess(b, CodeMessage.ROLE_DELETE_LINK_ERR.name());
            } else {
                b = roleMapper.deleteRoleById(id) >= 0;
                lsResponse.setAsSuccess();
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(b, CodeMessage.ROLE_DELETE_ERR.name());
            log.error("角色删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 获取所有的角色列表，实现分页获取角色
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public LsResponse getAllRoles(String keyword, Integer pageNum, Integer pageSize, String tenantId) {
        log.info("{} is being executed. Role = {}", "getAllRoles", JSON.toJSON(keyword));
        LsResponse lsResponse = new LsResponse();
        String name = "";
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        List<Role> roles = new ArrayList<>();
        try {
            if (keyword != null && !(keyword.equals(""))) {
                name = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
            }
            Integer count = roleMapper.getTotalCount(name, tenantId);
            roles = roleMapper.getRoleList(name, offset, pageSize, tenantId);
            lsResponse.setData(roles);
            lsResponse.setTotalCount(count);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.ROLE_SELECT_ERR.name());
            log.error("角色查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 通过角色id 获取对应的角色信息
     *
     * @param id
     * @return
     */
    @Override
    public LsResponse getRoleById(Integer id) {
        log.info("{} is being executed. Role = {}", "getRoleById", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            Role r = roleMapper.selectByPrimaryKey(id);
            lsResponse.setData(r);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.ROLE_SELECT_ERR.name());
            log.error("角色查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 角色授权：1。首先将原有的角色和菜单、菜单和按钮的对应关系删除，然后再添加新的角色和菜单、菜单和按钮的对应关系
     *
     * @param map
     * @return
     */
    @Override
    public LsResponse addRoleResource(Map<String, String> map) {
        log.info("{} is being executed. Role = {}", "addRolePower", JSON.toJSON(map));
        //resource 表中 id 是 23,24,25,26 分别是 商品种类，菜品种类，文件上传，单位，默认所有角色都有这几个权限
        String resources = map.get("resources") + ",23,24,25,26";
        log.info("resources===================" + resources);
        Integer roleId = Integer.parseInt(map.get("roleId"));
        LsResponse lsResponse = new LsResponse();
        String[] result = resources.split(",");
        try {
            //将原先的角色对应关系删除
            roleResourceMapper.deleteLinkByRoleId(roleId);
            //添加新的角色对应关系
            for (String s : result) {
                roleResourceMapper.addRoleRourcesLink(roleId, Integer.parseInt(s));
            }
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false, CodeMessage.ROLE_ADD_ERR.name());
            log.error("角色授权失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRoleResourceByUserId(Integer userId) {
        log.info("{} is being executed. Role = {}", "getRoleResourceByUserId", JSON.toJSON(userId));
        LsResponse lsResponse = new LsResponse();
        try {
            Role roles = roleMapper.getRoleResourcesByUserId(userId);
            lsResponse.setData(roles);
        } catch (Exception e) {
            log.error("查询用户权限失败：{}", e.getMessage());
            lsResponse.setMessage(CodeMessage.ROLE_SELECT_ERR.getMsg());
        }
        return lsResponse;
    }

    /**
     * 根据角色id 获取角色对应的资源权限
     *
     * @param id
     * @return
     */
/*    @Override
    public LsResponse getRolePowerById(Integer id) {
        log.info("{} is being executed. Role = {}", "getRoleResourceById", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            //所有的权限列表
            List<Resources> resourcesList = resourceMapper.getResourceList();
            //用当前角色具有的权限列表
            List<Resources> selectResources = resourceMapper.getResourceByRoleId(id);
            //拼接成树形结构，目前只有两层

            for (Resources r1 : resourcesList) {
                for (Resources r2 : selectResources) {
                    if (r2.getId().intValue() == r1.getId().intValue()) {
                        r1.setFlag(true);
                        break;
                    } else {
                        r1.setFlag(false);
                    }
                }
            }
            List<Resources> result = TreeUtil.getDataToTree(resourcesList);
            lsResponse.setData(result);
        } catch (Exception e) {
            log.error("查询角色对应的权限列表失败：{}", e.getMessage());
            lsResponse.setMessage(CodeMessage.ROLE_SELECT_POWER_ERR.getMsg());
        }
        return lsResponse;
    }*/

    /**
     * 根据当前用户具有的角色级别，查询比当前用户角色级别低的所有角色列表
     *
     * @return
     */
    @Override
    public LsResponse getRolePart(String tenantId) {
        log.info("{} is being executed. Role = {}", "getRolePart", JSON.toJSON(tenantId));
        LsResponse lsResponse = new LsResponse();
        try {
            List<Role> roles = roleMapper.getRolePartList(tenantId);
            lsResponse.setData(roles);
        } catch (Exception e) {
            log.error("获取角色列表失败：{}", e.getMessage());
            lsResponse.setMessage(CodeMessage.ROLE_SELECT_ERR.getMsg());
        }
        return lsResponse;
    }

}
