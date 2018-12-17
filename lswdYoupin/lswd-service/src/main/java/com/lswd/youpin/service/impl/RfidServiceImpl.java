package com.lswd.youpin.service.impl;

import RFID.RfidUtil;
import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.DateUtils;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.DiskRecipeMapper;
import com.lswd.youpin.dao.lsyp.LabelMapper;
import com.lswd.youpin.model.lsyp.DiskRecipe;
import com.lswd.youpin.model.lsyp.Label;
import com.lswd.youpin.model.vo.LabelVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RfidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created by liruilong on 2017/11/24.
 */
@Service
public class RfidServiceImpl implements RfidService {
    private final Logger log = LoggerFactory.getLogger(RfidServiceImpl.class);
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private DiskRecipeMapper diskRecipeMapper;

    @Override
    public LsResponse getLabel() {
        log.info("开始录入芯片信息");
        LsResponse lsResponse = new LsResponse();
        try {
            ArrayList<String> list = RfidUtil.getLabelInfo();
            lsResponse.setData(list);
            lsResponse.setTotalCount(list.size());
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.setAsFailure();
            log.info(e.getMessage());
            log.error("芯片录入失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse add(LabelVO labelVO) {
        log.info("添加餐具信息====labelVO============={}", JSON.toJSON(labelVO));
        String canteenId = labelVO.getCanteenId();
        String recipeId = labelVO.getRecipeId();
        int diskTypeId = labelVO.getDiskTypeId();
        // Date startTime = labelVO.getStartTime();
        //Date endTime = labelVO.getEndTime();
        List<String> labels = labelVO.getLabels();
        int count = 0;
        LsResponse lsResponse = new LsResponse();
        try {
            for (String str : labels) {
                Label label = new Label();
                label.setCanteenId(canteenId);
                label.setDiskTypeId(diskTypeId);
                label.setLabelUid(str);
                label.setStartTime(new Date());
                label.setEndTime(new Date());
                label.setRecipeId(recipeId);
                label.setExwTime(new Date());
                labelMapper.insertSelective(label);
                count++;
            }
            lsResponse.setAsSuccess();
            lsResponse.setTotalCount(count);
            lsResponse.setMessage("餐盘录入成功");
            log.info("餐具信息添加成功");
        } catch (Exception e) {
            lsResponse.setData(count);
            lsResponse.checkSuccess(false, CodeMessage.LABEL_ADD_ERR.getMsg());
            log.error("餐具信息添加失败======{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getLabelInfoByUid() {
        LsResponse lsResponse = new LsResponse();
        ArrayList<String> list = RfidUtil.getLabelInfo();
        //String uId = list.get(0);
        try {
            List<Label> label = labelMapper.getLabelInfo(list);
            lsResponse.setData(label);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.LABEL_SELECT_ERR.getMsg());
            log.error("获取餐具对应的信息失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse update(DiskRecipe diskRecipe) {
        log.info("批量更新餐具====diskRecipe=={}", JSON.toJSON(diskRecipe));
        LsResponse lsResponse = new LsResponse();
        try {
            if(!(diskRecipe.getBreakfast().equals("")&&diskRecipe.getLunch().equals("")&&diskRecipe.getDinner().equals(""))){
                int count = diskRecipeMapper.updateByPrimaryKeySelective(diskRecipe);
                String recipeId ="";
                if (!diskRecipe.getBreakfast().equals("")) {//&&(nowHour>6)&&(nowHour<=10)
                    recipeId = diskRecipe.getBreakfast();
                } else if (!diskRecipe.getLunch().equals("")) {//&&(nowHour>10)&&(nowHour<=12)
                    recipeId = diskRecipe.getLunch();
                } else {
                    recipeId = diskRecipe.getDinner();
                }
                if (count > 0) {
                    labelMapper.updateLabel(diskRecipe.getCanteenId(), diskRecipe.getDiskTypeId(), recipeId);
                }
                lsResponse.setTotalCount(count);
            }
            lsResponse.setAsSuccess();
            lsResponse.setMessage("更新成功");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false, CodeMessage.DISK_RECIPE_UPDATE_ERR.getMsg());
            log.error("餐具更新失败={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getDiskRecipe(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        log.info("获取餐具和菜品的对应关系列表======canteenId==={},============餐具名称keyword==={}", canteenId, keyword);
        LsResponse lsResponse = new LsResponse();
        String diskName = "";
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            if (keyword != null && !(keyword.equals(""))) {
                diskName = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
            }
            List<DiskRecipe> labels = diskRecipeMapper.getDiskRecipe(diskName, canteenId, offset, pageSize);
            int totalCount = diskRecipeMapper.getDiskRecipeCount(diskName, canteenId);
            lsResponse.setTotalCount(totalCount);
            lsResponse.setData(labels);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.DISK_RECIPE_SELECT_ERR.getMsg());
            log.error("餐具和菜品的对应关系列表查询失败=={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteDiskRecipe(String canteenId, Integer diskTypeId) {
        log.info("删除餐具和菜品的对应关系列表======canteenId==={}，=======diskTypeId====={}", canteenId, diskTypeId);
        LsResponse lsResponse = new LsResponse();
        try {
            int count = diskRecipeMapper.deleteDiskRecipe(canteenId, diskTypeId);
            lsResponse.setAsSuccess();
            lsResponse.setMessage("删除成功");
            lsResponse.setTotalCount(count);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.DISK_RECIPE_DELETE_ERR.getMsg());
            log.error("餐具和菜品的对应关系列表删除失败", e.getMessage());
        }
        return lsResponse;
    }

    /**
     * 每天 7点，10点，14点修改餐具和菜品的对应关系
     *
     * @return
     */
    @Override
    public void updateDiskRecipe(int mealType, String canteenId) {
        log.info("开始定时修改餐具和菜品的对应关系=====mealType==={},======canteenId===={}", mealType, canteenId);
        try {
            List<DiskRecipe> diskRecipes = diskRecipeMapper.getAll(canteenId);
            if (mealType == 0) {
                for (DiskRecipe d : diskRecipes) {
                    labelMapper.updateDiskRecipe(d.getBreakfast(), d.getDiskTypeId(), canteenId);
                }
            } else if (mealType == 1) {
                for (DiskRecipe d : diskRecipes) {
                    labelMapper.updateDiskRecipe(d.getLunch(), d.getDiskTypeId(), canteenId);
                }
            } else {
                for (DiskRecipe d : diskRecipes) {
                    labelMapper.updateDiskRecipe(d.getDinner(), d.getDiskTypeId(), canteenId);
                }
            }
            log.info("定时修改餐具和菜品的对应关系成功");
        } catch (Exception e) {
            log.info("定时修改餐具和菜品的对应关系失败==={}", e.getMessage());
        }
    }

    @Override
    public LsResponse updateSingleDisk(String uId, String recipeId) {
        log.info("开始修改单个餐具和菜品的对应关系=====uId==={},======recipeId===={}", uId, recipeId);
        LsResponse lsResponse = new LsResponse();
        try {
            int count = labelMapper.updateSingleDisk(uId, recipeId);
            lsResponse.setData(count);
            lsResponse.setAsSuccess();
            lsResponse.setMessage("修改成功");
        } catch (Exception e) {
            log.error("修改餐具和菜品的对应关系失败==={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SINGLE_LABEL_UPDATE_ERR.getMsg());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getLabelStatistics(String canteenId, Integer diskTypeId) {
        log.info("统计每种餐具的盘子数量=====canteenId==={},======diskTypeId===={}", canteenId, diskTypeId);
        LsResponse lsResponse = new LsResponse();
        try {
            List<Label> labelList = labelMapper.getLabels(canteenId, diskTypeId);
            if (labelList.size() <= 0 || labelList == null) {
                lsResponse.setAsFailure();
                lsResponse.setTotalCount(0);
                return lsResponse;
            }
            lsResponse.setTotalCount(labelList.size());
            lsResponse.setData(labelList);
        } catch (Exception e) {
            log.error("统计餐盘数量失败===={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("查询失败");
        }
        return lsResponse;
    }
}
