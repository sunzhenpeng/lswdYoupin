package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.dao.lsy.*;
import com.lswd.youpin.lsy.LsyOperationService;
import com.lswd.youpin.response.LsyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LsyOperationServiceImpl implements LsyOperationService {
    private final Logger log = LoggerFactory.getLogger(LsyOperationServiceImpl.class);

    @Autowired
    private AppResourceMapper appResourceMapper;
    @Autowired
    private MachineMapper machineMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private PageMapper pageMapper;

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private PageImgMapper pageImgMapper;
    @Autowired
    private PdfMapper pdfMapper;
    @Autowired
    private ResTypeMapper resTypeMapper;



    @Override
    public LsyResponse getRulesList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "管理制度-二级页面", JSON.toJSON(machineNo));
        //banner list<resType>
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> resTypeList = new ArrayList<Map<String, Object>>();
        String bannerUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                resTypeList = resTypeMapper.getResTypeListByMachineNo(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", resTypeList);
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }

    @Override
    public LsyResponse getRulesInfo(String url,String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "管理制度-详细页面", JSON.toJSON(machineNo));
        //banner pdf
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
        String pdfUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            log.info("pageUrl=========="+url);
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl("getRulesList");
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pdfUrl = pdfMapper.getPdfUrlByMachineNo(machineId,id);
                map.put("banner", bannerUrl);
                map.put("pdf", pdfUrl);
                log.info("lsys===" + JSON.toJSONString(map));
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }


    @Override
    public LsyResponse getDeviceControllList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "设备管理-二级页面", JSON.toJSON(machineNo));
        //banner list<resType>
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> resTypeList = new ArrayList<Map<String, Object>>();
        String bannerUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                resTypeList = resTypeMapper.getResTypeListByMachineNo(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", resTypeList);
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }

    @Override
    public LsyResponse getDeviceDetailInfo(String url,String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "设备管理-详细页面", JSON.toJSON(machineNo));
        //banner pdf
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
        String pdfUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            log.info("pageUrl=========="+url);
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl("getDeviceControllList");
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pdfUrl = pdfMapper.getPdfUrlByMachineNo(machineId,id);
                map.put("banner", bannerUrl);
                map.put("pdf", pdfUrl);
                log.info("lsys===" + JSON.toJSONString(map));
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }



    @Override
    public LsyResponse getOperationFlowList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "操作流程-二级页面", JSON.toJSON(machineNo));
        //banner list<resType>
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> resTypeList = new ArrayList<Map<String, Object>>();
        String bannerUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                resTypeList = resTypeMapper.getResTypeListByMachineNo(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", resTypeList);
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }

    @Override
    public LsyResponse getOperationFlowInfo(String url,String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "操作流程-详细页面", JSON.toJSON(machineNo));
        //banner pdf
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
        String pdfUrl = "";
        Integer machineId;
        Integer pageId;
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            log.info("pageUrl=========="+url);
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("UTF-8"), "UTF-8");
            }else {
                machineNo = "";
            }
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if(machineId==null){
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl("getOperationFlowList");
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pdfUrl = pdfMapper.getPdfUrlByMachineNo(machineId,id);
                map.put("banner", bannerUrl);
                map.put("pdf", pdfUrl);
                log.info("lsys===" + JSON.toJSONString(map));
                if (map.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有返回map");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

        return lsyResponse;
    }








}