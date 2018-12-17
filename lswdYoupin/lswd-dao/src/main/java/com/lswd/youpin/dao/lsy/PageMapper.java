package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.PageMapperGen;
import com.lswd.youpin.model.lsy.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageMapper extends PageMapperGen {


    List<Page> getPageList(@Param(value = "keyword") String keyword,
                           @Param(value = "machineId") Integer machineId,
                           @Param(value = "pageSize") Integer pageSize,
                           @Param(value = "offset") Integer offset
    );


    Integer getPageCount(@Param(value = "keyword") String keyword,
                         @Param(value = "machineId") Integer machineId
    );

    Integer delPage(@Param(value = "id") Integer id);

    //app--------------------
    Integer getPageIdByImgUrl(@Param(value = "requestUrl") String requestUrl);


}