package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.DateUtils;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MealMenuMapper;
import com.lswd.youpin.dao.lsyp.MealRecordMapper;
import com.lswd.youpin.dao.lsyp.NutritionMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MealMenu;
import com.lswd.youpin.model.lsyp.MealRecord;
import com.lswd.youpin.model.lsyp.Nutrition;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.model.vo.MealRecordVO;
import com.lswd.youpin.model.vo.MealStatisticVO;
import com.lswd.youpin.model.vo.NutritionVo;
import com.lswd.youpin.poiutils.ExportUtil;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MealRecordService;
import com.lswd.youpin.utils.StringsUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by zhenguanqi on 2017/12/11.
 */
@Service
public class MealRecordServiceImpl implements MealRecordService {
    private final Logger logger = LoggerFactory.getLogger(MealRecordServiceImpl.class);

    @Autowired
    private MealRecordMapper mealRecordMapper;
    @Autowired
    private NutritionMapper nutritionMapper;
    @Autowired
    private MealMenuMapper mealMenuMapper;

    private static final String STARTTIME = "2000-05-31 00:00:00";
    private static final String ENDTIME = "2030-05-31 00:00:00";
    private static final String STARTTIMETIME = "11:30";
    private static final String ENDTIMETIME = "13:00";


    @Override
    public LsResponse getMemberMealRecordList(User user, String keyword, String date, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            keyword = StringsUtil.encodingChange(keyword);
            canteenId = StringsUtil.encodingChange(canteenId);
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (date != null && !date.equals("")) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        try {
            Integer total = mealRecordMapper.getMemberMealRecordListCount(keyword, dateStr[0], dateStr[1], canteenId);
            List<MealRecord> mealRecords = mealRecordMapper.getMemberMealRecordList(keyword, dateStr[0], dateStr[1], canteenId, offSet, pageSize);
            if (mealRecords != null && mealRecords.size() > 0) {
//                for (MealRecord mealRecord : mealRecords) {
//                    List<MealMenu> mealMenus = mealMenuMapper.getMenusByRecordId(mealRecord.getMealRecordId());
//                    List<MealMenu> mealMenus = mealRecord.getMealMenus();
//                    if (mealMenus != null && mealMenus.size() > 0) {
//                        NutritionVo nutritionVo = getRecordNutrition(mealMenus);
//                        mealRecord.setNutritionVo(nutritionVo);
//                    } else {
//                        mealRecord.setNutritionVo(new NutritionVo());
//                    }
//                }
                lsResponse.setData(mealRecords);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeMealRecordList(User user, String keyword, Integer categoryId, String date, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            if (keyword != null && !keyword.equals("")) {
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
            if (canteenId != null && !canteenId.equals("")) {
                canteenId = new String(canteenId.getBytes("utf-8"), "utf-8");
            } else {
                canteenId = "";
            }
            if (date != null && !date.equals("")) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        try {
            Integer total = mealRecordMapper.getRecipeMealRecordListCount(keyword, dateStr[0], dateStr[1], categoryId, canteenId, canteenIds);
            List<Recipe> recipes = mealRecordMapper.getRecipeMealRecordList(keyword, dateStr[0], dateStr[1], categoryId, canteenId, canteenIds, offSet, pageSize);
            if (recipes != null && recipes.size() > 0) {
                lsResponse.setData(recipes);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    /**
     * 获取每个菜品的营养成分
     *
     * @param recipeId
     * @return
     */
    private NutritionVo getRecipeNutrition(String recipeId) {
        NutritionVo nutritionVo = new NutritionVo();
        Float calorie = 0f;//卡路里
        Float protein = 0f;//蛋白质
        Float fat = 0f;//脂肪
        Float carbonhydrate = 0f;//碳水化合物
        Float vc = 0f;//维生素C
        List<Nutrition> nutritions = nutritionMapper.getNutritionListByRecipeId(recipeId);
        if (nutritions != null && nutritions.size() > 0) {
            for (Nutrition nutrition : nutritions) {
                calorie += nutrition.getCalorie() * nutrition.getAmount() * 0.01f;
                protein += nutrition.getProtein() * nutrition.getAmount() * 0.01f;
                fat += nutrition.getFat() * nutrition.getAmount() * 0.01f;
                carbonhydrate += nutrition.getCarbonhydrate() * nutrition.getAmount() * 0.01f;
                vc += nutrition.getVc() * nutrition.getAmount() * 0.01f;
            }
        }
        nutritionVo.setCalorie(calorie);
        nutritionVo.setProtein(protein);
        nutritionVo.setFat(fat);
        nutritionVo.setCarbonhydrate(carbonhydrate);
        nutritionVo.setVc(vc);
        return nutritionVo;
    }

    /**
     * 获取一个订单中所有菜的营养成分
     */
    private NutritionVo getRecordNutrition(List<MealMenu> mealMenus) {
        NutritionVo nutritionVo = new NutritionVo();
        List<NutritionVo> nutritionVos = new ArrayList<>();
        for (MealMenu mealMenu : mealMenus) {
            NutritionVo vo = getRecipeNutritionByView(mealMenu.getRecipeIdBH());
            nutritionVos.add(vo);
        }
        Float calorieAll = 0f;//卡路里
        Float proteinAll = 0f;//蛋白质
        Float fatAll = 0f;//脂肪
        Float carbonhydrateAll = 0f;//碳水化合物
        Float vcAll = 0f;//维生素C
        if (nutritionVos != null && nutritionVos.size() > 0) {
            for (NutritionVo vo : nutritionVos) {
                calorieAll += vo.getCalorie();
                proteinAll += vo.getProtein();
                fatAll += vo.getFat();
                carbonhydrateAll += vo.getCarbonhydrate();
                vcAll += vo.getVc();
            }
        }
        nutritionVo.setCalorie(calorieAll);
        nutritionVo.setProtein(proteinAll);
        nutritionVo.setFat(fatAll);
        nutritionVo.setCarbonhydrate(carbonhydrateAll);
        nutritionVo.setVc(vcAll);
        return nutritionVo;
    }

    /**
     * 获取每个菜的营养成分，通过数据库的视图进行查询
     */
    private NutritionVo getRecipeNutritionByView(String recipeIdBH) {
        NutritionVo nutritionVo = nutritionMapper.getRecipeNutritionByView(recipeIdBH);
        if (nutritionVo == null) {
            nutritionVo = new NutritionVo();
        }
        return nutritionVo;
    }

    @Override
    public LsResponse getRecipeSaleSpeed(User user, String date, String startTime, String endTime, String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] dateStr = new String[2];
        if (date != null && !date.equals("")) {
            dateStr = date.split(" - ");
            dateStr[0] = dateStr[0].replace("/", "-");
            dateStr[1] = dateStr[1].replace("/", "-");
        } else {
            dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
            dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
        }
        if (startTime == null || startTime.equals("")) {
            startTime = STARTTIMETIME;
        }
        if (endTime == null || endTime.equals("")) {
            endTime = ENDTIMETIME;
        }
        try {
            List<Recipe> recipes = mealRecordMapper.getRecipeSaleSpeed(dateStr[0], dateStr[1], canteenId, startTime, endTime);
            if (recipes != null && recipes.size() > 0) {
                Collections.sort(recipes, new Comparator<Recipe>() {
                    @Override
                    public int compare(Recipe r1, Recipe r2) {
                        int i = r2.getSaleTotal() - r1.getSaleTotal();//首先按照销量排序
//                        return r2.getSaleTotal().compareTo(r1.getSaleTotal());
                        return i;
                    }
                });
                lsResponse.setData(recipes);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMealRecords(User u, String canteenId, Integer leaveId, String memberName, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        logger.info("查询会员的消费记录====memberName====" + memberName);
        LsResponse lsResponse = new LsResponse();
        Integer offSet = 0;
        try {
            if (memberName != null && !memberName.equals("")) {
                memberName = new String(memberName.getBytes("iso8859-1"), "utf-8");
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            startTime = startTime.equals("") ? STARTTIME : startTime;
            endTime = endTime.equals("") ? ENDTIME : endTime;
            Date startDate = Dates.toDate(startTime);
            Date endDate = Dates.toDate(endTime);
            List<MealRecordVO> mealRecordVOs = mealRecordMapper.getMealRecords(canteenId, leaveId, memberName, startDate, endDate, offSet, pageSize);
            boolean flag = mealRecordVOs.size() > 0 ? true : false;
            //mealRecords.size()总的记录条数
            List<String> list = flag ? mealRecordMapper.getTotalCount(canteenId, leaveId, memberName, startDate, endDate) : new ArrayList<>();
            //totalNum 用餐总人数
            Integer totalNum = flag ? mealRecordMapper.getTotalMembers(canteenId, leaveId, startDate, endDate) : 0;
            if (!flag) {
                lsResponse.setMessage("暂时没有销售记录");
            }
            Map<String, Object> map = new HashedMap();
            map.put("records", mealRecordVOs);
            map.put("customers", totalNum);
            lsResponse.setData(map);
            lsResponse.setTotalCount(list.size());
            lsResponse.setSuccess(flag);
        } catch (Exception e) {
            logger.error("餐厅流水统计失败=={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("查询失败");
        }
        return lsResponse;
    }

    @Override
    public LsResponse getSales(String canteenId, Integer deviceId,String startTime, String endTime, Integer pageNum, Integer pageSize) {
        logger.info("餐厅早中晚营业额统计==========canteenId==========" + canteenId);
        LsResponse lsResponse = new LsResponse();
        Integer offSet = 0;
        boolean flag = true;
        try {
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            startTime = startTime.equals("") ? STARTTIME : DateUtils.getBeforeDay(startTime);
            endTime = endTime.equals("") ? ENDTIME : endTime;
            Date startDate = Dates.toDate(startTime);
            Date endDate = Dates.toDate(endTime);
            List<MealStatisticVO> mealStatisticVOS = new ArrayList<>();
            Float amount = 0F;
            Integer count = 0;
            if (deviceId == 0) {
                mealStatisticVOS = mealRecordMapper.getSalesAll(canteenId, deviceId,startDate, endDate, offSet, pageSize);
                // amount 选定时间段内总的销售额
                amount = mealRecordMapper.getSalesAllAmount(canteenId, deviceId, DateUtils.getNextDay(startDate), DateUtils.getNextDay(endDate));
                count = mealRecordMapper.getSalesAllCount(canteenId, deviceId, startDate, endDate);
            } else {
                mealStatisticVOS = mealRecordMapper.getSalesBy(canteenId, deviceId, startDate, endDate, offSet, pageSize);
                // amount 选定时间段内总的销售额
                amount = mealRecordMapper.getSalesAmountBy(canteenId, deviceId, DateUtils.getNextDay(startDate), DateUtils.getNextDay(endDate));
                count = mealRecordMapper.getSalesCountBy(canteenId, deviceId, startDate, endDate);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("mealStatistic", mealStatisticVOS);
            map.put("amount", amount);
            if (mealStatisticVOS.size() == 0 || count == 0) {
                flag = false;
                lsResponse.setMessage("暂时没有销售记录");
            }
            lsResponse.setData(map);
            lsResponse.setTotalCount(count);
        } catch (Exception e) {
            flag = false;
            logger.error("餐厅早中晚营业额统计失败==={}", e.getMessage());
            lsResponse.setMessage("查询失败");
        }
        lsResponse.setSuccess(flag);
        return lsResponse;
    }

    @Override
    public LsResponse exportJSTSales(String canteenId, Integer deviceId, String startTime, String endTime, HttpServletResponse response) {
        logger.info("餐厅结算台营业额统计，导出功能==========canteenId==========" + canteenId);
        LsResponse lsResponse = new LsResponse();
        ExportUtil pee = new ExportUtil(response, "餐厅结算台流水", "sheet1");
        try {
            startTime = startTime.equals("") ? STARTTIME : DateUtils.getBeforeDay(startTime);
            endTime = endTime.equals("") ? ENDTIME : endTime;
            Date startDate = Dates.toDate(startTime);
            Date endDate = Dates.toDate(endTime);
            List<MealRecord> mealRecords = mealRecordMapper.exportJSTSales(canteenId, deviceId, startDate, endDate);
            if (mealRecords != null && mealRecords.size() > 0) {
                String titleName[] = {"流水号", "餐厅编号", "会员编号", "会员名称", "刷卡金额", "余额", "刷卡时间", "刷卡设备"};
                String titleColumn[] = {"mealRecordId", "canteenId", "memberId", "memberName", "paymentAmount", "balance", "paymentTimeStr", "jstName"};
                int titleSize[] = {10, 18, 18, 18, 14, 14, 25, 22};
                pee.wirteExcel(titleColumn, titleName, titleSize, mealRecords);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            logger.error("餐厅结算台营业额统计，导出功能，导出失败==={}", e.getMessage());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getPersonMealRecords(String canteenId, Integer memberId, String eatTime) {
        logger.info("查询会员当天的就餐记录");
        LsResponse lsResponse = new LsResponse();
        String startTime = eatTime + " 00:00:00";
        String endTime = eatTime + " 23:59:59";
        Date startDate = Dates.toDate(startTime);
        Date endDate = Dates.toDate(endTime);
        try {
            List<MealRecord> mealRecords = mealRecordMapper.getPersonMealRecords(canteenId, memberId, startDate, endDate);
            for (MealRecord m : mealRecords) {
                StringBuilder str = new StringBuilder("");
                for (String s : m.getRecipeNames()) {
                    str.append(s).append("、");
                }
                m.setEatMenus(str.toString().substring(0, str.length() - 1));
            }
            lsResponse.setData(mealRecords);
        } catch (Exception e) {
            logger.error("查询会员当天的消费明细失败===={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("查询失败");
        }
        return lsResponse;
    }

    /**
     * @param user
     * @param canteenId
     * @param memberId
     * @param nutritionFlag
     * @param timeFlag      0：上月  1：上周  2:本月  3：上周
     * @return
     */
    @Override
    public LsResponse getMemberNutrition(User user, String canteenId, Integer memberId, Integer nutritionFlag, Integer timeFlag) {
        LsResponse lsResponse = LsResponse.newInstance();
        String startTime = "";
        String endTime = "";
        List<Object> list = new ArrayList<>();
        Float calorie = 0F;//卡路里
        Float protein = 0F;//蛋白质
        Float fat = 0F;//脂肪
        Float carbonhydrate = 0F;//碳水化合物
        Float vc = 0F;//维生素C
        Map<String, Float> returnMap = new HashMap<>();
        try {
            if (timeFlag == 0) {
                startTime = Dates.getLastMonthFirstDay();
                endTime = Dates.getLastMonthEndDay();
            } else if (timeFlag == 1) {
                startTime = Dates.getLastWeekMonday();
                endTime = Dates.getLastWeekSunday();
            } else if (timeFlag == 2) {
                startTime = Dates.getCurrentMonthFirstDay();
                endTime = Dates.now("yyyy-MM-dd");
            } else {
                startTime = Dates.getCurrentWeekMonDay();
                endTime = Dates.now("yyyy-MM-dd");
            }
            List<MealRecord> mealRecords = mealRecordMapper.getMemberNutrition(canteenId, memberId, startTime, endTime);
            if (mealRecords.size() > 0 && mealRecords != null) {
                Map<String, List<MealRecord>> map = new HashMap<>();
                for (MealRecord mealRecord : mealRecords) {
                    List<MealRecord> tempList = map.get(mealRecord.getPayDate());
                    /*如果取不到数据,那么直接new一个空的ArrayList**/
                    if (tempList == null) {
                        tempList = new ArrayList<>();
                        tempList.add(mealRecord);
                        map.put(mealRecord.getPayDate(), tempList);
                    } else {
                        /*某个sku之前已经存放过了,则直接追加数据到原来的List里**/
                        tempList.add(mealRecord);
                    }
                }
                for (String payDate : map.keySet()) {
                    List<MealRecord> records = map.get(payDate);
                    if (records != null && records.size() > 0) {
                        for (MealRecord record : records) {
                            if (record.getNutritionVo() != null) {
                                calorie += record.getNutritionVo().getCalorie();
                                protein += record.getNutritionVo().getProtein();
                                fat += record.getNutritionVo().getFat();
                                carbonhydrate += record.getNutritionVo().getCarbonhydrate();
                                vc += record.getNutritionVo().getVc();
                            }
                        }
                    }
                    switch (nutritionFlag) {
                        case 0:
                            returnMap.put(payDate, calorie);
                            break;
                        case 1:
                            returnMap.put(payDate, protein);
                            break;
                        case 2:
                            returnMap.put(payDate, fat);
                            break;
                        case 3:
                            returnMap.put(payDate, carbonhydrate);
                            break;
                        case 4:
                            returnMap.put(payDate, vc);
                            break;
                    }
                }
//                lsResponse.setData(map);
                lsResponse.setData(returnMap);
            } else {
                lsResponse.checkSuccess(false, "无数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error("查询失败" + e.toString());
        }
        return lsResponse;
    }
}
