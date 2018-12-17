package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.SupplierMapperGen;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierMapper extends SupplierMapperGen {

    List<Supplier> getCanteenLinkSupplierByCanteenId(@Param("canteenId") String canteenId);

    Integer insertSupplier(Supplier supplier);

    Integer deleteById(Supplier supplier);

    Integer updateSupplier(Supplier supplier);

    Integer getSupplierCount(@Param(value = "keyword") String keyword);

    List<Supplier> selectSuppliers(@Param(value = "keyword")String keyword, @Param(value = "pageSize") Integer pageSize
            , @Param(value ="offSet" ) int offSet);

    Supplier selectById(Integer id);

    List<Supplier> getSuppliers();

    Supplier selectBySupplierId(@Param(value = "supplierId") String supplierId);

    List<Supplier> getSuppliersByCanteenId(@Param(value = "canteenId") String canteenId);

    Integer selLastSupplierId();

    Integer getSuplierMaterialCount(@Param(value = "supplierId") String supplierId, @Param(value = "keyword") String keyword);

    List<Material> getSuplierMaterials(@Param(value = "supplierId") String supplierId, @Param(value = "keyword") String keyword,
                                       @Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize);

}