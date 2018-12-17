package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.PageImgMapper;
import com.lswd.youpin.lsy.PageImgService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.PageImg;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageImgServiceImpl implements PageImgService {
    private final Logger log = LoggerFactory.getLogger(PageImgServiceImpl.class);
    @Autowired
    private PageImgMapper pageImgMapper;

    @Override
    public LsResponse getPageImgList(User u, String keyword, Integer pageId, Integer machineId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. pageImgId = {}", "获取页面图片列表", JSON.toJSON(machineId));
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
            int total = pageImgMapper.getPageImgCount(keyword,pageId,machineId,canteenIds);
            log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword===="+ JSON.toJSONString(keyword));
            List<PageImg> pageImgs = pageImgMapper.getPageImgList(keyword,pageId,machineId,canteenIds,pageSize,offSet);
            log.info("pageImgs==="+ JSON.toJSONString(pageImgs));
            if (pageImgs != null && pageImgs.size() > 0) {
                lsResponse.setData(pageImgs);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.VIDEO_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取页面图片出错", e.toString());
            log.info("e============"+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPageImgById(Integer id) {
        log.info("{} is being executed. pageImgId = {}", "根据餐厅ID获取页面图片", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                PageImg pageImg = pageImgMapper.selectByPrimaryKey(id);
                log.info("pageImgs===" + JSON.toJSONString(pageImg));
                if (pageImg!= null) {
                    lsResponse.setData(pageImg);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.VIDEO_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取页面图片出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdatePageImg(PageImg pageImg) {
        log.info("{} is being executed. pageImg = {}", "根据餐厅ID获取页面图片", JSON.toJSON(pageImg));
        LsResponse lsResponse = new LsResponse();
        pageImg.setUpdateTime(Dates.now());
        int result;
        if(pageImg.getId()==null) {
            try {

                pageImg.setCreateTime(Dates.now());
                result = pageImgMapper.insertSelective(pageImg);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. pageImg = {}", "添加资源", "成功");
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
            result = pageImgMapper.updateByPrimaryKeySelective(pageImg);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.VIDEO_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delPageImg(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = pageImgMapper.delPageImg(id);
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