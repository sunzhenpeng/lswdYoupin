package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.MachineMapperGen;
import com.lswd.youpin.model.lsy.Machine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MachineMapper extends MachineMapperGen {
    List<Machine> getMachineList(@Param(value = "canteenId") String canteenId,
                                 @Param(value = "keyword") String keyword
    );

    List<Machine> getMachineListByCanteenIds(@Param(value = "canteenIds") String[] canteenIds, @Param(value = "keyword") String keyword);

    Integer getIdByMachineNo(@Param(value = "machineNo") String machineNo);

    Machine getMachineById(@Param(value = "id") Integer id);

    Integer getMaxId();

    Integer delMachine(Integer id);

    //--------------app端

    Integer getUseByMachineNo(@Param(value = "machineNo") String machineNo);

    String getTitleByMachineNo(@Param(value = "machineNo") String machineNo);

    String getCanteenIdByMachineNo(@Param(value = "machineNo") String machineNo);

    String getMachineNoByCanteenId(@Param(value = "canteenId") String canteenId
    );



}