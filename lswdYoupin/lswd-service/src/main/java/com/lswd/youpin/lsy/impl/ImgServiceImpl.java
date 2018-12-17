package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.ImgMapper;
import com.lswd.youpin.lsy.ImgService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Img;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {
    private final Logger log = LoggerFactory.getLogger(ImgServiceImpl.class);
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public LsResponse getImgList(User u, String keyword, Integer resTypeId, String updateTime, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. imgId = {}", "根据餐厅ID获取图片列表", JSON.toJSON(resTypeId));
        LsResponse lsResponse = new LsResponse();
        String[] canteenIds =null;
        try {

            if(resTypeId==null){
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
            if (updateTime != null&&!"".equals(updateTime)) {
                updateTime = new String(updateTime.getBytes("utf-8"), "utf-8");
            } else {
                updateTime = "";
            }

            int total = imgMapper.getImgCount(keyword,resTypeId,canteenIds,updateTime);
            log.info("imgId==="+ JSON.toJSONString(resTypeId)+"keyword===="+ JSON.toJSONString(keyword));
            List<Img> imgs = imgMapper.getImgList(keyword,resTypeId,canteenIds,updateTime,pageSize,offSet);
            log.info("imgs==="+ JSON.toJSONString(imgs));
            if (imgs != null && imgs.size() > 0) {
                lsResponse.setData(imgs);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.IMG_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取图片列表出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getImgById(Integer id) {
        log.info("{} is being executed. imgId = {}", "根据餐厅ID获取图片列表", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Img img = imgMapper.selectByPrimaryKey(id);
                log.info("imgs===" + JSON.toJSONString(img));
                if (img!= null) {
                    lsResponse.setData(img);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.IMG_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取图片列表出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateImg(Img img) {
        log.info("{} is being executed. img = {}", "根据餐厅ID获取图片列表", JSON.toJSON(img));
        LsResponse lsResponse = new LsResponse();
        img.setUpdateTime(Dates.now());
        int result;
        if(img.getId()==null) {
            img.setUpdateTime(Dates.now());
            try {
                img.setCreateTime(Dates.now());
                result = imgMapper.insertSelective(img);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. img = {}", "添加图片", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.IMG_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加图片报错", e.toString());
            }
        }else{
            img.setCreateTime(Dates.now());
            img.setUpdateTime(Dates.now());
         //   img.setCreateUser(user.getUsername());
          //  img.setUpdateUser(user.getUsername());
            result = imgMapper.updateByPrimaryKeySelective(img);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.IMG_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delImg(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = imgMapper.delImg(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.IMG_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.IMG_DEL_ERR.getMsg());
            log.error("图片删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}