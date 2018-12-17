package com.lswd.youpin.Thin;

import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuruilong on 2017/8/17.
 */
public interface InstitutionThin {
    LsResponse deleteInstitution(String institutionId,HttpServletRequest request);
}
