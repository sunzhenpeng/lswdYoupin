package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.EmployeesMapperGen;
import com.lswd.youpin.model.lsy.Employees;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeesMapper extends EmployeesMapperGen {


    List<Employees> getEmployeesList(@Param(value = "keyword") String keyword,
                                     @Param(value = "positionId") Integer positionId,
                         @Param(value = "pageSize") Integer pageSize,
                         @Param(value = "offset") Integer offset);

    Integer getEmployeesCount(@Param(value = "keyword") String keyword,
                              @Param(value = "positionId") Integer positionId
    );


    Integer delEmployees(@Param(value = "id") Integer id);

    //app-------------------------------


    List<Map<String,Object>> getEmployeesByPositionId(@Param(value = "positionId") Integer positionId
    );

    Map<String,Object> getEmployeeInfo(@Param(value = "id") Integer id
    );

}