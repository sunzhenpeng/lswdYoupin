package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.InstitutionMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Institution;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liruilong on 2017/6/7.
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {
    private final Logger log = LoggerFactory.getLogger(InstitutionServiceImpl.class);

    @Autowired
    private InstitutionMapper institutionMapper;

    /**
     * 根据商家编号获取商家下所有的区域公司
     *
     * @param tenantId
     * @return
     */
    @Override
    public LsResponse getInstitutionByTenantId(String tenantId) {
        log.info("{} is being executed. Institution = {}", "getInstitutionByTenantId", JSON.toJSON(tenantId));
        LsResponse lsResponse = new LsResponse();
        try {
            List<Institution> institutions = institutionMapper.getInstitutionByTenantId(tenantId);
            lsResponse.setData(institutions);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.INSTITUTION_SELECT_ERR.getMsg());
            log.error("查询商家下所有区域失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 新建或者更新区域，如果区域id 为空则为添加操作否则为更新操作
     *
     * @param institution
     * @return
     */
    @Override
    public LsResponse addOrUpdateInstitution(Institution institution, User u) {
        log.info("{} is being executed. Institution = {}", "addOrUpdateInstitution", JSON.toJSON(institution));
        LsResponse lsResponse = new LsResponse();
        institution.setUpdateTime(Dates.now());
        institution.setCreateTime(Dates.now());

        boolean b = false;
        if (institution.getId() == null) {
            try {
                Integer maxId = institutionMapper.getMaxId();
                if(maxId==null){
                    maxId=0;
                }
                String suffix = String.valueOf(Integer.parseInt("200001") + maxId);
                String institutionId = u.getTenantId().substring(0, 4) + suffix;
                institution.setInstitutionId(institutionId);
                institution.setTenantId(u.getTenantId());
                b = institutionMapper.insertSelective(institution) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.INSTITUTION_ADD_ERR.getMsg());
                log.error("区域添加失败：{}", e.getMessage());
            }
        } else {
            try {
                b = institutionMapper.updateByPrimaryKeySelective(institution) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.INSTITUTION_UPDATE_ERR.getMsg());
                log.error("区域更新失败:{}", e.getMessage());
            }
        }
        return lsResponse;
    }

    /**
     * 根据区域编号删除区域，如果区域下有餐厅则不能删除
     *
     * @param institutionId
     * @return
     */
    @Override
    public LsResponse deleteInstitution(String institutionId) {
        log.info("{} is being executed. Institution = {}", "deleteInstitutionByInstitutionId", JSON.toJSON(institutionId));
        LsResponse lsResponse = new LsResponse();
        try {
            institutionMapper.deleteInstitution(institutionId);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.INSTITUTION_DELETE_ERR.getMsg());
            log.error("区域删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 获取所有的区域列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public LsResponse getAllInstitutions(User u,String keyword, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. Institution = {}", "getAllInstitutions");
        LsResponse lsResponse = new LsResponse();
        String name = "";
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        List<Institution> institutions = new ArrayList<>();
        try {
            if (keyword != null && !(keyword.equals(""))) {
                String tmp = URLDecoder.decode(keyword, "UTF-8");
                name = new String(tmp.getBytes("ISO-8859-1"), "UTF-8");
            }
            log.info("keyword========================" + name);
            Integer count = institutionMapper.getTotalCount(u.getTenantId(),name);
            institutions = institutionMapper.getInstitutionList(u.getTenantId(),name, offset, pageSize);
            log.info("institutions========================" + JSON.toJSON(institutions));
            lsResponse.setData(institutions);
            lsResponse.setTotalCount(count);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.INSTITUTION_SELECT_ERR.getMsg());
            log.error("区域查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getInstitutionByInstitutionId(String institutionId) {
        log.info("{} is being executed. Institution = {}", "getInstitutionByInstitutionId", JSON.toJSON(institutionId));
        LsResponse lsResponse = new LsResponse();
        try {
            Institution institution = institutionMapper.getInstitutionByInstitutionId(institutionId);
            lsResponse.setData(institution);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.INSTITUTION_SELECT_ERR.getMsg());
            lsResponse.setErrorCode(CodeMessage.INSTITUTION_SELECT_ERR.getCode());
            log.error("区域查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }
}
