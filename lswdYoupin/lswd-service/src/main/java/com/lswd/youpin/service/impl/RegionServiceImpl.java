package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.RegionMapper;
import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RegionService;
import com.lswd.youpin.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by H61M-K on 2017/7/17.
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

    @Override
    public LsResponse addOrUpdateRegion(Region region,User u) {
        log.info("{} is being executed. Region = {}", "addOrUpdateRegion", JSON.toJSON(region));
        LsResponse lsResponse = new LsResponse();
        boolean b = false;
        region.setCreateTime(new Date());
        region.setUpdateTime(new Date());
        if (region.getId() == null) {
            try {
                Integer maxId = regionMapper.getMaxId();
                if(maxId==null){
                    maxId=0;
                }
                String prefix = u.getTenantId().substring(0, 4);
                String suffix = String.valueOf(Integer.parseInt("1001") + maxId);
                region.setRegionId(prefix+suffix);
                b = regionMapper.insertSelective(region) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.REGION_ADD_ERR.name());
                log.error("园区添加失败:", e.getMessage());
            }
        } else {
            try {
                b = regionMapper.updateByPrimaryKeySelective(region) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.REGION_UPDATE_ERR.name());
                log.error("园区更新失败:", e.getMessage());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAllRegion(String keyword, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. Region = {}", "getAllRegion", JSON.toJSON(keyword));
        LsResponse lsResponse = new LsResponse();
        String name = "";
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        List<Region> regions = new ArrayList<Region>();
        try {
            if (keyword != null && !(keyword.equals(""))) {
                name = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
            }
            Integer count = regionMapper.getTotalCount(name);
            regions = regionMapper.getRegionList(name, offset, pageSize);
            lsResponse.setData(regions);
            lsResponse.setTotalCount(count);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.REGION_SELECT_ERR.name());
            log.error("园区查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRegionById(Integer id) {
        log.info("{} is being executed. Region = {}", "getRegionById", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            Region region = regionMapper.selectByPrimaryKey(id);
            lsResponse.setData(region);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.REGION_SELECT_ERR.name());
            log.error("园区查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRegionAll() {
        LsResponse lsResponse=new LsResponse();
        try {
            List<Region>regions=regionMapper.selectRegions();
            lsResponse.setData(regions);
        } catch (Exception e) {
            log.error("园区查询失败：{}", e.getMessage());
            lsResponse.checkSuccess(false, CodeMessage.REGION_SELECT_ERR.name());
        }
        return lsResponse;
    }
}
