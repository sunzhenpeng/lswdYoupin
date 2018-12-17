package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.LabelMapperGen;
import com.lswd.youpin.model.lsyp.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelMapper extends LabelMapperGen {
    List<Label> getLabelInfo(@Param("uIds") List<String> uid);

    int updateDiskRecipe(@Param("recipeId") String recipeId, @Param(value = "diskTypeId") Integer diskTypeId, @Param("canteenId") String canteenId);

    List<Label> getDiskLabelByDiskTypeId(@Param(value = "diskTypeId") Integer diskTypeId);

    int updateSingleDisk(@Param(value = "uId") String uId, @Param("recipeId") String recipeId);

    int updateLabel(@Param("canteenId") String canteenId, @Param("diskTypeId") Integer diskTypeId, @Param("recipeId") String recipeId);

    List<Label> getLabels(@Param("canteenId") String canteenId, @Param("diskTypeId") Integer diskTypeId);

    Integer deleteByTypeId(@Param(value = "typeId")Integer typeId);
}