package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Department;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/5.
 */
public interface DepartmentService {
    LsResponse add(Department department, User user);

    LsResponse delete(Integer id, User user);

    LsResponse update(Department department,User user);

    LsResponse getDepartments(Integer pageNum, Integer pageSize, String keyword,String institutionId);

    LsResponse lookDepartment(Integer id);
}
