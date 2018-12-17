package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.DepartmentMapperGen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper extends DepartmentMapperGen {

    int insertDepartment(Department department);

    int deleteById(Department department);

    int updateById(Department department);

    int getDepartmentCount(@Param(value = "institutionId")String institutionId,  @Param(value = "keyword")String keyword);

    List<Department> selectDepartments(@Param(value = "institutionId") String institutionId, @Param(value = "offSet")int offSet,
                                       @Param(value = "pageSize") Integer pageSize,  @Param(value = "keyword")String keyword);

    Department selectById(@Param(value = "id")Integer id);

    Department getDepartmentByDepartmentID(@Param(value = "departmentID")String departmentID);
    List<Department> getDepartmentAll();
    Department getDepartmentByDepartmentName(@Param(value = "departmentName")String departmentName);
}