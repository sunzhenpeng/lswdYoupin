package com.lswd.youpin.lsy.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.dao.lsy.*;
import com.lswd.youpin.lsy.LsyCanteenQualityService;
import com.lswd.youpin.model.lsy.Employees;
import com.lswd.youpin.model.lsy.Position;
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
public class LsyCanteenQualityServiceImpl implements LsyCanteenQualityService {
    private final Logger log = LoggerFactory.getLogger(LsyCanteenQualityServiceImpl.class);

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
    private EmployeesMapper employeesMapper;
    @Autowired
    private PositionMapper positionMapper;



    @Override
    public LsyResponse getRestaurantManagementMainInfo(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        //banner
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
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
                    log.error("此设备码没有绑定到后台 ");
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
                    log.error("没有返回map ");
                }
            }else{
                lsyResponse.setAsFailure();
                log.error("参数为空");
            }

        } catch (Exception e) {
            lsyResponse.setAsFailure();
            log.error("e.string====="+e.toString());
        }

    /*    Object jsonString = JSONObject.parse(ConstantsCode.getRestaurantManagementMainInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }



    @Override
    public LsyResponse getQualificationList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        //banner list<id  img>
        LsyResponse lsyResponse = new LsyResponse();

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> imgList = new ArrayList<Map<String, Object>>();
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
                imgList = pageImgMapper.getPageImgListByMachineNo(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", imgList);
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

        /*Object jsonString = JSONObject.parse(ConstantsCode.getQualificationList);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }


    @Override
    public LsyResponse getForbiddenList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        //banner list<id imgurl>
        LsyResponse lsyResponse = new LsyResponse();

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> imgList = new ArrayList<Map<String, Object>>();
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
                imgList = pageImgMapper.getPageImgListByMachineNo(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", imgList);
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

      /*  Object jsonString = JSONObject.parse(ConstantsCode.getForbiddenList);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }


    @Override
    public LsyResponse getStaffList(String url, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        //banner  stifflist( key  image  data(title  text key) )
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> stiffList = new ArrayList<Map<String, Object>>();

        List<Employees> employees = new ArrayList<>();
        List<Position> positions = new ArrayList<>();
        List<Object> positionList = new ArrayList<>();
        List<String> imgList = new ArrayList<String>();
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
                positions = positionMapper.getAllPosition();
                log.info("positions", JSON.toJSON(positions));
                for(int i=0;i<positions.size();i++){
                    Map<String, Object> positionMap = new HashMap<String, Object>();
                    int positionId = positions.get(i).getId();

                    stiffList = employeesMapper.getEmployeesByPositionId(positionId);


                    log.info("name"+ positions.get(i).getPositionName());
                    positionMap.put("id", positions.get(i).getPositionName());
                    positionMap.put("image", "https://web.lsypct.com/upload/nodata/position_icon.png");
                    positionMap.put("data", stiffList);
                    positionList.add(positionMap);
                }


                map.put("banner", bannerUrl);
                map.put("stafflist", positionList);
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

    /*    Object jsonString = JSONObject.parse(ConstantsCode.getStaffList);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }


    @Override
    public LsyResponse getStaffInfo(String url,Integer id, String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        // info
        LsyResponse lsyResponse = new LsyResponse();

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> employeeMap = new HashMap<>();

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
                employeeMap = employeesMapper.getEmployeeInfo(id);

                map.put("info", employeeMap);
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

        /*Object jsonString = JSONObject.parse(ConstantsCode.getStaffInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }

    @Override
    public LsyResponse getTrainList(String url,String machineNo) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-员工培训-三级页面", JSON.toJSON(machineNo));
        //banner
        LsyResponse lsyResponse = new LsyResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> imgList = new ArrayList< Map<String, Object>>();
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
                imgList = resTypeMapper.getResTypeListByPageId(machineId, pageId);
                map.put("banner", bannerUrl);
                map.put("list", imgList);
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
      /*  Object jsonString = JSONObject.parse(ConstantsCode.getTrainList);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;

    }


    @Override
    public LsyResponse getTrainInfo(String url, String machineNo,String updateTime,Integer id) {
        log.info("{} is being executed. machineNo = {}", "餐饮资质-二级页面", JSON.toJSON(machineNo));
        //banner imgList
        LsyResponse lsyResponse = new LsyResponse();

        Map<String, Object> map = new HashMap<String, Object>();
        List<String> imgList = new ArrayList<String>();
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

                    log.error("此设备码没有绑定到后台 ");
                    return lsyResponse;
                }
                pageId = pageMapper.getPageIdByImgUrl(url);
                bannerUrl = bannerMapper.getBannerUrlByMachineNo(machineId, pageId);
                imgList = imgMapper.getImgListByResTyId(id,updateTime);
                map.put("banner", bannerUrl);
                map.put("imagelist", imgList);
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

       /* Object jsonString = JSONObject.parse(ConstantsCode.getTrainInfo);
        log.info(jsonString.toString());
        lsyResponse.setData(jsonString);*/
        return lsyResponse;
    }


}