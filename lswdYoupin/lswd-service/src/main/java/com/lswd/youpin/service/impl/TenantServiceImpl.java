package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.TenantCodeMapper;
import com.lswd.youpin.dao.TenantMapper;
import com.lswd.youpin.model.Tenant;
import com.lswd.youpin.model.TenantCode;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.TenantService;
import com.lswd.youpin.utils.RandomUtil;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by liruilong on 2017/6/7.
 */
@Service
public class TenantServiceImpl implements TenantService {
    private final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private TenantCodeMapper tenantCodeMapper;

    @Override
    public LsResponse getTenantByTenantId(String tenantId) {
        log.info("{} is being executed. Tenant = {}", "getTenantByTenantId", JSON.toJSON(tenantId));
        LsResponse lsResponse = new LsResponse();
        try {
            lsResponse.setData(tenantMapper.getTenantByTenantId(tenantId));
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.TENANT_SELECT_ERR.name());
            log.error("租户查询失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdateTenant(Tenant tenant, User u) {
        log.info("{} is being executed. Tenant = {}", "addTenant", JSON.toJSON(tenant));
        LsResponse lsResponse = new LsResponse();
        tenant.setUpdateTime(Dates.now());
        tenant.setCreateTime(Dates.now());
        tenant.setCreateUser(u.getUsername());
        boolean b = false;
        if (tenant.getId() == null) {
            try {
                Integer maxId = tenantMapper.getMaxId();
                if(maxId==null){
                    maxId=0;
                }
                String prefix = u.getUsername().substring(0, 4);
                String suffix = String.valueOf(Integer.parseInt("1001") + maxId);
                String tenantId = prefix + suffix;
                tenant.setTenantId(tenantId);
                tenantMapper.insertSelective(tenant);
                TenantCode tenantCode = new TenantCode();
                tenantCode.setTenantId(tenantId);
                tenantCode.setTenantCode(prefix);
                tenantCode.setUpdateTime(Dates.now());
                tenantCodeMapper.insertSelective(tenantCode);
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                lsResponse.checkSuccess(b, CodeMessage.TENANT_ADD_ERR.name());
                log.error("租户添加失败：{}", e.getMessage());
            }
        } else {
            try {
                b = tenantMapper.updateByPrimaryKeySelective(tenant) > 0;
                lsResponse.setAsSuccess();
            } catch (Exception e) {
                lsResponse.checkSuccess(b, CodeMessage.TENANT_UPDATE_ERR.name());
                log.error("租户更新失败:{}", e.getMessage());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteTenant(Integer id) {
        log.info("{} is being executed. Tenant = {}", "deleteTenant", JSON.toJSON(id));
        LsResponse lsResponse = new LsResponse();
        try {
            tenantMapper.deleteById(id);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.TENANT_DELETE_ERR.getMsg());
            log.error("租户删除失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAllTenants(String tenantId, String keyword, Integer pageNum, Integer pageSize) throws UnsupportedEncodingException {
        log.info(" being executed. getAllTenant = {}", JSON.toJSON(tenantId));
        LsResponse lsResponse = new LsResponse();
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            keyword= StringsUtil.encodingChange(keyword);
            List<Tenant> tenantList = tenantMapper.getAllTenants(tenantId, keyword, offset, pageSize);
            log.info("tenantList======================" + JSON.toJSON(tenantList));
            lsResponse.setData(tenantList);
            lsResponse.setTotalCount(1);
            return lsResponse;
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.TENANT_SELECT_ERR.getMsg());
            log.error(" 获取所有商家失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public List<Tenant> getTenantAll() {
        return tenantMapper.getTenantALL();
    }
}
