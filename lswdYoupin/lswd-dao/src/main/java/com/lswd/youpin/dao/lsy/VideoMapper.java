package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.VideoMapperGen;
import com.lswd.youpin.model.lsy.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VideoMapper extends VideoMapperGen {


    List<Video> getVideoList(@Param(value = "keyword") String keyword,
                             @Param(value = "pageId") Integer pageId,
                             @Param(value = "machineId") Integer machineId,
                             @Param(value = "canteenIds") String[] canteenIds,
                             @Param(value = "pageSize") Integer pageSize,
                             @Param(value = "offset") Integer offset
    );


    Integer getVideoCount(@Param(value = "keyword") String keyword,
                          @Param(value = "pageId") Integer pageId,
                          @Param(value = "machineId") Integer machineId,
                          @Param(value = "canteenIds") String[] canteenIds
    );

    Integer delVideo(@Param(value = "id") Integer id);


    //app----------------------
    List<Map<String, Object>> getVideoListByMachineNo(@Param(value = "machineId") Integer machineId,
                                                      @Param(value = "pageId") Integer pageId);

    String getVideoUrlByMachineNo(@Param(value = "machineId") Integer machineId,
                                  @Param(value = "pageId") Integer pageId);

    String getVideoByMachineNo(@Param(value = "id") Integer id);
}