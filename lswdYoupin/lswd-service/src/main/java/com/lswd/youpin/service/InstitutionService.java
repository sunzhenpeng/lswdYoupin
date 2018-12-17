package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Institution;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/6/7.
 */
public interface InstitutionService {
    LsResponse getInstitutionByTenantId(String tenantId);
    LsResponse addOrUpdateInstitution(Institution institution,User u);
    LsResponse deleteInstitution(String institutionId);
    LsResponse getAllInstitutions(User u,String keyword,Integer pageNum, Integer pageSize);
    LsResponse getInstitutionByInstitutionId(String institutionId);
}
