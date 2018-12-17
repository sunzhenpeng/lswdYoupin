package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.util.Strings;
import com.lswd.youpin.dao.lsy.*;
import com.lswd.youpin.lsy.LsyService;
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
public class LsyServiceImpl implements LsyService {
    private final Logger log = LoggerFactory.getLogger(LsyServiceImpl.class);

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
    public LsyResponse getHomeInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "获取首页APP列表", JSON.toJSON(machineNo));
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> appResources =new ArrayList<Map<String, Object>>();
        String videoUrl = "";
        String machineName = "";
        String qrcodeUrl = "";
        Integer machineId;
        Integer pageId;
        try {
          /*  String prefix = Strings.urlHandle(url);
            log.info("prefix====="+JSON.toJSONString(prefix));*/
            url = Strings.urlHandle(url);
            log.info("pageUrl=========="+url);
            machineNo = Strings.paramHandle(machineNo);
            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                pageId = pageMapper.getPageIdByImgUrl(url);
                machineId = machineMapper.getIdByMachineNo(machineNo);
                appResources =  appResourceMapper.getAppResourceByMachineNo(machineNo);
                videoUrl =  videoMapper.getVideoUrlByMachineNo(machineId,pageId);
                machineName =  machineMapper.getTitleByMachineNo(machineNo);
                qrcodeUrl = pageImgMapper.getImgUrlByMachineNo(machineId, pageId);
                map.put("video", videoUrl);
                map.put("title", machineName);
                map.put("qrcode", qrcodeUrl);
                map.put("menuFlagList", appResources);
                log.info("lsys===" + JSON.toJSONString(map));
                if (appResources.size() > 0) {
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                }else{
                    lsyResponse.setAsSuccess();
                    lsyResponse.setData(map);
                    log.error("没有菜单");
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
    public LsyResponse getLawAndRegulationMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "法律法规二级页面", JSON.toJSON(machineNo));
        //banner1 video1 imglist
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<String> pageImgList = new ArrayList<String>();
        String videoUrl = "";
        String banner = "";
        Integer machineId;
        Integer pageId;
        try {
          /*  String prefix = Strings.urlHandle(url);
            log.info("prefix====="+JSON.toJSONString(prefix));*/

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
                pageId = pageMapper.getPageIdByImgUrl(url);
                videoUrl =  videoMapper.getVideoUrlByMachineNo(machineId,pageId);
                banner = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pageImgList = pageImgMapper.getPageImg(machineId, pageId);
                map.put("video", videoUrl);
                map.put("banner", banner);
                map.put("imagelist", pageImgList);
           //     map.put("imagelist", imageList);
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
    public LsyResponse getLeaderVisitList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "法律法规领导访问三级页面", JSON.toJSON(machineNo));
        //banner1 list
        LsyResponse lsyResponse = new LsyResponse();
        List<Map<String, Object>> videos =new ArrayList<Map<String, Object>>();
        Map<String, Object> map=new HashMap<String, Object>();
        String banner = "";
        Integer machineId;
        Integer pageId;
        try {
          /*  String prefix = Strings.urlHandle(url);
            log.info("prefix====="+JSON.toJSONString(prefix));*/

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
                pageId = pageMapper.getPageIdByImgUrl(url);
                banner = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                videos = videoMapper.getVideoListByMachineNo(machineId, pageId);

                map.put("banner", banner);
                map.put("list", videos);
                //     map.put("imagelist", imageList);
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
    public LsyResponse getLeaderVisitInfo(String url, String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "法律法规领导访问详细页面", JSON.toJSON(machineNo));
        //title banner video
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
        String title = "";
        String videoUrl = "";
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
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                videoUrl = videoMapper.getVideoUrlByMachineNo(machineId, id);
                map.put("banner", bannerUrl);
                map.put("video", videoUrl);
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
    public LsyResponse getLawList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "法律法规法律三级页面", JSON.toJSON(machineNo));
        //banner1 list<restype>
               LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> pageImgs = new ArrayList<Map<String, Object>>();
        String banner = "";
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
                pageId = pageMapper.getPageIdByImgUrl(url);
                banner = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pageImgs = pageImgMapper.getPageImgListByMachineNo(machineId, pageId);
                map.put("list", pageImgs);
                map.put("banner", banner);
                //     map.put("imagelist", imageList);
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
    public LsyResponse getLawInfo(String url, String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "法律法规法律详细页面", JSON.toJSON(machineNo));
        //title banner pdf
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
  //      String title = "";
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
              //  title =  machineMapper.getTitleByMachineNo(machineNo);
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                pdfUrl = pdfMapper.getPdfUrlByPageImgId(id);
              //  map.put("title", title);
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
    public LsyResponse getUploadTypeList(String machineNo) {
        log.info("{} is being executed. machineNo = {}", "根据餐厅ID获取资源类型列表", JSON.toJSON(machineNo));
        LsyResponse lsyResponse = new LsyResponse();
        List<Map<String, Object>> resTypeList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int offSet = 0;

            if (machineNo != null && !machineNo.equals("")) {
                Integer machineId = machineMapper.getIdByMachineNo(machineNo);
                 resTypeList = resTypeMapper.getUploadResTypeList(machineId);

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


   /* @Override
    public LsResponse getRecipePlanListWebPageShow(User user, String canteenId, String eatTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String[] canteenIds = user.getCanteenIds().split(",");
            if (canteenId != null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            String startdate = "";
            String enddate = "";
            if (eatTime != null && !("").equals(eatTime)){
                startdate = eatTime + " 00:00:00";
                enddate = eatTime + " 23:59:59";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            String now = Dates.format(new Date(),"yyyy-MM-dd 00:00:00");

//            List<RecipePlan> recipePlen = recipePlanMapper.getRecipePlanBeforeNow(now);//将今天之前的列表查询出来，修改他们的状态为1，为已过期
//            if (recipePlen != null && recipePlen.size() >0){
//                for (RecipePlan plan : recipePlen){
//                    plan.setUpdateUser(user.getUsername());
//                    plan.setUpdateTime(Dates.now());
//                    recipePlanMapper.updateRecipePlanIsDelete(plan);
//                }
//            }

            int total = recipePlanMapper.getRecipePlanListCountWebPageShow(canteenId,now,startdate,enddate,canteenIds);
            List<RecipePlan> planList = recipePlanMapper.getRecipePlanListWebPageShow(canteenId,now,startdate,enddate,canteenIds,offSet,pageSize);
            for (RecipePlan recipePlan :planList){
                recipePlan.setStatusName(recipePlan.getDelete()?"已过期":"正常使用");
                List<RecipePlanItems> items = recipePlanItemsMapper.getPlanItemsByRecipePlanId(recipePlan.getRecipePlanId());
                if (items != null && items.size() >0){
                    recipePlan.setPlanItemses(items);
                }
                //Canteen canteen = canteenMapper.getCanteenByCanteenId(recipePlan.getCanteenId());得不到餐厅名称，不同的数据库
            }
            if (planList != null&& planList.size() > 0){
                lsResponse.setData(planList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("数据查询成功");
            }else{
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("数据查询失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("数据查询失败，发生异常"+e.getMessage());
            log.error("菜品计划WEB端，列表数据查询失败，发生异常，异常信息为:"+e.toString());
        }
        return lsResponse;
    }*/

}