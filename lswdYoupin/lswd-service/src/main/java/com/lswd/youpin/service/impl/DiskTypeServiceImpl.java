package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.DiskRecipeMapper;
import com.lswd.youpin.dao.lsyp.DiskTypeMapper;
import com.lswd.youpin.dao.lsyp.LabelMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.DiskLabel;
import com.lswd.youpin.model.lsyp.DiskRecipe;
import com.lswd.youpin.model.lsyp.DiskType;
import com.lswd.youpin.model.lsyp.Label;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.DiskTypeService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by zhenguanqi on 2017/11/15
 */

@Service
public class DiskTypeServiceImpl implements DiskTypeService {
    private final Logger log = LoggerFactory.getLogger(DiskTypeServiceImpl.class);
    @Autowired
    private DiskTypeMapper diskTypeMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private DiskRecipeMapper diskRecipeMapper;

    @Override
    public LsResponse getDiskTypeList(String name, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        int total;
        try {
            name = StringsUtil.encodingChange(name);
            canteenId = StringsUtil.encodingChange(canteenId);
        } catch (UnsupportedEncodingException e) {
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
            log.error("查询餐盘类型列表失败，失败原因为：" + e.toString());
        }
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            total = diskTypeMapper.getDiskTypeListCount(name, canteenId);
            List<DiskType> diskTypes = diskTypeMapper.getDiskTypeList(name, canteenId, offSet, pageSize);
            if (diskTypes.size() > 0 && diskTypes != null) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(diskTypes);
            } else {
                lsResponse.setAsFailure();
                if (name != null && !("").equals(name)) {
                    lsResponse.setMessage("该餐盘类型不存在");
                } else {
                    lsResponse.setMessage("该餐厅没有餐盘类型信息");
                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查询餐盘类型列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addDiskType(DiskType diskType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<DiskType> diskTypes = diskTypeMapper.checkName(diskType.getName(), diskType.getCanteenId());
        if (diskTypes != null && diskTypes.size() > 0) {
            lsResponse.setAsFailure();
            lsResponse.setMessage("类型名称已经存在");
            return lsResponse;
        }
        diskType.setCreateUser(user.getUsername());
        diskType.setCreateTime(Dates.now());
        diskType.setCreateUser(user.getUsername());
        diskType.setUpdateTime(Dates.now());
        try {
            diskTypeMapper.insertSelective(diskType);
            lsResponse.setMessage("餐盘类型新增成功");
            DiskRecipe diskRecipe = new DiskRecipe();
            diskRecipe.setDiskTypeId(diskType.getId());
            diskRecipe.setCanteenId(diskType.getCanteenId());
            diskRecipe.setCreateTime(Dates.now());
            diskRecipe.setCreateUser(user.getUsername());
            diskRecipeMapper.insertSelective(diskRecipe);
            lsResponse.setMessage("餐具类型添加成功");
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("餐盘类型新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateDiskType(DiskType diskType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<DiskType> diskTypes = diskTypeMapper.checkName(diskType.getName(), diskType.getCanteenId());
        if (diskTypes != null && diskTypes.size() > 0) {
            lsResponse.setAsFailure();
            lsResponse.setMessage("类型名称已经存在");
            return lsResponse;
        }
        diskType.setUpdateUser(user.getUsername());
        diskType.setUpdateTime(Dates.now());
        try {
            int updateFlag = diskTypeMapper.updateByPrimaryKeySelective(diskType);
            if (updateFlag > 0) {
                lsResponse.setMessage("餐盘类型修改成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("餐盘类型修改失败");
            }
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("餐盘修改新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteDiskType(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null) {
                return lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            List<Label> diskLabels = labelMapper.getDiskLabelByDiskTypeId(id);
            List<DiskRecipe> diskRecipes = diskRecipeMapper.getDiskRecipeByDiskTypeId(id);
            if (diskRecipes != null && diskRecipes.size() > 0) {
                lsResponse.setAsFailure();
                lsResponse.setMessage("餐具和菜品关联不能删除");
                return lsResponse;
            }
            if (diskLabels != null && diskLabels.size() > 0) {
                lsResponse.setAsFailure();
                lsResponse.setMessage("该类型仍有标签未删除，故不能删除该类型");
                return lsResponse;
            }
            int deleteFlag = diskTypeMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0) {
                lsResponse.setMessage("餐具类型删除成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("餐具类型删除失败");
            }
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("餐具类型删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getDiskTypeListAll(String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            canteenId = StringsUtil.encodingChange(canteenId);
            List<DiskType> diskTypes = diskTypeMapper.getDiskTypeListAll(canteenId);
            if (diskTypes.size() > 0 && diskTypes != null) {
                lsResponse.setData(diskTypes);
            } else {
                lsResponse.checkSuccess(false, "该餐厅暂时没有餐盘类型信息");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("根据餐厅获取餐盘列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getLabelByTypeId(Integer typeId, String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offSet = 0;
        try {
            keyword = StringsUtil.encodingChange(keyword);
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            Integer total = diskTypeMapper.getLabelByTypeIdCount(typeId, keyword);
            List<DiskLabel> diskLabels = diskTypeMapper.getLabelByTypeId(typeId, keyword, offSet, pageSize);
            if (diskLabels.size() > 0 && diskLabels != null) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(diskLabels);
            } else {
                lsResponse.checkSuccess(false, "该类型没有标签");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("根据类型获取标签失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteLabelByTypeId(Integer typeId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            Integer flag = labelMapper.deleteByTypeId(typeId);
            if (flag > 0) {
                lsResponse.setMessage("删除该类型的所有标签成功");
            } else {
                lsResponse.checkSuccess(false, "删除该类型的所有标签失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除该类型的所有标签失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }
}
