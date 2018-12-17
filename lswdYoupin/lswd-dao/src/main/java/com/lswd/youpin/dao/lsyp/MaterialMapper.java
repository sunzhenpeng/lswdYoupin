package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.MaterialCategory;
import com.lswd.youpin.model.lsyp.Unit;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.List;

/**
 * Created by liuhao on 2017/6/20.
 */
public interface MaterialMapper {
//    Integer insertMaterial(Material material);
//
//    Integer deleteById(Material material);
//
//    Integer updateById(Material material);
//
    Integer getMaterialCount(@Param(value = "keyword") String keyword,
                         @Param(value = "canteenIds") List<String> canteenIds,
                         @Param(value = "categoryId") int categoryId,
                         @Param(value = "supplierId") String supplierId);
//
    List<Material> selectMaterials(@Param(value = "keyword")String keyword,@Param(value = "offSet") int offSet,
                                   @Param(value = "pageSize") Integer pageSize,
                                   @Param(value = "canteenIds") List<String> canteenIds,
                                   @Param(value = "categoryId") int categoryId,
                                   @Param(value = "supplierId") String supplierId);
//
    Material selectById(@Param(value = "id") Integer id);

    List<MaterialCategory> selectCategoryList(@Param(value = "canteenId") String canteenId,
                                              @Param(value = "pageSize") Integer pageSize,@Param(value = "offset") Integer offset,
                                              @Param(value = "keyword") String keyword);
//
//
    MaterialCategory getMaterialCategoryByName(@Param(value = "name") String name);
//
//
//    List<MaterialCategory> getMaterialCategoryAll();
//
    List<Unit> getUnits();
//
    Unit getUnitByName(@Param(value = "name")String name);
//
    Integer getLastId();

    Integer insertMaterial(Material material);

    Integer insertMaterialSupplier(@Param(value = "materialId") String materialId,@Param(value = "supplierId") String supplierId,@Param(value = "price") Double price);

    Integer deleteById(Material material);

    Integer updateById(Material material);

//
//    Integer insertMaterialSuplier(@Param(value = "materialId") String materialId,
//                              @Param(value = "supplierId") String supplierId,
//                              @Param(value = "price") Double price);
//
    Integer updateMaterialSupplier(@Param(value = "materialId") String materialId,@Param(value = "supplierId") String supplierId,
                               @Param(value = "price") Double price);

    Integer addCategory(MaterialCategory category);

    Integer getCategoryCount(@Param(value = "canteenId") String canteenId, @Param(value = "keyword") String keyword);


    MaterialCategory selectCategoryByName(@Param(value = "name") String name,@Param(value = "canteenId") String canteenId);

    Integer delCategoryById(@Param(value = "id")Integer id);


    List<MaterialCategory> categoryAll(@Param(value = "canteenId") String canteenId);

    Integer deleteMaterialByCategoryId(@Param(value = "CategoryId") Integer CategoryId);
}
