package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.AppResourceMachineMapperGen;
import com.lswd.youpin.model.lsy.AppResourceMachine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppResourceMachineMapper extends AppResourceMachineMapperGen {

     int insertAppResourceMachineByList(@Param(value = "appResourceMachines") List<AppResourceMachine> appResourceMachines);

     int deleteAppResourceMachineByMachineId(@Param(value = "machineId") Integer machineId);

     Integer getAppResourceMachineCount(@Param(value = "machineId") Integer machineId);

}