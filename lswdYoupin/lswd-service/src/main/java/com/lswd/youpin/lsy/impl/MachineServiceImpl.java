package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.util.Strings;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.MachineMapper;
import com.lswd.youpin.lsy.MachineService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Machine;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {
    private final Logger log = LoggerFactory.getLogger(MachineServiceImpl.class);

    @Autowired
    private MachineMapper machineMapper;

    @Override
    public LsResponse getMachineList(User u,String canteenId, String keyword) {
        log.info("{} is being executed. canteenId = {}", "根据餐厅ID获取设备列表", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();

        try {

            if (keyword != null && !(keyword.equals(""))) {
               // String tmp = URLDecoder.decode(keyword, "UTF-8");
                keyword = new String(keyword.getBytes("iso8859-1"), "UTF-8");
            }else {
                keyword = "";
            }

            List<Machine> machines = new ArrayList<>();

            if (!("").equals(canteenId) && canteenId != null){
               // String tmp = URLDecoder.decode(canteenId, "UTF-8");
                canteenId = new String(keyword.getBytes("iso8859-1"), "UTF-8");
                 machines = machineMapper.getMachineList(canteenId,keyword);

            }else {
                String[] canteenIds = u.getCanteenIds().split(",");
                machines = machineMapper.getMachineListByCanteenIds(canteenIds,keyword);
            }

            log.info("machines==="+ JSON.toJSONString(machines));
            if (machines != null && machines.size() > 0) {
                lsResponse.setData(machines);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.MACHINE_SELECT_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取设备列表出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public String getMachineNoByCanteenId(String machineNo, String restaurantId) {
        if (restaurantId != null && !(restaurantId.equals(""))) {
            //  String tmp = URLDecoder.decode(keyword, "UTF-8" );
            try {
                restaurantId = new String(restaurantId.getBytes("iso8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            machineNo = machineMapper.getMachineNoByCanteenId(restaurantId);
        }
        return machineNo;
    }

    @Override
    public LsResponse getMachineById(Integer id) {
        log.info("{} is being executed. canteenId = {}", "根据餐厅ID获取设备列表", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Machine machine = machineMapper.getMachineById(id);
                log.info("machines===" + JSON.toJSONString(machine));
                if (machine!= null) {
                    lsResponse.setData(machine);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.MACHINE_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取设备列表出错", e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse getUseByMachineNo(String machineNo) {
        log.info("{} is being executed. canteenId = {}", "查询设备是否可用", JSON.toJSON(machineNo));
        LsResponse lsResponse = new LsResponse();

        try {

            if (machineNo != null && !(machineNo.equals(""))) {
                // String tmp = URLDecoder.decode(keyword, "UTF-8");
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo!= null) {
                 int use  = machineMapper.getUseByMachineNo(machineNo);
                 if(use>-1){
                     log.info("use===" + JSON.toJSONString(use));
                     lsResponse.setData(use);

                 }else{
                     lsResponse.checkSuccess(false, CodeMessage.MACHINE_SELECT_ERR.name());
                 }

            }else{
                lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取设备列表出错", e.toString());
        }
        return lsResponse;
    }
    @Override
    public LsResponse addOrUpdateMachine(Machine machine) {
        log.info("{} is being executed. machine = {}", "根据餐厅ID获取设备列表", JSON.toJSON(machine));
        LsResponse lsResponse = new LsResponse();
        machine.setUpdateTime(Dates.now());
        int result;
        if(machine.getId()==null) {
            try {
                String machineId = "";
                try {
                    int re =  machineMapper.getMaxId();
                    if(re>0){
                        machineId = Strings.threeNumberToString("ls", re);
                    }else{
                        machineId = "ls0000";
                    }
                } catch (Exception e) {
                    machineId = "ls0000";
                }
                    machine.setMachineId(machineId);
                  machine.setCreateTime(Dates.now());
                    result = machineMapper.insertSelective(machine);
                    if (result > 0) {
                        lsResponse.setAsSuccess();
                        log.info("{} is being executed. machine = {}", "添加设备", "成功");
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.MACHINE_ADD_ERR.name());
                    }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加设备报错", e.toString());
            }
        }else{
                result = machineMapper.updateByPrimaryKeySelective(machine);
               if (result>0) {
                   lsResponse.setAsSuccess();
               }else{
                   lsResponse.checkSuccess(false, CodeMessage.MACHINE_UPDATE_ERR.name());
               }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delMachine(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            machineMapper.delMachine(id);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.MACHINE_DEL_ERR.getMsg());
            log.error("设备删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}