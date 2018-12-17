package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.lsy.*;
import com.lswd.youpin.dao.lsyp.RecipeMapper;
import com.lswd.youpin.dao.lsyp.RecipePlanItemsMapper;
import com.lswd.youpin.dao.lsyp.RecipePlanMapper;
import com.lswd.youpin.lsy.LsyNutritionService;
import com.lswd.youpin.response.LsyResponse;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LsyNutritionServiceImpl implements LsyNutritionService {
    private final Logger log = LoggerFactory.getLogger(LsyNutritionServiceImpl.class);

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
    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipePlanMapper recipePlanMapper;


    @Autowired
    private RecipePlanItemsMapper recipePlanItemsMapper;




    @Override
    public LsyResponse getNutritionMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "管理制度-二级页面", JSON.toJSON(machineNo));
        //banner
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

       /* Object jsonString = JSONObject.parse(ConstantsCode.getNutritionMainInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }

    @Override
    public LsyResponse getRecipeList(String url,String machineNo,String updateTime ) {
        log.info("{} is being executed. machineNo = {}", "管理制度-详细页面", JSON.toJSON(machineNo));
        //banner pdf
        DataSourceHandle.setDataSourceType("LSWD");
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> recipeMap01=new HashMap<String, Object>();
        Map<String, Object> recipeMap02=new HashMap<String, Object>();
        Map<String, Object> recipeMap03=new HashMap<String, Object>();
        List<Object> tempList = new ArrayList<>();
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> recipeTypeList = new ArrayList<Map<String, Object>>();
        String bannerUrl = "";
        Integer machineId;
        Integer pageId;
        try {

            String[] urls = url.split("\\/");
            url = urls[3];
            String canteenId = "";
            if (updateTime != null && !(updateTime.equals(""))) {
                updateTime = new String(updateTime.getBytes("iso8859-1"), "UTF-8");
            }else {
                updateTime = "";
            }
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("iso8859-1"), "UTF-8");
            }else {
                machineNo = "";
            }

            if (machineNo != null && !machineNo.equals("")&&url != null && !url.equals("")) {
                machineId = machineMapper.getIdByMachineNo(machineNo);
                if (machineId == null) {
                    lsyResponse.setAsFailure();
                    log.error("此设备码没有绑定到后台");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
            }

            if (machineNo != null && !machineNo.equals("")) {

                  canteenId = machineMapper.getCanteenIdByMachineNo(machineNo);
            }

            if (canteenId == null || "".equals(canteenId)){
                lsyResponse.setAsFailure();
                return lsyResponse;
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

            DataSourceHandle.setDataSourceType("LSCT");
            String  planId = recipePlanMapper.getRecipePlanListApp(canteenId,now,updateTime);

            items = recipePlanItemsMapper.getPlanItemsApp(planId,0);
            recipeMap01.put("title", "早餐");
            recipeMap01.put("id", 0);
            recipeMap01.put("data", items);
            tempList.add(recipeMap01);

            items = recipePlanItemsMapper.getPlanItemsApp(planId,1);
            recipeMap02.put("title", "午餐");
            recipeMap02.put("id", 1);
            recipeMap02.put("data", items);
            tempList.add(recipeMap02);

            items = recipePlanItemsMapper.getPlanItemsApp(planId,2);
            recipeMap03.put("title", "晚餐");
            recipeMap03.put("id", 2);
            recipeMap03.put("data", items);
            tempList.add(recipeMap03);

            map.put("banner", bannerUrl);
            map.put("recipelist", tempList);
            log.info("lsys===" + JSON.toJSONString(map));
            if (map.size() > 0) {
                lsyResponse.setAsSuccess();
                lsyResponse.setData(map);
            }else{
                lsyResponse.setAsSuccess();
                lsyResponse.setData(map);
                log.error("没有返回map");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsyResponse.setAsFailure();
            log.error("菜品计划WEB端，列表数据查询失败，发生异常，异常信息为:"+e.toString());
        }
        return lsyResponse;
        /*Object jsonString = JSONObject.parse(ConstantsCode.getRecipeList);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);
        return lsyResponse;*/

    }



    @Override
    public LsyResponse getRecipeInfo(String url,String machineNo,Integer id) {
        log.info("{} is being executed. machineNo = {}", "管理制度-详细页面", JSON.toJSON(machineNo));
        //banner data
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map=new HashMap<String, Object>();
        Map<String, Object> tempMap=new HashMap<String, Object>();
        List<Object> tempList = new ArrayList<>();
        Map<String, Object> recipeMap=new HashMap<String, Object>();

        String bannerUrl = "";
        Integer machineId;
        Integer pageId;
        DataSourceHandle.setDataSourceType("LSWD");
        try {
            String[] urls = url.split("\\/");
            url = urls[3];
            log.info("pageUrl=========="+url);
            if (machineNo != null && !(machineNo.equals(""))) {
                machineNo = new String(machineNo.getBytes("iso8859-1"), "UTF-8");
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
                DataSourceHandle.setDataSourceType("LSCT");
                recipeMap  = recipeMapper.getRecipeUrlByRecipeId(id);

                map.put("banner", bannerUrl);
                map.put("title", recipeMap.get("name"));
                tempList.add(recipeMap.get("img").toString());
                map.put("imagelist",tempList );
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
     /*   Object jsonString = JSONObject.parse(ConstantsCode.getRecipeInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        lsyResponse.setData(map);
        return lsyResponse;

    }


    @Override
    public LsyResponse getNutritionInfo(String url,String machineNo) {
        log.info("{} is being executed. machineNo = {}", "管理制度-详细页面", JSON.toJSON(machineNo));
        //banner pdf
        LsyResponse lsyResponse = new LsyResponse();
      /*  Map<String, Object> map=new HashMap<String, Object>();
        String bannerUrl = "";
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

                map.put("banner", bannerUrl);

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
        }*/
        Object jsonString = JSONObject.parse(ConstantsCode.getNutritionInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);
        return lsyResponse;

    }


    @Override
    public LsyResponse getNutritionRecordInfo(String url,String machineNo) {
        log.info("{} is being executed. machineNo = {}", "营养档案-详细页面", JSON.toJSON(machineNo));
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
                pageImgList = pageImgMapper.getPageImg(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("imagelist", pageImgList);
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
    /*    Object jsonString = JSONObject.parse(ConstantsCode.getNutritionRecordInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;

    }





}