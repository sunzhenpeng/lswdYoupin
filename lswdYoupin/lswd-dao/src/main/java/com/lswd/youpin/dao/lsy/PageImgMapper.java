package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.PageImgMapperGen;
import com.lswd.youpin.model.lsy.PageImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PageImgMapper extends PageImgMapperGen {


    List<PageImg> getPageImgList(@Param(value = "keyword") String keyword,
                                 @Param(value = "pageId") Integer pageId,
                                 @Param(value = "machineId") Integer machineId,
                                 @Param(value = "canteenIds") String[] canteenIds,
                                 @Param(value = "pageSize") Integer pageSize,
                                 @Param(value = "offset") Integer offset
    );


    Integer getPageImgCount(@Param(value = "keyword") String keyword,
                            @Param(value = "pageId") Integer pageId,
                            @Param(value = "machineId") Integer machineId,
                            @Param(value = "canteenIds") String[] canteenIds
    );


    Integer delPageImg(@Param(value = "id") Integer id);


    //app----------------------
    List<String> getPageImgUrlByMachineNo(@Param(value = "machineId") Integer machineId,
                                             @Param(value = "pageId") Integer pageId,
                                           @Param(value = "updateTime") String updateTime
    );

    List<Map<String,Object>> getPageImgListByMachineNo(@Param(value = "machineId") Integer machineId,
                                        @Param(value = "pageId") Integer pageId
    );

    String getImgUrlByMachineNo(@Param(value = "machineId") Integer machineId,
                                          @Param(value = "pageId") Integer pageId
    );

    List<String> getPageImg(@Param(value = "machineId") Integer machineId,
                               @Param(value = "pageId") Integer pageId
    );
    List<Map<String,Object>> getIdAndImg(@Param(value = "machineId") Integer machineId,
                            @Param(value = "pageId") Integer pageId
    );


}