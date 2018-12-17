package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodMapperGen;
import com.lswd.youpin.model.lsyp.Good;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodMapper extends GoodMapperGen {

    int insertGood(Good good);

    int deleteGoodCancel(@Param(value = "id") Integer id);//商品列表不显示该商品，不是删除，因为之前的商品关联到了订单

    int delGoodDownById(@Param(value = "id") Integer id);//商品下架,修改status == 2 ，is_delete = 1

    int updateGoodIsDeleteById(@Param(value = "id") Integer id);//商品过期，是否删除，修改good表中的 is_delete 字段为1，1代表已删除

    int updateGood(Good good);//根据商品编号进行修改

    int getGoodCount(@Param(value = "keyword") String keyword,@Param(value = "supplierId") String supplierId,
                     @Param(value = "categoryId") Integer categoryId,@Param(value = "canteenId") String canteenId,@Param(value = "canteenIds") String[] canteenIds);

    List<Good> getGoodList(@Param(value = "keyword") String keyword, @Param(value = "supplierId") String supplierId,
                              @Param(value = "categoryId") Integer categoryId,@Param(value = "canteenId") String canteenId,@Param(value = "canteenIds") String[] canteenIds,
                              @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    Good getGoodByGoodId(@Param(value = "goodid") String goodid);//根据商品编号查找商品

    Integer getMaxId();//获取商品最大id

    /*商品计划新增时需要请求的方法*/
    int getGoodMINICount(@Param(value = "keyword") String keyword,@Param(value = "supplierId") String supplierId,
                         @Param(value = "categoryId") Integer categoryId,@Param(value = "canteenId") String canteenId,@Param(value = "pickingTime") String pickingTime);

    List<Good> getGoodMINIList(@Param(value = "keyword") String keyword, @Param(value = "supplierId") String supplierId,
                               @Param(value = "categoryId") Integer categoryId,@Param(value = "canteenId") String canteenId,@Param(value = "pickingTime") String pickingTime,
                               @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

    int updateGoodSurPlus(@Param(value = "goodId")String goodId,@Param(value = "surplus")Integer surplus);
}