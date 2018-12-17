package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Machine;
import com.lswd.youpin.response.LsResponse;

public interface MachineService {
    LsResponse getMachineList(User user, String canteenId, String keyword);

    LsResponse getMachineById(Integer id);

    LsResponse addOrUpdateMachine(Machine machine);

    LsResponse getUseByMachineNo(String machineNo);

    LsResponse delMachine(Integer id);

    String getMachineNoByCanteenId(String machineNo, String restaurantId);

}
