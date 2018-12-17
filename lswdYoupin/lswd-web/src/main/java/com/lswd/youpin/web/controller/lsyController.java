package com.lswd.youpin.web.controller;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.lsy.ResTypeMapper;
import com.lswd.youpin.dao.lsy.UploadLogMapper;
import com.lswd.youpin.lsy.*;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Img;
import com.lswd.youpin.model.lsy.UploadLog;
import com.lswd.youpin.response.LsyResponse;
import com.lswd.youpin.service.UserService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.RandomUtil;
import com.lswd.youpin.utils.SerializeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by sunzhenpeng on 20180714.
 */
@Api(value = "lsy", tags = "lsy", description = "APP接口汇总")
@Controller
@RequestMapping(value = "/lsy")
public class lsyController {
    private final Logger log = LoggerFactory.getLogger(UpLoadController.class);
    @Autowired
    LsyService lsyService;
    @Autowired
    LsyQualityControlService lsyQualityControlService;
    @Autowired
    LsySupplyManageService lsySupplyManageService;
    @Autowired
    LsyOperationService lsyOperationService;

    @Autowired
    LsyKitchenService lsyKitchenService;
    @Autowired
    LsyNutritionService lsyNutritionService;
    @Autowired
    LsyCanteenQualityService lsyCanteenQualityService;

    @Autowired
    UserService userService;
    @Autowired
    RedisManager redisManager;
    @Autowired
    MachineService machineService;

    @Autowired
    CanteenMapper canteenMapper;
    @Autowired
    ResTypeMapper resTypeMapper;
    @Autowired
    ImgService imgService;
    @Autowired
    UploadLogMapper uploadLogMapper;

    @ApiOperation(value = "app主页", notes = "app主页", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getHomeInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getHomeInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                   HttpServletRequest request,  HttpServletResponse rep) throws UnsupportedEncodingException {
        String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getHomeInfo(url,machineNo);
    }

    @ApiOperation(value = "法律法规-二级页面", notes = "法律法规-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLawAndRegulationMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLawAndRegulationMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                   HttpServletRequest request) throws UnsupportedEncodingException {
        String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getLawAndRegulationMainInfo(url,machineNo);
    }

    @ApiOperation(value = "法律法规-领导访问-三级页面", notes = "法律法规-领导访问-三级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLeaderVisitList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLeaderVisitList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                   HttpServletRequest request) throws UnsupportedEncodingException {
        String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getLeaderVisitList(url,machineNo);
    }


    @ApiOperation(value = "法律法规-领导访问-详细页面", notes = "法律法规-领导访问-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLeaderVisitInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLeaderVisitInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                          @ApiParam(value = "id", required = true) @RequestParam(value = "id") Integer id,
                                          HttpServletRequest request) throws UnsupportedEncodingException {
        String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getLeaderVisitInfo(url,machineNo,id);
    }

    @ApiOperation(value = "法律法规-法律-三级页面", notes = "法律法规-法律-三级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLawList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLawList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                          HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getLawList(url,machineNo);
    }
    @ApiOperation(value = "法律法规-法律-详细页面", notes = "法律法规-法律-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLawInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLawInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                  @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                  HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getLawInfo(url,machineNo,id);
    }

   // --------------------------------------------------------------品控监管------------------------------------------------------------


   @ApiOperation(value = "品控监管-二级页面", notes = "品控监管-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getQualityControllMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getQualityControllMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                  HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getQualityControllMainInfo(url,machineNo);
    }

    @ApiOperation(value = "品控监管-工作记录-三级页面", notes = "品控监管-工作记录-三级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getWorkLogList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getWorkLogList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                  HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getWorkLogList(url,machineNo);
    }
    @ApiOperation(value = "品控监管-工作记录-详细页面", notes = "品控监管-工作记录-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getWorkLogDetailInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getWorkLogDetailInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                            @ApiParam(value = "machineNo") @RequestParam(value = "id", required = false) Integer id,
                                            @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                      HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getWorkLogDetailInfo(url,machineNo,id,updateTime);
    }

    @ApiOperation(value = "品控监管-内部品控管理", notes = "品控监管-内部品控管理", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getQualityInnerControllInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getQualityInnerControllInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                   @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getQualityInnerControllInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "品控监管-部门监管", notes = "品控监管-部门监管", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getQualityOutControllInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getQualityOutControllInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                 @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getQualityOutControllInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "品控监管-供应商列表", notes = "品控监管-供应商列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplierList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplierList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getSupplierList(url,machineNo);
    }

    @ApiOperation(value = "品控监管-供应商-详细页面", notes = "品控监管-供应商-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplierDetail", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplierDetail(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "machineNo") @RequestParam(value = "id", required = false) Integer id,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getSupplierDetail(url,machineNo,id);
    }
    @ApiOperation(value = "品控监管-监管信息", notes = "品控监管-监管信息", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getQualityRegulatoryInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getQualityRegulatoryInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyQualityControlService.getQualityRegulatoryInfo(url,machineNo);
    }

    //--------------------------------------------------------供应管理----------------------------------------------------------------------

    @ApiOperation(value = "供应管理-货品分类", notes = "供应管理-货品分类", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyControllMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyControllMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyControllMainInfo(url,machineNo);
    }

    @ApiOperation(value = "供应管理-生鲜肉类-三级页面", notes = "供应管理-生鲜肉类-三级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyMeatMenuMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyMeatMenuMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                  HttpServletRequest request) throws UnsupportedEncodingException {
              String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyMeatMenuMainInfo(url,machineNo);
    }

    @ApiOperation(value = "供应管理-粮油米面-四级页面", notes = "供应管理-粮油米面-四级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyOilMenuMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyOilMenuMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyOilMenuMainInfo(url,machineNo);
    }


    @ApiOperation(value = "供应管理-猪牛羊肉", notes = "供应管理-水产海鲜", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyMeatInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyMeatInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                                 @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyMeatInfo(url,machineNo,updateTime);
    }


    @ApiOperation(value = "供应管理-水产海鲜", notes = "供应管理-水产海鲜", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyWaterSeafoodInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyWaterSeafoodInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyWaterSeafoodInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "供应管理-猪牛羊肉", notes = "供应管理-新鲜蔬菜", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyVegetablesInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyVegetablesInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyVegetablesInfo(url,machineNo,updateTime);
    }


    @ApiOperation(value = "供应管理-猪牛羊肉", notes = "供应管理-新鲜水果", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyFruitsInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyFruitsInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyFruitsInfo(url,machineNo,updateTime);
    }


    @ApiOperation(value = "供应管理-猪牛羊肉", notes = "供应管理-大米", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyRiceInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyRiceInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyRiceInfo(url,machineNo,updateTime);
    }


    @ApiOperation(value = "供应管理-猪牛羊肉", notes = "供应管理-面粉", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyFlourInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyFlourInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyFlourInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "供应管理-粮油", notes = "供应管理-粮油", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyOilInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyOilInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                         @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                         HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyOilInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "供应管理-半成品", notes = "供应管理-半成品", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyHalfFoodInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyHalfFoodInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                        @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                        HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyHalfFoodInfo(url,machineNo,updateTime);
    }


  @ApiOperation(value = "供应管理-厨房调味料", notes = "供应管理-厨房调味料", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyKitchenInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyKitchenInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                            @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyKitchenInfo(url,machineNo,updateTime);
    }

    @ApiOperation(value = "供应管理-其他产品", notes = "供应管理-其他产品", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getSupplyOtherFoodInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getSupplyOtherFoodInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                            @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsySupplyManageService.getSupplyOtherFoodInfo(url,machineNo,updateTime);
    }

//-----------------------------------------------------------管理制度、设备管理、操作流程----------------------------------------------------------


    @ApiOperation(value = "管理制度-二级页面", notes = "管理制度-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getRulesList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getRulesList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                              HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getRulesList(url,machineNo);
    }

    @ApiOperation(value = "管理制度-详细页面", notes = "管理制度-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getRulesInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getRulesInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                    @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getRulesInfo(url,machineNo,id);
    }

    @ApiOperation(value = "设备管理-二级页面", notes = "设备管理-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getDeviceControllList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getDeviceControllList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getDeviceControllList(url,machineNo);
    }

    @ApiOperation(value = "设备管理-详细页面", notes = "设备管理-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getDeviceDetailInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getDeviceDetailInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                    @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getDeviceDetailInfo(url,machineNo,id);
    }
//

    @ApiOperation(value = "操作流程-二级页面", notes = "操作流程-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getOperationFlowList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getOperationFlowList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                             HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getOperationFlowList(url,machineNo);
    }

    @ApiOperation(value = "操作流程-详细页面", notes = "操作流程-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getOperationFlowInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getOperationFlowInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                           @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                           HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyOperationService.getOperationFlowInfo(url,machineNo,id);
    }

//--------------------------------------------------------名厨亮灶-------------------------------------------------------------

    @ApiOperation(value = "名厨亮灶-二级页面", notes = "名厨亮灶-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLiveshowList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLiveshowList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyKitchenService.getLiveshowList(url,machineNo);
    }


   /* @ApiOperation(value = "名厨亮灶-详细页面", notes = "名厨亮灶-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLiveshowInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLiveshowInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyKitchenService.getLiveshowInfo(url,machineNo);
    }*/


    @ApiOperation(value = "名厨亮灶-详细页面", notes = "名厨亮灶-详细页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getLiveshowInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getLiveshowInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                           @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
             String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyKitchenService.getLiveshowInfo(url,machineNo,id);
    }


//--------------------------------------------------------营养管理------------------------------------------------------------------

    @ApiOperation(value = "营养管理-二级页面", notes = "营养管理-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getNutritionMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyNutritionService.getNutritionMainInfo(url,machineNo);
    }


    @ApiOperation(value = "营养管理-菜谱展示", notes = "营养管理-菜谱展示", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getRecipeList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                       HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyNutritionService.getRecipeList(url,machineNo,updateTime);
    }

    @ApiOperation(value = "营养管理-菜谱细节", notes = "营养管理-菜谱细节", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getRecipeInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getRecipeInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyNutritionService.getRecipeInfo(url,machineNo,id);
    }


    @ApiOperation(value = "营养管理-营养知识", notes = "营养管理-营养知识", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getNutritionInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyNutritionService.getNutritionInfo(url,machineNo);
    }


    @ApiOperation(value = "营养管理-营养档案", notes = "营养管理-营养档案", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getNutritionRecordInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getNutritionRecordInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                        HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyNutritionService.getNutritionRecordInfo(url,machineNo);
    }

    //--------------------------------------------------------餐饮资质---------------------------------------------------------------


    @ApiOperation(value = "餐厅管理-二级页面", notes = "餐厅管理-二级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getRestaurantManagementMainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getRestaurantManagementMainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                            HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getRestaurantManagementMainInfo(url,machineNo);
    }


    @ApiOperation(value = "餐厅管理-餐饮资质", notes = "餐厅管理-餐饮资质", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getQualificationList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getQualificationList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getQualificationList(url,machineNo);
    }

    @ApiOperation(value = "餐厅管理-菜谱细节", notes = "餐厅管理-菜谱细节", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getForbiddenList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getForbiddenList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getForbiddenList(url,machineNo);
    }


    @ApiOperation(value = "餐厅管理-员工档案", notes = "餐厅管理-员工档案", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getStaffList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getStaffList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                        HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getStaffList(url,machineNo);
    }


    @ApiOperation(value = "餐厅管理-员工信息", notes = "餐厅管理-员工信息", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getStaffInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getStaffInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                        @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                              HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getStaffInfo(url,id,machineNo);
    }

    @ApiOperation(value = "餐厅管理-员工培训-三级页面", notes = "餐厅管理-员工培训-三级页面", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getTrainList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getTrainList(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                              HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getTrainList(url,machineNo);
    }

    @ApiOperation(value = "餐厅管理-员工培训", notes = "餐厅管理-员工培训", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getTrainInfo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsyResponse getTrainInfo(@ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
                                    @ApiParam(value = "updateTime") @RequestParam(value = "updateTime", required = false) String updateTime,
                                    @ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                              HttpServletRequest request) throws UnsupportedEncodingException {
          String url = request.getRequestURI();
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyCanteenQualityService.getTrainInfo(url,machineNo,updateTime,id);
    }


    @ApiOperation(value = "app用户登录操作", notes = "app用户登录操作", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    @ResponseBody
    public LsyResponse doLogin(
                               @ApiParam(value = "username") @RequestParam(value = "username", required = false) String username,
                               @ApiParam(value = "password") @RequestParam(value = "password", required = false) String password,
                             HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSWD");
        return userService.doLogin(username,password,request);
    }

    @ApiOperation(value = "上传类型", notes = "上传类型", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getUploadTypeList", method = RequestMethod.GET)
    @ResponseBody
    public LsyResponse getUploadTypeList(
            @ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
            @ApiParam(value = "token") @RequestParam(value = "token", required = false) String token,
                                   @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
            HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSWD");
      //  log.info(token.toString());
    /*    log.info(restaurantId);
        log.info(token);
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            log.info(key.toString());
        }*/
        machineNo = machineService.getMachineNoByCanteenId(machineNo, restaurantId);
        return lsyService.getUploadTypeList(machineNo);
    }

    @ApiOperation(value = "我的上传", notes = "我的上传", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getUploadList", method = RequestMethod.GET)
    @ResponseBody
    public LsyResponse getUploadList(
            @ApiParam(value = "machineNo") @RequestParam(value = "machineNo", required = false) String machineNo,
            @ApiParam(value = "restaurantId") @RequestParam(value = "restaurantId", required = false) String restaurantId,
            HttpServletRequest request) {
        DataSourceHandle.setDataSourceType("LSWD");
        LsyResponse lsyResponse = new LsyResponse();
        try {
            String token = request.getHeader("token");
            if (token == null) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                lsyResponse.setAsFailure();
                return lsyResponse;
            } else {
                Integer userType = Integer.parseInt(request.getHeader("type"));
                Object object = SerializeUtils.deserialize(redisManager.get(token.getBytes()));
                if (userType == 4) {
                    User user = (User) object;
                    request.setAttribute("user", user);
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
            }
        } catch (Exception e) {
            log.info("exception===========：{}", e.getMessage());
            lsyResponse.setAsFailure();
            return lsyResponse;
        }
        List<Map<String, Object>> list = new ArrayList< Map<String, Object>>();
        Map<String, Object> map=new HashMap<String, Object>();
        User user = (User) request.getAttribute("user");
         list =  uploadLogMapper.getUploadLogApp(user.getUsername());
        map.put("list", list);
        lsyResponse.setData(map);
        return lsyResponse;
    }



    @ApiOperation(value = "上传", notes = "上传", nickname = "szp", httpMethod = "POST")
  @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
  @ResponseBody
  public LsyResponse doUpload(HttpServletRequest request,
                              @RequestParam("files[]") MultipartFile[] files,
                              @RequestParam(value = "type", required = true) Integer type
  ) {
        LsyResponse lsyResponse = new LsyResponse();
        try {
            String token = request.getHeader("token");
            if (token == null) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                lsyResponse.setAsFailure();
                return lsyResponse;
            } else {
                Integer userType = Integer.parseInt(request.getHeader("type"));
                Object object = SerializeUtils.deserialize(redisManager.get(token.getBytes()));
                if (userType == 4) {
                    User user = (User) object;
                    request.setAttribute("user", user);
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
            }
        } catch (Exception e) {
            log.info("exception===========：{}", e.getMessage());
            lsyResponse.setAsFailure();
            return lsyResponse;
        }

        User user = (User) request.getAttribute("user");

      log.info("type=============：{}", type.toString());
      List<Map<String, Object>> imgList = new ArrayList< Map<String, Object>>();
      /*  String resTypeId = request.getHeader("resTypeId");
        String resType= request.getHeader("resType");*/
      String pageId = "" ;
      String canteenId = "";
      try {
          canteenId = canteenMapper.getCanteenIdByResTypeId(type);
          pageId = resTypeMapper.getPageIdByResTypeId(type);
      } catch (Exception e) {

      }

      String codeOne  = "";
      String codeTwo = "" ;
      if (canteenId != null&&!"".equals(canteenId)) {
          codeOne = canteenId.substring(0, 4).toLowerCase();
      } else {
          codeOne  = ConstantsCode.LSCT;
      }

      if (pageId != null&&!"".equals(pageId)) {
          codeTwo = pageId;
      } else {
          codeTwo = ConstantsCode.GENERAL;
      }

      StringBuffer path = new StringBuffer("../webapps/upload/images/");
      path.append(codeOne).append("/").append(codeTwo).append("/");

      File targetFile = new File(String.valueOf(path), "");
      if (!targetFile.exists()) {
          targetFile.mkdirs();
      }

        log.info("fileName==========：{}", files[0].getOriginalFilename());

        List<UploadLog> uploadLogs = new ArrayList<>();
        try {

            for (int i = 0; i < files.length; i++) {
                StringBuffer stringBuffer = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"images/");
                StringBuffer stringBufferSmall = new StringBuffer(ConstantsCode.YUN_UPDATE_URL+"images/");
                String fileName = files[i].getOriginalFilename();
                log.info("fileName=======：{}", fileName.toString());
                String fName = "";
                String suffix;
                String realPath = "";
                String smallRealPath = "";
                Img img = new Img();
                UploadLog uploadLog = new UploadLog();
                // StringBuffer stringBuffer = new StringBuffer("YUN_UPDATE_URL");
                if (fileName.lastIndexOf(".") >= 0) {
                    int indexDot = fileName.lastIndexOf(".");
                    suffix = fileName.substring(indexDot);
                    String prefix = RandomUtil.getRandomCodeStr(2);
                    fName = prefix + suffix;
                    realPath = stringBuffer.append(codeOne).append("/").append(codeTwo).append("/").append(fName).toString();
                    smallRealPath = stringBufferSmall.append(codeOne).append("/").append(codeTwo).append("/").append("small"+fName).toString();
                }

                // 先尝试压缩并保存图片
                //    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.25f).toFile(path + fName);
                OutputStream os = new FileOutputStream(path + fName);
                //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
                InputStream is = files[i].getInputStream();
                int temp;
                //一个一个字节的读取并写入
                while ((temp = is.read()) != (-1)) {
                    os.write(temp);
                }
                os.flush();
                os.close();
                is.close();

               Thumbnails.of(files[i].getInputStream()).size(40, 60).toFile(path +"small"+fName);

                img.setImgUrl(realPath);
                img.setResTypeId(type);
                imgService.addOrUpdateImg(img);

                uploadLog.setCreateTime(new Date());
                uploadLog.setImgUrl(smallRealPath);
                uploadLog.setCreateUser(user.getUsername());
                uploadLog.setTypeName(resTypeMapper.getResTypeNameById(type));
                uploadLogMapper.insertSelective(uploadLog);
                uploadLogs.add(uploadLog);

            }
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("list",uploadLogs);
            log.info("map===="+JSON.toJSON(map));
            lsyResponse.setData(map);

        } catch (IOException e) {
            log.info("Thumbnails压缩出错===============：{}", e.getMessage());
            try {
                // 失败了再用springmvc自带的方式
                for (int i = 0; i < files.length; i++) {
                    files[i].transferTo(targetFile);
                }

            } catch (IOException e1) {
                log.info("springmvc自带上传图片出错===========：{}", e1.getMessage());
            }
        } catch (Exception e2) {
            log.info("exception===========：{}", e2.getMessage());
        }
        log.info("code===="+lsyResponse.getCode());
        log.info("lsyRes==="+ JSON.toJSONString(lsyResponse));
      return lsyResponse;
  }






}