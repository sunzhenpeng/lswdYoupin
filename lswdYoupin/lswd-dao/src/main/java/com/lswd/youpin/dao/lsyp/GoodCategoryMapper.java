package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodCategoryMapperGen;
import com.lswd.youpin.model.lsyp.Good;
import com.lswd.youpin.model.lsyp.GoodCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodCategoryMapper extends GoodCategoryMapperGen {

    int getFirstGoodCategoryCount(@Param(value = "name")String name);

    List<GoodCategory> getFirstGoodCategory(@Param(value = "name")String name, @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    List<GoodCategory> getSecondGoodCategory(@Param(value = "name")String name, @Param(value = "id")Integer id);

    List<GoodCategory> getGoodCategoryListAll(@Param(value = "canteenId")String canteenId);

    int getGoodCategoryWebCount(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId);

    List<GoodCategory> getGoodCategoryWeb(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId,
                                                @Param(value = "offSet")Integer offSet, @Param(value = "pageSize")Integer pageSize);

    List<GoodCategory> getGoodCategoryListH5(@Param(value = "canteenId")String canteenId);

    GoodCategory checkOutName(@Param(value = "keyword")String keyword,@Param(value = "canteenId")String canteenId);
}