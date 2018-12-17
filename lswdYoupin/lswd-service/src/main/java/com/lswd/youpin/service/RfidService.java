package com.lswd.youpin.service;

import com.lswd.youpin.model.lsyp.DiskRecipe;
import com.lswd.youpin.model.vo.LabelVO;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/11/24.
 */
public interface RfidService {
    LsResponse getLabel();

    LsResponse add(LabelVO labelVO);

    LsResponse getLabelInfoByUid();

    LsResponse update(DiskRecipe diskRecipe);

    LsResponse getDiskRecipe(String keyword, String canteenId, Integer pageNum, Integer pageSize);

    LsResponse deleteDiskRecipe(String canteenId, Integer diskTypeId);

    void updateDiskRecipe(int mealType, String canteenId);

    LsResponse updateSingleDisk(String uId, String recipeId);

    LsResponse getLabelStatistics(String canteenId, Integer diskTypeId);
}
