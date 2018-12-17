package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.GoodCategoryMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodCategory;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodCategoryService;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.StringsUtil;
import com.lswd.youpin.weixin.core.Datas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/22.
 */
@Service
public class GoodCategoryServiceImpl implements GoodCategoryService {
    private final Logger log = LoggerFactory.getLogger(GoodCategoryServiceImpl.class);

    @Autowired
    private GoodCategoryMapper goodCategoryMapper;


    @Override
    public LsResponse addOrUpdateGoodCategory(GoodCategory goodCategory, User user) {
        LsResponse lsResponse = new LsResponse();
        if (goodCategory.getId() == null) { //表示新增
            log.info(user.getUsername() + "addOrUpdateGoodCategory() method is being executed", "addOrUpdateGoodCategory", JSON.toJSON(goodCategory));
            try {
                goodCategory.setCreateTime(Dates.now());
                goodCategory.setCreateUser(user.getUsername());
                goodCategory.setUpdateTime(Dates.now());
                goodCategory.setUpdateUser(user.getUsername());
                int flag = goodCategoryMapper.insertSelective(goodCategory);
                if (flag > 0) {
                    lsResponse.setMessage("商品类别新增成功");
                    log.info("商品类别新增成功");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品类别新增失败");
                    log.info("商品类别新增失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setErrorCode("500");
                lsResponse.checkSuccess(false, "商品类别新增失败");
                log.error("商品类别添加失败：{}", e.getMessage());
            }
        } else {//表示修改
            try {
                goodCategory.setUpdateTime(Dates.now());
                goodCategory.setUpdateUser(user.getUsername());
                int flag = goodCategoryMapper.updateByPrimaryKeySelective(goodCategory);
                if (flag > 0) {
                    lsResponse.setMessage("商品类别修改成功");
                    log.info("商品类别修改成功");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品类别修改失败");
                    log.info("商品类别修改失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResponse.setErrorCode("500");
                lsResponse.checkSuccess(false, "商品修改失败");
                log.error("商品类别更新失败:{}", e.getMessage());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteGoodCategoryById(Integer id, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (id != null) {
                log.info(user.getUsername() + "deleteGoodCategoryById() method is being executed.", "deleteGoodCategoryById", JSON.toJSON(id));
                int flag = goodCategoryMapper.deleteByPrimaryKey(id);
                if (flag > 0) {
                    lsResponse.setMessage("删除成功");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("删除失败");
                    log.info("商品分类删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.TENANT_SELECT_ERR.name());
            log.error("商品类别删除失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getFirstGoodCategory(String name, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            name = StringsUtil.encodingChange(name);
            int total = goodCategoryMapper.getFirstGoodCategoryCount(name);
            List<GoodCategory> goodCategoryList = goodCategoryMapper.getFirstGoodCategory(name, offset, pageSize);
            lsResponse.setData(goodCategoryList);
            lsResponse.setTotalCount(total);
            return lsResponse;
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.TENANT_SELECT_ERR.getMsg());
            log.error(" 获取商品一级分类失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getSecondGoodCategory(String name, Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            name = StringsUtil.encodingChange(name);
            List<GoodCategory> goodCategoryList = goodCategoryMapper.getSecondGoodCategory(name, id);
            lsResponse.setData(goodCategoryList);
            return lsResponse;
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.TENANT_SELECT_ERR.getMsg());
            log.error(" 获取商品二级分类失败:{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodCategoryListAll(String canteenId, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            List<GoodCategory> goodCategoryList = goodCategoryMapper.getGoodCategoryListAll(canteenId);
            if (goodCategoryList != null && goodCategoryList.size() > 0) {
                lsResponse.setData(goodCategoryList);
                lsResponse.setMessage(user.getUsername() + "商品分类查询成功");
                log.info(user.getUsername() + "getGoodCategoryListAll is being executed,查询成功", "getGoodCategoryListAll", JSON.toJSON(user.getUsername()));
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("查询失败，数据为0");
                log.info(user.getUsername() + "商品分类查询失败，数据为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(" getGoodCategoryListAll(),获取商品一级分类失败", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodCategoryWeb(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = 0;
        try {
            keyword = StringsUtil.encodingChange(keyword);
            canteenId = StringsUtil.encodingChange(canteenId);
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            total = goodCategoryMapper.getGoodCategoryWebCount(keyword, canteenId);
            List<GoodCategory> goodCategories = goodCategoryMapper.getGoodCategoryWeb(keyword, canteenId, offSet, pageSize);
            if (goodCategories != null && goodCategories.size() > 0) {
                lsResponse.setData(goodCategories);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("商品列表查询成功");
            } else {
                lsResponse.setAsFailure();
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该分类不存在");
                } else {
                    lsResponse.setMessage("商品列表查询失败");
                }
                log.info("商品列表查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，获取商品列表出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse checkOutName(String name, String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            name = StringsUtil.encodingChange(name);
            canteenId = StringsUtil.encodingChange(canteenId);
            GoodCategory goodCategory = goodCategoryMapper.checkOutName(name, canteenId);
            if (goodCategory != null) {//如果有同名的，设置date为false
                lsResponse.setData(false);
            } else {//如果没有同名的，设置date为true
                lsResponse.setData(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("校验分类名是否重复", e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------H5页面需要全部分类-------------------------------------------------------------------*/
    @Override
    public LsResponse getGoodCategoryListH5(String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            canteenId = StringsUtil.encodingChange(canteenId);
            String dataSource = DataSourceHandle.getDataSourceType();
            log.info("查询商品分类进的是-----------------------------------------" + dataSource + "库");
            List<GoodCategory> goodCategoryList = goodCategoryMapper.getGoodCategoryListH5(canteenId);
            if (goodCategoryList != null && goodCategoryList.size() > 0) {
                log.info("商品分类的长度为" + goodCategoryList.size());
//                goodCategoryList.get(0).setCheck(true);
                lsResponse.setData(goodCategoryList);
                lsResponse.setMessage("查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}
