package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.ImgMapperGen;
import com.lswd.youpin.model.lsy.Img;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImgMapper extends ImgMapperGen {

      List<Img> getImgList(@Param(value = "keyword") String keyword,
                           @Param(value = "resTypeId") Integer resTypeId,
                           @Param(value = "canteenIds") String[] canteenIds,
                           @Param(value = "updateTime") String updateTime,
                           @Param(value = "pageSize") Integer pageSize,
                           @Param(value = "offset") Integer offset);

    Integer getImgCount(@Param(value = "keyword") String keyword,
                        @Param(value = "resTypeId") Integer resTypeId,
                        @Param(value = "canteenIds") String[] canteenIds,
                        @Param(value = "updateTime") String updateTime);

    Integer delImg(@Param(value = "id") Integer id);

    //app---------------------


  /*  List<String> getImgListByMachineNo(@Param(value = "machineId") Integer machineId,
                                   @Param(value = "pageId") Integer pageId);*/


    List<String> getImgListByResTyId(@Param(value = "resTypeId") Integer resTypeId,
                                     @Param(value = "updateTime") String updateTime
                                     );




}