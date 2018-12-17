package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.PageMapper;
import com.lswd.youpin.lsy.PageService;
import com.lswd.youpin.model.lsy.Page;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {
    private final Logger log = LoggerFactory.getLogger(PageServiceImpl.class);
    @Autowired
    private PageMapper pageMapper;

    @Override
    public LsResponse getPageList(String keyword, Integer machineId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. pageId = {}", "获取页面列表", JSON.toJSON(machineId));
        LsResponse lsResponse = new LsResponse();
        try {

            int offSet = 0;
            if (keyword != null && !(keyword.equals(""))) {
                // String tmp = URLDecoder.decode(keyword, "UTF-8");
                keyword = new String(keyword.getBytes("iso8859-1"), "UTF-8");
            }else {
                keyword = "";
            }

            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = pageMapper.getPageCount(keyword,machineId);
            log.info("machineId==="+ JSON.toJSONString(machineId)+"keyword===="+ JSON.toJSONString(keyword));
            List<Page> pages = pageMapper.getPageList(keyword,machineId,pageSize,offSet);
            log.info("pages==="+ JSON.toJSONString(pages));
            if (pages != null && pages.size() > 0) {
                lsResponse.setData(pages);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.PAGE_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("获取页面出错", e.toString());
            log.info("e============"+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPageById(Integer id) {
        log.info("{} is being executed. pageId = {}", "根据餐厅ID获取页面", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Page page = pageMapper.selectByPrimaryKey(id);
                log.info("pages===" + JSON.toJSONString(page));
                if (page!= null) {
                    lsResponse.setData(page);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.PAGE_NO_ERR.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取页面出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdatePage(Page page) {
        log.info("{} is being executed. page = {}", "根据餐厅ID获取页面", JSON.toJSON(page));
        LsResponse lsResponse = new LsResponse();
        page.setUpdateTime(Dates.now());
        int result;
        if(page.getId()==null) {
            try {

                result = pageMapper.insertSelective(page);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. page = {}", "添加资源", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.PAGE_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加资源报错", e.toString());
            }
        }else{
            result = pageMapper.updateByPrimaryKeySelective(page);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.PAGE_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delPage(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = pageMapper.delPage(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.PAGE_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.PAGE_DEL_ERR.getMsg());
            log.error("资源删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}