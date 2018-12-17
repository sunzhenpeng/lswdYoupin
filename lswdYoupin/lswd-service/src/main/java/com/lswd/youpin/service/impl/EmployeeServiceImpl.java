package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.RegionMapper;
import com.lswd.youpin.dao.lsyp.EmployeeMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Employee;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.EmployeeService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/6/6.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RegionMapper regionMapper;

    /**
     * @param employee
     * @param user
     * @return
     * @author liuhao
     * @功能 添加员工信息
     */
    @Override
    public LsResponse addEmployee(Employee employee, User user) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "添加新员工", JSON.toJSON(user.getUsername() + "准备追加新的员工" + employee.getEmployeeName()));
        try {
            if (employee != null) {
                employee.setCreateTime(Dates.now());
                employee.setUpdateTime(Dates.now());
                employee.setUpdateUser(user.getUsername());
                Integer id=employeeMapper.selectLastId();
                if(id==null)
                {
                    id=0;
                }
                employee.setEmployeeId("e"+String.valueOf(100001+id));
                Integer b = employeeMapper.insertEmployee(employee);
                if (b!=null&&b > 0) {
                    log.info("{} is being executed. User = {}", "添加新员工", JSON.toJSON(user.getUsername()+ "追加了新的员工" + employee.getEmployeeName()));
                } else {
                    lsResponse.checkSuccess(false,CodeMessage.EMPLOYEE_ADD_ERR.name());
                }
                }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加员工错误", e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    /**
     * @param user
     * @param employeeId
     * @return
     * @author liuhao
     * @功能 删除员工
     */
    @Override
    public LsResponse delete(User user, String employeeId) {
        log.info("{} is being executed. User = {}", "删除员工", JSON.toJSON(user.getUsername() + "准备删除编号为" + employeeId + "的员工"));
        LsResponse lsResponse = new LsResponse();
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setIsDelete(true);
        employee.setUpdateUser(user.getUsername());
        employee.setUpdateTime(Dates.now());
        try {
            Integer b = employeeMapper.deleteById(employee);
            if (b!=null&&b > 0) {
                log.info("{} is being executed. User = {}", "删除员工", JSON.toJSON(user.getUsername() + "删除编号为" + employeeId + "的员工"));
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPLOYEE_DELETE_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("删除员工错误", e.toString());
        }
        return lsResponse;
    }

    /**
     * @param employee
     * @param user
     * @return
     * @author liuhao
     * @功能 修改员工信息
     */
    @Override
    public LsResponse updateEmployee(Employee employee, User user) {
        log.info("{} is being executed. User = {}", "修改员工信息", JSON.toJSON(user.getUsername() + "准备修改编号为" + employee.getEmployeeId() + "的员工信息"));
        LsResponse lsResponse = new LsResponse();
        try {
            if (employee != null) {
                employee.setUpdateTime(Dates.now());
                employee.setUpdateUser(user.getUsername());
                Integer b = employeeMapper.updateEmployeeById(employee);
                if (b!=null&&b > 0) {
                    log.info("{} is being executed. User = {}", "修改员工信息", JSON.toJSON(user.getUsername() + "修改了编号为" + employee.getEmployeeId() + "的员工信息"));
                } else {
                    lsResponse.checkSuccess(false,CodeMessage.EMPLOYEE_UPDATE_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false,CodeMessage.EMPLOYEE_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("修改员工信息错误", e.toString());
        }
        return lsResponse;
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param user
     * @return
     * @author liuhao
     * @功能 员工列表
     */
    @Override
    public LsResponse getEmployees(String regionId, Integer pageNum, Integer pageSize, String keyword, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if(regionId==null)
            {
                regionId="";
            }
            Integer total = employeeMapper.getEmployeeCount(regionId, keyword);
            List<Employee> employees = employeeMapper.selectEmployees(regionId, offSet, pageSize, keyword);
            if(employees!=null&&employees.size()>0){
                Map<String, Object> map = new HashedMap();
                map.put("employees", employees);
                lsResponse.setData(map);
                lsResponse.setTotalCount(total);
            }else{
                lsResponse.checkSuccess(false,CodeMessage.EMPLOYEE_NO_SELECT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            e.printStackTrace();
        }

        return lsResponse;
    }

    /**
     * @param id
     * @return
     * @author liuhao
     * @功能 员工详情
     */
    @Override
    public LsResponse lookEmployee(Integer id) {

        LsResponse lsResponse = new LsResponse();
        try {
            Employee employee = employeeMapper.selectById(id);
            if (employee != null) {
                lsResponse.setData(employee);
            } else {
                lsResponse.checkSuccess(false,CodeMessage.EMPLOYEE_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("查看员工详情报错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public Employee lookEmployeeByEId(String employeeId) {
        Employee employee= null;
        try {
            employee = employeeMapper.selectEmployeeByEid(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据员工编号查找员工信息报错", e.toString());
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByCardId(String cardNumber,String username) {
        return employeeMapper.selectByCardId(cardNumber,username);
    }

    @Override
    public Integer updateTelphone(String data,Associator associator) {
        JSONObject jsonObject = JSONObject.parseObject(data);//解析json参数
        String tel = jsonObject.get("tel").toString();
        String phone = associator.getTelephone();
        Integer b=0;
        Employee employee = employeeMapper.selectByPhone(phone);
        if (employee != null) {
            employee.setTelephone(tel);
            b = employeeMapper.updateEmployeeById(employee);
            if(b==null)
            {
                b=0;
            }
        }
        return b;
    }
}
