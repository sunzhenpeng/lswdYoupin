package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.InstitutionMapperGen;
import com.lswd.youpin.model.Role;
import com.lswd.youpin.model.lsyp.Institution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstitutionMapper extends InstitutionMapperGen {
    List<Institution> getInstitutionByTenantId(@Param("tenantId") String tenantId);

    Integer deleteInstitution(@Param("institutionId") String institutionId);

    List<Institution> getInstitutionList(@Param("tenantId") String tenantId, @Param("institutionName") String institutionName, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Integer getTotalCount(@Param("tenantId") String tenantId, @Param("institutionName") String institutionName);

    Institution getInstitutionByInstitutionId(@Param("institutionId") String institutionId);

    Integer getMaxId();
}