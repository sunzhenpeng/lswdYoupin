package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Employee;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/6.
 */
public interface EmployeeService {
    LsResponse addEmployee(Employee employee, User user);

    LsResponse delete(User user, String employeeId);

    LsResponse updateEmployee(Employee employee, User user);

    LsResponse getEmployees(String regionId, Integer pageNum, Integer pageSize, String keyword, User user);

    LsResponse lookEmployee(Integer id);

    Employee lookEmployeeByEId(String employeeId);

    Employee getEmployeeByCardId(String cardNumber,String username);

    Integer updateTelphone(String data,Associator associator);

}
