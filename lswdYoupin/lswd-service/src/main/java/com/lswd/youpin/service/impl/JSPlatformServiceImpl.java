package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.JSPlatformMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Counter;
import com.lswd.youpin.model.lsyp.JSPlatform;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.JSPlatformService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by zhenguanqi on 2017/12/23.
 */
@Service
public class JSPlatformServiceImpl implements JSPlatformService {

    private final Logger logger = LoggerFactory.getLogger(JSPlatformServiceImpl.class);

    @Autowired
    private JSPlatformMapper jsPlatformMapper;

    @Override
    public LsResponse getJSPlatformListAll(User user, String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            List<JSPlatform> jsPlatforms = jsPlatformMapper.getJSPlatformListAll(canteenId);
            if (jsPlatforms.size() > 0 && jsPlatforms != null) {
                lsResponse.setData(jsPlatforms);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("查看所有的结算台列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getJSPlatformList(User user, String canteenId, String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        int offSet = 0;
        try {
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            canteenId=StringsUtil.encodingChange(canteenId);
            keyword=StringsUtil.encodingChange(keyword);
        } catch (UnsupportedEncodingException e) {
            logger.error("查看结算台列表失败，失败原因为：" + e.toString());
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            Integer total = jsPlatformMapper.getJSPlatformListCount(keyword, canteenId);
            List<JSPlatform> jsPlatforms = jsPlatformMapper.getJSPlatformList(keyword, canteenId,offSet, pageSize);
            if (jsPlatforms.size() > 0 && jsPlatforms != null) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(jsPlatforms);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("查看结算台列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addJSPlatform(JSPlatform jsPlatform, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        jsPlatform.setCreateUser(user.getUsername());
        jsPlatform.setCreateTime(Dates.now());
        jsPlatform.setUpdateUser(user.getUsername());
        jsPlatform.setUpdateTime(Dates.now());
        jsPlatform.setIsDelete((byte) 0);
        try {
            Integer maxId = jsPlatformMapper.getMaxId();
            if (maxId == null) maxId = 0;
            int jstId = maxId + 1001;
            jsPlatform.setJsPlatformId("JST" + jstId);
            int insertFlag = jsPlatformMapper.insertSelective(jsPlatform);
            if (insertFlag > 0) {
                lsResponse.setMessage("结算台新增成功");
            } else {
                lsResponse.checkSuccess(false, "结算台新增失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateJSPlatform(JSPlatform jsPlatform, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        jsPlatform.setUpdateUser(user.getUsername());
        jsPlatform.setUpdateTime(Dates.now());
        try {
            int updateFlag = jsPlatformMapper.updateByPrimaryKeySelective(jsPlatform);
            if (updateFlag > 0) {
                lsResponse.setMessage("结算台修改成功");
            } else {
                lsResponse.checkSuccess(false, "结算台修改失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台修改失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteJSPlatform(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (id == null){
            return  lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
        }
        try {
            int deleteFlag = jsPlatformMapper.deleteByPrimaryKeyUpdate(id);
            if (deleteFlag > 0) {
                lsResponse.setMessage("结算台删除成功");
            } else {
                lsResponse.checkSuccess(false, "结算台删除失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("结算台删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }
}
