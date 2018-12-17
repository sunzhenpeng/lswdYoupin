package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/6/8.
 */
public interface EmployeeThin {
    LsResponse deleteEmployee(String employeeId, User user);
}
