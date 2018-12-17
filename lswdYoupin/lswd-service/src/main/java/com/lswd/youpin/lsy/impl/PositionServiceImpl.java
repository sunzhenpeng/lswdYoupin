package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.PositionMapper;
import com.lswd.youpin.lsy.PositionService;
import com.lswd.youpin.model.lsy.Position;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);
    @Autowired
    private PositionMapper positionMapper;

    @Override
    public LsResponse getPositionList(String keyword,Integer pageNum, Integer pageSize) {
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
            int total = positionMapper.getPositionCount(keyword);

            List<Position> position = positionMapper.getPositionList(keyword,pageSize,offSet);
            log.info("positions==="+ JSON.toJSONString(position));
            if (position != null && position.size() > 0) {
                lsResponse.setData(position);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取岗位管理出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPositionById(Integer id) {
        log.info("{} is being executed. positionId = {}", "根据ID获取岗位管理", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Position position = positionMapper.selectByPrimaryKey(id);
                log.info("positions===" + JSON.toJSONString(position));
                if (position!= null) {
                    lsResponse.setData(position);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取岗位管理出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdatePosition(Position position) {
        log.info("{} is being executed. position = {}", "根据ID获取岗位管理", JSON.toJSON(position));
        LsResponse lsResponse = new LsResponse();
        position.setUpdateTime(Dates.now());
        int result;
        if(position.getId()==null) {
            position.setUpdateTime(Dates.now());
            try {
                result = positionMapper.insertSelective(position);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. position = {}", "添加员工", "成功");
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
            position.setUpdateTime(Dates.now());
         //   position.setCreateUser(user.getUsername());
          //  position.setUpdateUser(user.getUsername());
            result = positionMapper.updateByPrimaryKeySelective(position);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.DATA_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delPosition(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = positionMapper.delPosition(id);
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