package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.EmployeeThin;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liuhao on 2017/6/8.
 */
@Component
public class EmployeeThinImpl implements EmployeeThin{

    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse deleteEmployee(String employeeId, User user) {
        LsResponse lsResponse  = employeeService.delete(user,employeeId);
        return lsResponse;
    }

}
