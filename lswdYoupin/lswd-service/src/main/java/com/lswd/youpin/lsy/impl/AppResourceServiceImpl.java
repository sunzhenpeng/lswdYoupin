package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.AppResourceMapper;
import com.lswd.youpin.lsy.AppResourceService;
import com.lswd.youpin.model.lsy.AppResource;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppResourceServiceImpl implements AppResourceService {
    private final Logger log = LoggerFactory.getLogger(MachineServiceImpl.class);
    @Autowired
    AppResourceMapper appResourceMapper;
    @Override
    public LsResponse getAppResourceList(Integer machineId, String keyword) {
        log.info("{} is being executed. machineId = {}", "根据餐厅ID获取菜单列表", JSON.toJSON(machineId));
        LsResponse lsResponse = new LsResponse();
        try {

            if (!("").equals(keyword) && keyword != null){
                keyword = new String(keyword.getBytes("iso8859-1"), "UTF-8");
            }else {
                keyword = "";
            }

                log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword===="+ JSON.toJSONString(keyword));
            List<AppResource> appReources = appResourceMapper.getAppResourceByMachineId(keyword,machineId);
            log.info("appReources==="+ JSON.toJSONString(appReources));
            if (appReources != null && appReources.size() > 0) {
                lsResponse.setData(appReources);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取菜单列表出错", e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse getAppResourceById(Integer id) {

        log.info("{} is being executed. appResourceId = {}", "根据ID获取菜单列表", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                AppResource appResource = appResourceMapper.selectByPrimaryKey(id);
                log.info("appResources===" + JSON.toJSONString(appResource));
                if (appResource != null) {
                    lsResponse.setData(appResource);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_NO_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取菜单列表出错", e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse getAppResourceByMachineNo(String machineNo) {
        log.info("{} is being executed. machineId = {}", "根据餐厅ID获取菜单列表", JSON.toJSON(machineNo));
        LsResponse lsResponse = new LsResponse();
        try {
            if (!("").equals(machineNo) && machineNo != null){
                machineNo = new String(machineNo.getBytes("iso8859-1"),"utf-8");
            }else {
                machineNo = "";
            }
            log.info("machineId==="+ JSON.toJSONString(machineNo)+"keyword===="+ JSON.toJSONString(machineNo));
            List<Map<String, Object>> appReources = appResourceMapper.getAppResourceByMachineNo(machineNo);
            log.info("appReources==="+ JSON.toJSONString(appReources));
            if (appReources != null && appReources.size() > 0) {
                lsResponse.setData(appReources);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取菜单列表出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateAppResource(AppResource appResource) {
        log.info("{} is being executed. appResource = {}", "根据餐厅ID获取菜单列表", JSON.toJSON(appResource));
        LsResponse lsResponse = new LsResponse();
        appResource.setUpdateTime(Dates.now());
        int result;
        if(appResource.getId()==null) {
            try {
                appResource.setCreateTime(Dates.now());
                result = appResourceMapper.insertSelective(appResource);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. appResource = {}", "添加菜单", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加菜单报错", e.toString());
            }
        }else{
            result = appResourceMapper.updateByPrimaryKeySelective(appResource);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.APP_RESOURCE_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }



    @Override
    public LsResponse delAppResource(Integer id) {

        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            appResourceMapper.delAppResource(id);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.MACHINE_DEL_ERR.getMsg());
            log.error("菜单删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }
}
