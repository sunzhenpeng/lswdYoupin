package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.DiskType;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/11/15
 */
public interface DiskTypeService {

    LsResponse getDiskTypeList(String name, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse addDiskType(DiskType diskType, User user);

    LsResponse updateDiskType(DiskType diskType, User user);

    LsResponse deleteDiskType(Integer id);

    LsResponse getDiskTypeListAll(String canteenId);

    LsResponse getLabelByTypeId(Integer typeId, String keyword, Integer pageNum, Integer pageSize);

    LsResponse deleteLabelByTypeId(Integer typeId);

}
