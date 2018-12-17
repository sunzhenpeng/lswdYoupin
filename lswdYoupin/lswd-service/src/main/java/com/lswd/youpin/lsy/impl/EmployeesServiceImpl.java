package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.EmployeesMapper;
import com.lswd.youpin.lsy.EmployeesService;
import com.lswd.youpin.model.lsy.Employees;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService {
    private final Logger log = LoggerFactory.getLogger(EmployeesServiceImpl.class);
    @Autowired
    private EmployeesMapper employeesMapper;

    @Override
    public LsResponse getEmployeesList(String keyword,Integer positionId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        try {

            int offSet = 0;
            if (keyword != null&&!"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            } else {
                keyword = "";
            }

            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = employeesMapper.getEmployeesCount(keyword,positionId);

            List<Employees> employees = employeesMapper.getEmployeesList(keyword,positionId, pageSize,offSet);
            log.info("employeess==="+ JSON.toJSONString(employees));
            if (employees != null && employees.size() > 0) {
                lsResponse.setData(employees);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取员工列表出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getEmployeesById(Integer id) {
        log.info("{} is being executed. employeesId = {}", "根据餐厅ID获取员工列表", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Employees employees = employeesMapper.selectByPrimaryKey(id);
                log.info("employeess===" + JSON.toJSONString(employees));
                if (employees!= null) {
                    lsResponse.setData(employees);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取员工列表出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateEmployees(Employees employees) {
        log.info("{} is being executed. employees = {}", "根据餐厅ID获取员工列表", JSON.toJSON(employees));
        LsResponse lsResponse = new LsResponse();
        employees.setUpdateTime(Dates.now());
        int result;
        if(employees.getId()==null) {
            employees.setUpdateTime(Dates.now());
            try {
                employees.setCreateTime(Dates.now());
                result = employeesMapper.insertSelective(employees);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. employees = {}", "添加员工", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.DATA_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加员工报错", e.toString());
            }
        }else{
            employees.setCreateTime(Dates.now());
            employees.setUpdateTime(Dates.now());
         //   employees.setCreateUser(user.getUsername());
          //  employees.setUpdateUser(user.getUsername());
            result = employeesMapper.updateByPrimaryKeySelective(employees);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.DATA_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delEmployees(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = employeesMapper.delEmployees(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.DATA_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.DATA_DEL_ERR.getMsg());
            log.error("员工删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}