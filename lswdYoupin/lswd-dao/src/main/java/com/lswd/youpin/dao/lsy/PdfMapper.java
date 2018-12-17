package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.PdfMapperGen;
import com.lswd.youpin.model.lsy.Pdf;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PdfMapper extends PdfMapperGen {

    List<Pdf> getPdfList(@Param(value = "keyword") String keyword,
                         @Param(value = "resTypeId") Integer machineId,
                         @Param(value = "canteenIds") String[] canteenIds,
                         @Param(value = "pageSize") Integer pageSize,
                         @Param(value = "offset") Integer offset
    );


    Integer getPdfCount(@Param(value = "keyword") String keyword,
                        @Param(value = "resTypeId") Integer machineId,
                        @Param(value = "canteenIds") String[] canteenIds

    );
    Integer delPdf(@Param(value = "id") Integer id);

    //app--------------------------------------

    String getPdfUrlByMachineNo(@Param(value = "machineId") Integer machineId,
                                  @Param(value = "resTypeId") Integer resTypeId);

    String getPdfUrlByPageImgId(@Param(value = "pageImgId") Integer pageImgId);






}