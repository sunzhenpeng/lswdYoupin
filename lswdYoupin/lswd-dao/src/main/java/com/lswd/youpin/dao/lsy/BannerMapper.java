package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.BannerMapperGen;
import com.lswd.youpin.model.lsy.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper extends BannerMapperGen {
    List<Banner> getBannerList(@Param(value = "keyword") String keyword,
                               @Param(value = "pageId") Integer pageId,
                               @Param(value = "machineId") Integer machineId,
                               @Param(value = "canteenIds") String[] canteenIds,
                               @Param(value = "pageSize") Integer pageSize,
                               @Param(value = "offset") Integer offset
    );


    Integer getBannerCount(@Param(value = "keyword") String keyword,
                           @Param(value = "pageId") Integer pageId,
                           @Param(value = "machineId") Integer machineId,
                           @Param(value = "canteenIds") String[] canteenIds
    );

    Integer delBanner(@Param(value = "id") Integer id);

    //app-------------------------------------

    String getBannerUrlByMachineNo(@Param(value = "machineId") Integer machineId,
                                  @Param(value = "pageId") Integer pageId);

}