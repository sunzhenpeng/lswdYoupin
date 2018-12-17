package com.lswd.youpin.lsy;

import com.lswd.youpin.model.lsy.Employees;
import com.lswd.youpin.response.LsResponse;

public interface EmployeesService {
    LsResponse getEmployeesList(String keyword,Integer positionId,Integer pageNum, Integer pageSize);

    LsResponse getEmployeesById(Integer id);

    LsResponse addOrUpdateEmployees(Employees employees);

    LsResponse delEmployees(Integer id);

}
