package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.EmployeeMapperGen;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.lsyp.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper extends EmployeeMapperGen {

    Employee selectEmployeeByDid(@Param(value ="id") String id);

    Integer insertEmployee(Employee employee);

    Employee selectEmployeeByEid(@Param(value = "employeeId") String employeeId);

    Integer deleteById(Employee employee);

    Integer updateEmployeeById(Employee employee);

    Integer getEmployeeCount(@Param(value = "regionId") String regionId, @Param(value = "keyword") String keyword);

    List<Employee> selectEmployees(@Param(value = "regionId") String regionId, @Param(value = "offSet") Integer offSet,
                                   @Param(value = "pageSize") Integer pageSize, @Param(value = "keyword") String keyword);

    Employee selectById(@Param(value = "id") Integer id);

    Integer getMaxEmployeeID();

    Integer selectLastId();


    List<Employee> getEmployessAll(@Param(value = "regions") String[] regions); //查询所有的员工，导出用！

    Employee selectByCardId(@Param(value = "cardNumber") String cardNumber,@Param(value = "username") String username);

    Employee selectByPhone(@Param(value = "phone") String phone);
}