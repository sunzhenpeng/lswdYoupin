package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.CanteenMapper;
import com.lswd.youpin.dao.ShowImagesMapper;
import com.lswd.youpin.dao.UserMapper;
import com.lswd.youpin.dao.lsyp.CanteenSupplierMapper;
import com.lswd.youpin.dao.lsyp.RateMapper;
import com.lswd.youpin.dao.lsyp.SupplierMapper;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.ShowImages;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Rate;
import com.lswd.youpin.model.lsyp.Supplier;
import com.lswd.youpin.model.vo.CanteenHome;
import com.lswd.youpin.model.vo.CanteenImageVO;
import com.lswd.youpin.model.vo.CanteenVO;
import com.lswd.youpin.model.vo.SupplierVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.SerializeUtils;
import com.lswd.youpin.utils.StringsUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/6/8.
 */
@Service
public class CanteenServiceImpl implements CanteenService {
    private final Logger log = LoggerFactory.getLogger(CanteenServiceImpl.class);

    @Autowired
    private CanteenMapper canteenMapper;

    @Autowired
    private CanteenSupplierMapper canteenSupplierMapper;

    @Autowired
    private RateMapper rateMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ShowImagesMapper showImagesMapper;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询区域下所有的餐厅
     *
     * @param institutionId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public LsResponse getAllCanteenByInstitutionId(String institutionId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. Canteen = {}", "getAllCanteenByInstitutionId", JSON.toJSON(institutionId));
        LsResponse lsResponse = new LsResponse();
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            List<Canteen> canteens = canteenMapper.getAllCanteenByInstitutionId(institutionId, offset, pageSize);
            lsResponse.setData(canteens);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            log.error("餐厅获取失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateCanteen(CanteenVO canteenVO, User u, HttpServletRequest request) {
        log.info("{} is being executed. Canteen = {}", "addOrUpdateCanteen", JSON.toJSON(canteenVO));
        LsResponse lsResponse = new LsResponse();
        //餐厅信息
        Canteen canteen = canteenVO.getCanteen();
        canteen.setUpdateTime(Dates.now());
        canteen.setUpdateUser(u.getUsername());
        Rate trate = new Rate();
        if (canteen.getId() == null) {
            Integer maxId = canteenMapper.getMaxId();
            if (maxId == null) {
                maxId = 0;
            }
            String suffix = String.valueOf(Integer.parseInt("100001") + maxId);
            String canteenId = u.getTenantId().substring(0, 4) + suffix;
            canteen.setCreateTime(Dates.now());
            canteen.setCanteenId(canteenId);
            canteen.setTenantId(u.getTenantId());
            Double rate = canteenVO.getRate();
            //平台费率信息
            trate.setCanteenId(canteen.getCanteenId());
            trate.setRate(rate);
            trate.setUpdateTime(Dates.now());
            trate.setUpdateTime(Dates.now());
            trate.setUpdateUser(u.getUsername());
            try {
                canteenMapper.insertSelective(canteen);
                rateMapper.insertSelective(trate);
                u.setCanteenIds(u.getCanteenIds() + "," + canteenId);
                String token = request.getHeader("token");
                redisManager.set(token.getBytes(), SerializeUtils.serialize(u));
                userMapper.updateByPrimaryKeySelective(u);
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                lsResponse.setMessage(CodeMessage.CANTEEN_ADD_ERR.getMsg());
                lsResponse.setAsFailure();
                log.error("新建餐厅失败:{}", e.getMessage());
            }
        } else {
            try {
                canteen.setUpdateTime(Dates.now());
                canteen.setUpdateUser(u.getUsername());
                canteenMapper.updateByPrimaryKeySelective(canteen);
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                lsResponse.setMessage(CodeMessage.CANTEEN_UPDATE_ERR.getMsg());
                lsResponse.setAsFailure();
                log.error("新建更新失败:={}", e.getMessage());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenByCanteenId(String canteenId) {
        log.info("{} is being executed. Canteen = {}", "getCanteenByCanteenId", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        try {
            Canteen canteen = canteenMapper.getCanteenByCanteenId(canteenId);
            lsResponse.setData(canteen);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenPart(String canteenId) {
        log.info("{} is being executed. Canteen = {}", "getCanteenByCanteenId", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        try {
            Canteen canteen = canteenMapper.getCanteenPart(canteenId);
            lsResponse.setData(canteen);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败:={}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 获取用户管理的餐厅
     *
     * @param canteenIds
     * @return
     */
    @Override
    public LsResponse getUserCanteenList(String canteenIds) {
        log.info("{} is being executed. Canteen = {}", "getUserCanteenList", JSON.toJSON(canteenIds));
        LsResponse lsResponse = new LsResponse();
        try {
            String[] ids = canteenIds.split(",");
            List<Canteen> canteen = canteenMapper.getUserCanteenList(ids);
            lsResponse.setData(canteen);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAllCanteenPart(User u, String institutionId, String canteenName, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. Canteen-getAllCanteenPart= {}");
        LsResponse lsResponse = new LsResponse();
        String[] canteenIds = u.getCanteenIds().split(",");
        Integer offset = null;
        int count;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            canteenName= StringsUtil.encodingChange(canteenName);
            count = canteenMapper.getTotalCountWithinTenant(canteenIds, u.getTenantId(), institutionId, canteenName);
            List<Canteen> canteens = canteenMapper.getCanteenPartList(canteenIds, u.getTenantId(), institutionId, canteenName, offset, pageSize);
            lsResponse.setData(canteens);
            lsResponse.setTotalCount(count);
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败:={}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 为餐厅添加供应商
     *
     * @param
     * @return
     */
    @Override
    public LsResponse addCanteenSupplierLink(SupplierVO supplierVO) {
        log.info("{} is being executed. Canteen = {}", "addCanteenSupplierLink", JSON.toJSON(supplierVO));
        LsResponse lsResponse = new LsResponse();
        String canteenId = supplierVO.getCanteenId();
        List<Supplier> suppliers = supplierVO.getSuppliers();
        try {
            //先删除原来的绑定关系
            for (Supplier supplier : suppliers) {
                if (!supplier.isChecked()) {
                    canteenSupplierMapper.deleteCanteenSupplierLink(canteenId, supplier.getSupplierId());
                }
            }
            //添加新的关联关系
            for (Supplier supplier : suppliers) {
                if (supplier.isChecked()) {
                    canteenSupplierMapper.insertCanteenSupplierLink(canteenId, supplier.getSupplierId());
                }
            }
            List<Supplier> supplierList = supplierMapper.getCanteenLinkSupplierByCanteenId(canteenId);
            lsResponse.setData(supplierList);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.setMessage(CodeMessage.CANTEEN_ADD_SUPPLIER_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_ADD_SUPPLIER_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("添加供应商失败:={}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 根据餐厅名称查询餐厅
     *
     * @param canteenName
     * @return
     */
    @Override
    public LsResponse getCanteenListByCanteenName(String canteenName) {
        log.info("{} is being executed. Canteen = {}", "getCanteenListByCanteenName", JSON.toJSON(canteenName));
        LsResponse lsResponse = new LsResponse();
        try {
            canteenName= StringsUtil.encodingChange(canteenName);
            List<Canteen> canteens = canteenMapper.getCanteenListByCanteenName(canteenName);
            lsResponse.setData(canteens);
        } catch (Exception e) {
            log.error("获取餐厅信息失败={}", e.getMessage());
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getTenantAssociatorCanteenList(String associatorId) {
        log.info("传入的小程序会员编号是=============" + associatorId);
        LsResponse lsResponse = new LsResponse();
        try {
            List<Canteen> canteens = canteenMapper.getTenantAssociatorCanteenList(associatorId);
            lsResponse.setData(canteens);
        } catch (Exception e) {
            log.error("获取餐厅信息失败={}", e.getMessage());
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAssociatorCanteenList(String associatorId) {
        log.info("传入的H5会员编号是=============" + associatorId);
        LsResponse lsResponse = new LsResponse();
        try {
            List<Canteen> canteens = canteenMapper.getAssociatorCanteenList(associatorId);
            lsResponse.setData(canteens);
        } catch (Exception e) {
            log.info("获取会员绑定的餐厅列表失败={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getNearestCanteen(String associatorId) {
        log.info("is being executed. getNearestCanteen= {}", JSON.toJSON(associatorId));
        LsResponse lsResponse = new LsResponse();
        boolean flag;
        try {
            Canteen canteen = canteenMapper.getNearestCanteen(associatorId);
            flag = canteen == null ? false : true;
            lsResponse.setSuccess(flag);
            lsResponse.setData(canteen);
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setMessage("获取用户最近登录的餐厅失败" + e.getMessage());
            log.info("获取用户最近登录的餐厅失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateShowImages(List<ShowImages> showImages, User u) {
        log.info("is being executed. addOrUpdateShowImages= {}", JSON.toJSON(showImages));
        LsResponse lsResponse = new LsResponse();
        String tenantId = u.getTenantId();
        String path = "../webapps/images/" + tenantId.substring(0, 4).toLowerCase();
        try {
            for (ShowImages img : showImages) {
                if (img.getId() == null) {
                    showImagesMapper.insertSelective(img);
                } else {
                    showImagesMapper.updateByPrimaryKey(img);
                    if (img.getOldImgs() != null) {
                        for (int i = 0; i < img.getOldImgs().size() - 1; i++) {
                            int indexDot = img.getOldImgs().get(i).lastIndexOf("/");
                            File file = new File(path, img.getOldImgs().get(i).substring(indexDot));
                            file.delete();
                        }
                    }
                }
            }
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            log.info("更换首页图片出错={}", e.getMessage());
            lsResponse.setMessage(e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getImageWeb(String canteenId) {
        log.info("is being executed. getImageWeb= {}", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        try {
            List<ShowImages> showImages = showImagesMapper.getImagesWeb(canteenId);
            lsResponse.setData(showImages);
        } catch (Exception e) {
            log.info("WEB获取餐厅首页图片信息失败={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("WEB获取餐厅首页图片信息失败" + e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getImageH5(String canteenId) {
        log.info("is being executed. getImageH5= {}", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        try {
            List<CanteenImageVO> showImages = showImagesMapper.getImagesH5(canteenId);
            Canteen canteen = canteenMapper.getCanteenByCanteenId(canteenId);
            CanteenHome canteenHome = new CanteenHome();
            if (canteen != null) {
                canteenHome.setImageUrl(canteen.getImageUrl());
                canteenHome.setCanteenName(canteen.getCanteenName());
                canteenHome.setAddress(canteen.getAddress());
            }
            Map<String, Object> map = new HashedMap();
            map.put("images", showImages);
            map.put("canteen", canteenHome);
            lsResponse.setData(map);
        } catch (Exception e) {
            log.info("H5获取餐厅首页图片信息失败={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("H5获取餐厅首页图片信息失败" + e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteImage(ShowImages image, User u) {
        log.info("is being executed. deleteImage= {}", JSON.toJSON(image));
        LsResponse lsResponse = new LsResponse();
        String tenantId = u.getTenantId();
        String path = "../webapps/images/" + tenantId.substring(0, 4).toLowerCase();
        int indexDot = image.getNewImg().lastIndexOf("/");
        try {
            boolean b = showImagesMapper.deleteByPrimaryKey(image.getId()) >= 0 ? true : false;
            File file = new File(path, image.getNewImg().substring(indexDot));
            file.delete();
            lsResponse.setSuccess(b);
        } catch (Exception e) {
            log.info("删除图片失败：={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("微信首页删除图片失败");
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPayType(String canteenId) {
        log.info("is being executed. getPayType= {}", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        try {
            boolean b = canteenMapper.getPayType(canteenId);
            lsResponse.setAsSuccess();
            lsResponse.setData(b);
        } catch (Exception e) {
            log.info("获取餐厅信息失败={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
        }
        return lsResponse;
    }


    /**
     * 获取所有的餐厅
     *
     * @param canteenName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public LsResponse getAllCanteen(String canteenIds, String institutionId, String canteenName, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. Canteen-getAllCanteen= {}", JSON.toJSON(canteenName));
        log.info("从user 中获取到的canteenIds是==============" + canteenIds);
        LsResponse lsResponse = new LsResponse();
        String[] ids = canteenIds.split(",");
        Integer offset = null;
        int count;
      //  String name = "";
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
          /*  if (canteenName != null && !(canteenName.equals(""))) {
                String tmp = URLDecoder.decode(canteenName, "UTF-8");
                name = new String(tmp.getBytes("ISO-8859-1"), "UTF-8");
            }*/
            canteenName=StringsUtil.encodingChange(canteenName);
            count = canteenMapper.getTotalCount(ids, institutionId, canteenName);
            List<Canteen> canteens = canteenMapper.getCanteenList(ids, institutionId, canteenName, offset, pageSize);
            log.info("获取到的餐厅列表是=========" + JSON.toJSON(canteens) + "餐厅的总条数是=================" + count);
            lsResponse.setData(canteens);
            lsResponse.setTotalCount(count);
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败:={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenListByLocation(String associatorId, Double longitude, Double latitude) {
        log.info("{} is being executed. getCanteenListByLocation= {},={}", JSON.toJSON(longitude), JSON.toJSON(latitude));
        log.info("longitude=============================" + longitude);
        log.info("latitude==============================" + latitude);
        LsResponse lsResponse = new LsResponse();
        List<Canteen> canteens = new ArrayList<>();
        try {
            //List<Canteen> associatorBindCanteen = canteenMapper.getAssociatorCanteenList(associatorId);
            //if (associatorBindCanteen.size() < 1) {
            // log.info("当前会员没有绑定任何餐厅，默认根据会员当前的地理位置检索餐厅");
            if (longitude == null || latitude == null) {
                //List<Canteen> cans = canteenMapper.getCanteenPartList(null, null, null, null, 0, 10);
                //lsResponse.setData(cans);
                lsResponse.setAsFailure();
                lsResponse.setMessage("附近没有餐厅，请手动搜索");
                return lsResponse;
            }
            List<Canteen> canteenLocations = canteenMapper.getCanteenByLocation(longitude, latitude);
            if (canteenLocations.size() < 0 || canteenLocations == null) {
                lsResponse.setAsFailure();
                lsResponse.setMessage("附近没有餐厅，请手动搜索");
                return lsResponse;
            }
            for (Canteen can : canteenLocations) {
                if (can.getDistance() < 3000) {
                    canteens.add(can);
                }
            }
            lsResponse.setData(canteens);
            lsResponse.setAsSuccess();
           /* } else {
                for (Canteen canteen : associatorBindCanteen) {
                    canteen.setCanteenType(ConstantsCode.OPEN);
                }
                lsResponse.setData(associatorBindCanteen);
                lsResponse.setAsSuccess();
            }*/
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_LOCATION_ERR.getMsg());
            lsResponse.setAsFailure();
            log.error(CodeMessage.CANTEEN_SELECT_LOCATION_ERR.getMsg() + "={}", e.getMessage());
            log.error("餐厅查询失败：={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenIdByResTypeId(Integer resTypeId) {
        log.info("{} is being executed. Canteen == {}", "getCanteenByCanteenId", JSON.toJSON(resTypeId));
        LsResponse lsResponse = new LsResponse();
        String canteenId   = "";
        try {
            if (resTypeId != null) {
                canteenId = canteenMapper.getCanteenIdByResTypeId(resTypeId);
                lsResponse.setData(canteenId);
                lsResponse.setAsSuccess();
            } else {
                lsResponse.checkSuccess(false, CodeMessage.PARAM_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.CANTEEN_SELECT_ERR.getCode());
            lsResponse.setAsFailure();
            log.error("餐厅查询失败=={}", e.getMessage());
        }
        return lsResponse;
    }
}
