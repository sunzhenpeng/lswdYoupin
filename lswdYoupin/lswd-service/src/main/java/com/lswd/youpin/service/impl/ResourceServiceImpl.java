package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.lsyp.ResourceMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Resources;
import com.lswd.youpin.model.vo.Nodes;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.ResourceService;
import com.lswd.youpin.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liruilong on 2017/12/11.
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public LsResponse getResourceByParentId(Integer parentId,User u) {
        log.info("根据parentId查询子菜单=====parentId======" + parentId);
        LsResponse lsResponse = new LsResponse();
        try {
            List<Nodes> nodes = resourceMapper.getResource(u.getRoleId());
            List<Nodes> treeNodes = TreeUtil.getDataToTree(nodes, parentId);
            lsResponse.setData(treeNodes);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            log.error("菜单查询失败===={}", e.getMessage());
            lsResponse.setMessage("查询失败");
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getResourceListAll() {
        log.info("根据");
        LsResponse lsResponse = new LsResponse();
        try {
            List<Resources> nodes = resourceMapper.getResourceListAll();
           // List<Nodes> treeNodes = TreeUtil.getDataToTree(nodes);
            lsResponse.setData(nodes);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            log.error("菜单查询失败===={}", e.getMessage());
            lsResponse.setMessage("查询失败");
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }
}
