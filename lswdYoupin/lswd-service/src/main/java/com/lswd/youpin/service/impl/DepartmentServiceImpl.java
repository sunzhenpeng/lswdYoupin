package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.DepartmentMapper;
import com.lswd.youpin.dao.lsyp.EmployeeMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Department;
import com.lswd.youpin.model.lsyp.Employee;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhao on 2017/6/5.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * @param department
     * @param user
     * @return
     * @Author liuhao
     * @功能 兴建部门
     */
    @Override
    public LsResponse add(Department department, User user) {
        LsResponse lsResponse = new LsResponse();
        department.setUpdateUser(user.getUsername());
        department.setUpdateTime(Dates.now());
        log.info("{} is being executed. User = {}", "添加部门", JSON.toJSON(user.getUsername() + "准备追加新的部门" + department.getDepartmentName()));
        try {
            if (department != null) {
                int b = departmentMapper.insertDepartment(department);
                if (b > 0) {
                    log.info("{} is being executed. User = {}", "添加部门", JSON.toJSON(user.getUsername() + "追加了新的部门" + department.getDepartmentName()));
                }else{
                    lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT_ADD_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT_NO_ERR.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            log.error("添加部门报错", e.toString());
        }
        return lsResponse;
    }

    /**
     * @param id
     * @param user
     * @return
     * @author liuhao \
     * @功能 删除部门
     */
    @Override
    public LsResponse delete(Integer id, User user) {
        log.info("{} is being executed. User = {}", "删除部门", JSON.toJSON(user.getUsername() + "准备删除ID为" + id + "的部门"));
        LsResponse lsResponse = new LsResponse();
        Department department = new Department();
        try {
            Employee employee = employeeMapper.selectEmployeeByDid(String.valueOf(id));
            if (employee == null) {
                department.setIsDelete(true);
                department.setId(id);
                int b = departmentMapper.deleteById(department);
                if (b > 0) {
                    log.info("{} is being executed. User = {}", "删除部门", JSON.toJSON(user.getUsername() + "删除ID为" + id + "的部门"));
                } else {
                    lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT_DELETE_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT__DELETE_YE_EMP.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            log.error("删除部门出错", e.toString());
        }
        return lsResponse;
    }

    /**
     * @param department
     * @return
     * @author liuhao
     * @功能 修改部门信息
     */
    @Override
    public LsResponse update(Department department, User user) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "修改部门信息", JSON.toJSON(user.getUsername()
                + "准备修改ID为" + department.getId() + "的部门信息"));
        try {
            if (department != null) {
                department.setUpdateUser(user.getUsername());
                department.setUpdateTime(Dates.now());
                int b = departmentMapper.updateById(department);
                if (b > 0) {
                    log.info("{} is being executed. User = {}", "修改部门信息", JSON.toJSON(user.getUsername()
                            + "修改了ID为" + department.getId() + "的部门信息"));
                } else {
                    lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT__UPDATE_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT_NO_ERR.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
    /**
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     * @author liuhao
     * @功能 获取部门列表信息
     */
    @Override
    public LsResponse getDepartments(Integer pageNum, Integer pageSize, String keyword, String institutionId) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        if (keyword != null && !"".equals(keyword)) {
            keyword = "%" + keyword + "%";
        } else {
            keyword = "";
        }

        try {
            int total = departmentMapper.getDepartmentCount(institutionId, keyword);
            List<Department> departments = departmentMapper.selectDepartments(institutionId, offSet, pageSize, keyword);
            if(departments!=null&&departments.size()>0){
            lsResponse.setData(departments);
            lsResponse.setTotalCount(total);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT__NO_SELECT.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
            log.error("获取员工列表出错", e.toString());
        }
        return lsResponse;
    }
    /**
     * @param id
     * @return
     * @author liuhao
     * @功能 查看部门详情
     */
    @Override
    public LsResponse lookDepartment(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            Department department = departmentMapper.selectById(id);
            if (department != null) {
                lsResponse.setData(department);
            } else {
                lsResponse.checkSuccess(false,CodeMessage.DEPARTMENT__NO_HAVE.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
        }
        return lsResponse;
    }
}
