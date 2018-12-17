package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.VideoMapper;
import com.lswd.youpin.lsy.VideoService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Video;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public LsResponse getVideoList(User u, String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. videoId = {}", "获取视频列表", JSON.toJSON(machineId));
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
            int total = videoMapper.getVideoCount(keyword,pageId,machineId,canteenIds);
            log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword===="+ JSON.toJSONString(keyword));
            List<Video> videos = videoMapper.getVideoList(keyword,pageId,machineId,canteenIds,pageSize,offSet);
            log.info("videos==="+ JSON.toJSONString(videos));
            if (videos != null && videos.size() > 0) {
                lsResponse.setData(videos);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.VIDEO_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取视频出错", e.toString());
            log.info("e============"+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getVideoById(Integer id) {
        log.info("{} is being executed. videoId = {}", "根据餐厅ID获取视频", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Video video = videoMapper.selectByPrimaryKey(id);
                log.info("videos===" + JSON.toJSONString(video));
                if (video!= null) {
                    lsResponse.setData(video);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.VIDEO_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取视频出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateVideo(Video video) {
        log.info("{} is being executed. video = {}", "根据餐厅ID获取视频", JSON.toJSON(video));
        LsResponse lsResponse = new LsResponse();
        video.setUpdateTime(Dates.now());
        int result;
        if(video.getId()==null) {
            try {
                video.setCreateTime(Dates.now());
                result = videoMapper.insertSelective(video);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. video = {}", "添加资源", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.VIDEO_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加资源报错", e.toString());
            }
        }else{
            result = videoMapper.updateByPrimaryKeySelective(video);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.VIDEO_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delVideo(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = videoMapper.delVideo(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.VIDEO_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.VIDEO_DEL_ERR.getMsg());
            log.error("资源删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}