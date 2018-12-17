package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.BannerMapper;
import com.lswd.youpin.lsy.BannerService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Banner;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    private final Logger log = LoggerFactory.getLogger(BannerServiceImpl.class);
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public LsResponse getBannerList(User u,String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. bannerId = {}", "获取banner列表", JSON.toJSON(machineId));
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
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            
            
            int total = bannerMapper.getBannerCount(keyword,pageId,machineId,canteenIds);
            log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword================="+ JSON.toJSONString(keyword));
            List<Banner> banners = bannerMapper.getBannerList(keyword,pageId,machineId,canteenIds,pageSize,offSet);
            log.info("banners==="+ JSON.toJSONString(banners));
            if (banners != null && banners.size() > 0) {
                lsResponse.setData(banners);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            log.error("获取banner出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

//    @Override
    public LsResponse getBannerById(Integer id) {
        log.info("{} is being executed. bannerId = {}", "根据餐厅ID获取banner", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Banner banner = bannerMapper.selectByPrimaryKey(id);
                log.info("banners===" + JSON.toJSONString(banner));
                if (banner!= null) {
                    lsResponse.setData(banner);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取banner出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateBanner(Banner banner) {
        log.info("{} is being executed. banner = {}", "根据餐厅ID获取banner", JSON.toJSON(banner));
        LsResponse lsResponse = new LsResponse();
        banner.setUpdateTime(Dates.now());
        int result;
        if(banner.getId()==null) {
            try {
                banner.setCreateTime(Dates.now());
                result = bannerMapper.insertSelective(banner);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. banner = {}", "添加资源", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.BANNER_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加资源报错", e.toString());
            }
        }else{
            result = bannerMapper.updateByPrimaryKeySelective(banner);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.BANNER_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delBanner(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = bannerMapper.delBanner(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.BANNER_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.BANNER_DEL_ERR.getMsg());
            log.error("资源删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}
