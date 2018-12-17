package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsy.PdfMapper;
import com.lswd.youpin.lsy.PdfService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Pdf;
import com.lswd.youpin.response.LsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {
    private final Logger log = LoggerFactory.getLogger(PdfServiceImpl.class);
    @Autowired
    private PdfMapper pdfMapper;

    @Override
    public LsResponse getPdfList(User u, String keyword, Integer resTypeId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. PdfId = {}", "获取Pdf列表", JSON.toJSON(resTypeId));
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
            
            
            int total = pdfMapper.getPdfCount(keyword,resTypeId,canteenIds);
            log.info("machineId==="+ JSON.toJSONString(resTypeId)+"keyword================="+ JSON.toJSONString(keyword));
            List<Pdf> Pdfs = pdfMapper.getPdfList(keyword,resTypeId,canteenIds,pageSize,offSet);
            log.info("Pdfs==="+ JSON.toJSONString(Pdfs));
            if (Pdfs != null && Pdfs.size() > 0) {
                lsResponse.setData(Pdfs);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            log.error("获取Pdf出错", e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

//    @Override
    public LsResponse getPdfById(Integer id) {
        log.info("{} is being executed. PdfId = {}", "根据餐厅ID获取Pdf", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                Pdf Pdf = pdfMapper.selectByPrimaryKey(id);
                log.info("Pdfs===" + JSON.toJSONString(Pdf));
                if (Pdf!= null) {
                    lsResponse.setData(Pdf);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
                }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取Pdf出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdatePdf(Pdf pdf) {
        log.info("{} is being executed. Pdf = {}", "根据餐厅ID获取Pdf", JSON.toJSON(pdf));
        LsResponse lsResponse = new LsResponse();
        pdf.setUpdateTime(Dates.now());
        int result;
        if(pdf.getId()==null) {
            try {
                pdf.setCreateTime(Dates.now());
                result = pdfMapper.insertSelective(pdf);
                if (result > 0) {
                    lsResponse.setAsSuccess();
                    log.info("{} is being executed. Pdf = {}", "添加资源", "成功");
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.PDF_ADD_ERR.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
                lsResponse.setMessage(e.toString());
                log.error("添加资源报错", e.toString());
            }
        }else{
            result = pdfMapper.updateByPrimaryKeySelective(pdf);
            if (result>0) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.PDF_UPDATE_ERR.name());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse delPdf(Integer id) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            int result = pdfMapper.delPdf(id);
            if (result>-1) {
                lsResponse.setAsSuccess();
            }else{
                lsResponse.checkSuccess(false, CodeMessage.PDF_DEL_ERR.getMsg());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.PDF_DEL_ERR.getMsg());
            log.error("资源删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }



}
