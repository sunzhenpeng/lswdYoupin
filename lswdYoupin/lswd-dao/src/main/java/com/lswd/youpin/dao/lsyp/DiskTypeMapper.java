package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.DiskTypeMapperGen;
import com.lswd.youpin.model.lsyp.DiskLabel;
import com.lswd.youpin.model.lsyp.DiskType;
import org.apache.ibatis.annotations.Param;
import org.w3c.dom.ls.LSException;

import java.util.List;

public interface DiskTypeMapper extends DiskTypeMapperGen {


    int getDiskTypeListCount(@Param(value = "name")String name,@Param(value = "canteenId")String canteenId);

    List<DiskType> getDiskTypeList(@Param(value = "name")String name,@Param(value = "canteenId")String canteenId,
                                    @Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

    List<DiskType> checkName(@Param(value = "name")String name,@Param(value = "canteenId")String canteenId);

    List<DiskType> getDiskTypeListAll(@Param(value = "canteenId")String canteenId);

    Integer getLabelByTypeIdCount(@Param(value = "typeId")Integer typeId ,@Param(value = "keyword")String keyword);

    List<DiskLabel> getLabelByTypeId(@Param(value = "typeId")Integer typeId ,@Param(value = "keyword")String keyword,
                                     @Param(value = "offSet")Integer offSet,@Param(value = "pageSize")Integer pageSize);

}