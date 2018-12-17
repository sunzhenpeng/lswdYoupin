package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.AppResourceMachineMapper;
import com.lswd.youpin.lsy.AppResourceMachineService;
import com.lswd.youpin.model.lsy.AppResourceMachine;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppResourceMachineServiceImpl implements AppResourceMachineService {
    private final Logger log = LoggerFactory.getLogger(MachineServiceImpl.class);
    @Autowired
    AppResourceMachineMapper appResourceMachineMapper;
    @Override
    public LsResponse updateAppResourceMachine(String resourceIds, Integer machineId) {
        log.info("{} is being executed. appResource = {}", "更新设备菜单列表", JSON.toJSON(machineId));
        LsResponse lsResponse = new LsResponse();

        int result =0;
        Integer count;
        int b;
        if(resourceIds.length()>0) {
            try {

                count = appResourceMachineMapper.getAppResourceMachineCount(machineId);
                if (count>0) {
                    result = appResourceMachineMapper.deleteAppResourceMachineByMachineId(machineId);
                }
                    log.info("{} is being executedd. appResource = {}", "删除绑定菜单成功", "成功");
                    String[] ids = resourceIds.split(",");
                    ArrayList<AppResourceMachine> appResourceMachines = new ArrayList<>();
                    for (int i=0;i<ids.length;i++) {
                        AppResourceMachine appRe = new AppResourceMachine();
                        appRe.setResouceId(Integer.parseInt(ids[i]));
                        appRe.setMachineId(machineId);
                        appResourceMachines.add(appRe);
                    }
                    b = appResourceMachineMapper.insertAppResourceMachineByList(appResourceMachines);
                    if (b>0) {
                        lsResponse.setAsSuccess();
                        lsResponse.setMessage(CodeMessage.APP_RESOURCE_MACHINE_UPDATE_SUCCESS.name());
                   }else{
                        lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_MACHINE_UPDATE_ERR.name());
                    }

            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加绑定菜单报错", e.toString());
            }
        }else{
            lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_MACHINE_NO_ERR.name());
        }
        return lsResponse;
    }
}
