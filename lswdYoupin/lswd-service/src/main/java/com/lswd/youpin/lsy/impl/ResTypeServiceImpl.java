package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.ResTypeMapper;
import com.lswd.youpin.lsy.ResTypeService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.ResType;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResTypeServiceImpl implements ResTypeService {
    private final Logger log = LoggerFactory.getLogger(ResTypeServiceImpl.class);
    @Autowired
    private ResTypeMapper resTypeMapper;

    @Override
    public LsResponse getResTypeList(User u, String keyword,String type, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. resTypeId = {}", "根据餐厅ID获取资源类型列表", JSON.toJSON(machineId));
        LsResponse lsResponse = new LsResponse();
        String[] canteenIds =null;
        try {

            if(machineId==null){
                canteenIds = u.getCanteenIds().split(",");
            }

            int offSet = 0;
            if (keyword != null&&!"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            } else {
                keyword = "";
            }

            if (type != null&&!"".equals(type)) {
                type = new String(type.getBytes("iso8859-1"), "utf-8");
            } else {
                type = "";
            }

            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = resTypeMapper.getCount(keyword,type,canteenIds,pageId,machineId);
            log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword===="+ JSON.toJSONString(keyword));
            List<ResType> resTypes = resTypeMapper.getResTypeList(keyword,type,canteenIds,pageId,machineId,pageSize,offSet);
            log.info("resTypes==="+ JSON.toJSONString(resTypes));
            if (resTypes != null && resTypes.size() > 0) {
                lsResponse.setData(resTypes);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.RES_TYPE_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取资源类型出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getResTypeById(Integer id) {
        log.info("{} is being executed. resTypeId = {}", "根据餐厅ID获取资源类型", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                ResType resType = resTypeMapper.selectByPrimaryKey(id);
                log.info("resTypes===" + JSON.toJSONString(resType));
                if (resType!= null) {
                    lsResponse.setData(resType);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.RES_TYPE_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取资源类型出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateResType(ResType resType) {
        log.info("{} is being executed. resType = {}", "根据餐厅ID获取资源类型", JSON.toJSON(resType));
        LsResponse lsResponse = new LsResponse();
        resType.setUpdateTime(Dates.now());
        int result;
        if(resType.getResTypeId()==null) {
            try {
                resType.setCreateTime(Dates.now());
                result = resTypeMapper.insertSelective(resType);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. resType = {}", "添加资源", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.RES_TYPE_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加资源报错", e.toString());
            }
        }else{
            result = resTypeMapper.updateByPrimaryKeySelective(resType);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.RES_TYPE_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delResType(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            resTypeMapper.deleteByPrimaryKey(id);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.RES_TYPE_DEL_ERR.getMsg());
            log.error("资源删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}