package com.lswd.youpin.service;

import com.lswd.youpin.model.ShowImages;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.CanteenVO;
import com.lswd.youpin.model.vo.SupplierVO;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liruilong on 2017/6/8.
 */
public interface CanteenService {
    //查询区域下所有的餐厅
    LsResponse getAllCanteenByInstitutionId(String institutionId, Integer pageNum, Integer pageSize);

    //新建或者更新餐厅
    LsResponse addOrUpdateCanteen(CanteenVO canteenVO, User u, HttpServletRequest request);

    //餐厅详情，根据餐厅编号查询餐厅信息
    LsResponse getCanteenByCanteenId(String canteenId);

    //获取所有的餐厅，如果餐厅名称则按餐厅名称进行查询
    LsResponse getAllCanteen(String canteenIds, String institutionId, String canteenName, Integer pageNum, Integer pageSize);

    //根据当前地理位置检索餐厅
    LsResponse getCanteenListByLocation(String associatorId, Double longitude, Double latitude);

    LsResponse getCanteenPart(String canteenId);

    LsResponse getUserCanteenList(String canteenIds);

    LsResponse getAllCanteenPart(User u, String institutionId, String canteenName, Integer pageNum, Integer pageSize);

    LsResponse addCanteenSupplierLink(SupplierVO supplierVO);

    LsResponse getCanteenListByCanteenName(String canteenName);

    LsResponse getTenantAssociatorCanteenList(String tenantAssociatorId);

    LsResponse getAssociatorCanteenList(String associatorId);

    LsResponse getNearestCanteen(String associatorId);

    LsResponse addOrUpdateShowImages(List<ShowImages> showImages, User u);

    LsResponse getImageWeb(String canteenId);

    LsResponse getImageH5(String canteenId);

    LsResponse deleteImage(ShowImages image, User u);

    LsResponse getPayType(String canteenId);

    LsResponse getCanteenIdByResTypeId(Integer resTypeId);



}
