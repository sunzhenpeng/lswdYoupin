package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.dao.lsyp.RecipeSecKillMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.RecipeSecKill;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeSecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/8/19.
 */
@Service
public class RecipeSecKillServiceImpl implements RecipeSecKillService{
    private final Logger log = LoggerFactory.getLogger(RecipeSecKillServiceImpl.class);

    @Autowired
    private RecipeSecKillMapper recipeSecKillMapper;

    @Override
    public LsResponse getRecipeSecKillListWeb(User user, String keyword, String canteenId, String dinnerTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (keyword != null && !"".equals(keyword)){
                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
            }else{
                keyword = "";
            }
            if (dinnerTime != null && !"".equals(dinnerTime)){
                dinnerTime = new String(dinnerTime.getBytes("iso8859-1"),"utf-8");
            }else{
                dinnerTime = Dates.now("yyyy-MM-dd");
            }
            String[] canteenIds = user.getCanteenIds().split(",");
            if (canteenId != null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeSecKillMapper.getRecipeSecKillCountWeb(keyword,canteenId,dinnerTime,canteenIds);
            List<RecipeSecKill> recipeSecKills = recipeSecKillMapper.getRecipeSecKillListWeb(keyword,canteenId,dinnerTime,canteenIds,offSet,pageSize);
            if (recipeSecKills != null && recipeSecKills.size()>0 ){//表示数据查询成功
                lsResponse.setData(recipeSecKills);
                lsResponse.setTotalCount(total);
            }else{
                lsResponse.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }

    @Override
    public LsResponse addOrUpdate(User user, RecipeSecKill recipeSecKill) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("用户为"+user.getUsername()+"的正在新建或者修改秒杀菜品的数据为"+recipeSecKill);
            if (recipeSecKill.getId() == null){//表示新增
                recipeSecKill.setIsDelete(false);
                recipeSecKill.setCreateUser(user.getUsername());
                recipeSecKill.setUpdateUser(user.getUsername());
                recipeSecKill.setCreateTime(Dates.now());
                recipeSecKill.setUpdateTime(Dates.now());
                int insflag = recipeSecKillMapper.insertSelective(recipeSecKill);
                if (insflag > 0){
                    lsResponse.setMessage("新增成功");
                    log.info("新增成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("新增失败");
                    log.error("新增失败");
                }
            }else {//表示修改
                recipeSecKill.setUpdateUser(user.getUsername());
                recipeSecKill.setUpdateTime(Dates.now());
                int updflag = recipeSecKillMapper.updateByPrimaryKeySelective(recipeSecKill);
                if (updflag > 0){
                    lsResponse.setMessage("修改成功");
                    log.info("新增成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("修改失败");
                    log.error("修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            lsResponse.setMessage("新增或者修改失败，失败原因为："+e.toString());
            log.error("新增或者修改失败，失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse delete(User user, Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null){
                log.info("用户为"+user.getUsername()+"的正在删除id为"+id+"的秒杀菜品");
            }
            int delflag = recipeSecKillMapper.deleteStatus(id);
            if (delflag > 0){
                lsResponse.setMessage("删除成功");
                log.info("删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("删除失败");
                log.error("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            lsResponse.setMessage("删除失败，失败原因为："+e.toString());
            log.error("删除失败，失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeSecKillListH5(User user, String keyword, String canteenId, String dinnerTime, Integer eatType,Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (keyword != null && !"".equals(keyword)){
                keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
            }else{
                keyword = "";
            }
            if (dinnerTime != null && !"".equals(dinnerTime)){
                dinnerTime = new String(dinnerTime.getBytes("iso8859-1"),"utf-8");
            }else{
                dinnerTime = Dates.now("yyyy-MM-dd");
            }
            String[] canteenIds = user.getCanteenIds().split(",");
            if (canteenId != null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            if (eatType == null){
                eatType = 0;
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeSecKillMapper.getRecipeSecKillCountH5(keyword,canteenId,dinnerTime,canteenIds,eatType);
            List<RecipeSecKill> recipeSecKills = recipeSecKillMapper.getRecipeSecKillListH5(keyword,canteenId,dinnerTime,canteenIds,eatType,offSet,pageSize);
            if (recipeSecKills != null && recipeSecKills.size()>0 ){//表示数据查询成功
                lsResponse.setData(recipeSecKills);
                lsResponse.setTotalCount(total);
            }else{
                lsResponse.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }
}
