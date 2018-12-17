package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.DiskRecipeMapperGen;
import com.lswd.youpin.model.lsyp.DiskRecipe;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DiskRecipeMapper extends DiskRecipeMapperGen {

    List<DiskRecipe> getDiskRecipe(@Param("keyword") String keyword,@Param("canteenId") String canteenId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    int getDiskRecipeCount(@Param("keyword") String keyword,@Param("canteenId") String canteenId);

    int deleteDiskRecipe(@Param("canteenId") String canteenId,@Param("diskTypeId") Integer diskTypeId);

    List<DiskRecipe> getAll(@Param("canteenId") String canteenId);

    List<DiskRecipe> getDiskRecipeByDiskTypeId(Integer diskTypeId);
}