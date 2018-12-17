package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.dao.lsy.*;
import com.lswd.youpin.lsy.LsySupplyManageService;
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
public class LsySupplyManageServiceImpl implements LsySupplyManageService {
    private final Logger log = LoggerFactory.getLogger(LsySupplyManageServiceImpl.class);

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
    public LsyResponse getSupplyControllMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "品控监管-二级页面", JSON.toJSON(machineNo));
        //banner
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
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
                map.put("banner", bannerUrl);
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
    public LsyResponse getSupplyMeatMenuMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜肉类-三级页面", JSON.toJSON(machineNo));
        //banner list<resType>
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
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
                map.put("banner", bannerUrl);
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
    public LsyResponse getSupplyOilMenuMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "品控监管-粮油米面-三级页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
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
                map.put("banner", bannerUrl);
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
    public LsyResponse getSupplyMeatInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyWaterSeafoodInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyVegetablesInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyFruitsInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyRiceInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyFlourInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyOilInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyHalfFoodInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyKitchenInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    public LsyResponse getSupplyOtherFoodInfo(String url, String machineNo,String updateTime) {
        log.info("{} is being executed. machineNo = {}", "品控监管-生鲜粮油-详细页面", JSON.toJSON(machineNo));
        //banner imagelist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
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
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("UTF-8"), "UTF-8");
            }else {
                updateTime = "";
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
                pageImgList = pageImgMapper.getPageImgUrlByMachineNo(machineId, pageId,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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