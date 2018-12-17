package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.CartMapper;
import com.lswd.youpin.dao.lsyp.RecipeOrderMapper;
import com.lswd.youpin.dao.lsyp.RecipePlanItemsMapper;
import com.lswd.youpin.dao.lsyp.RecipePlanMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipePlanService;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/20.
 */
@Service
public class RecipePlanServiceImpl implements RecipePlanService {

    private final Logger log = LoggerFactory.getLogger(RecipePlanServiceImpl.class);
    @Autowired
    private RecipePlanMapper recipePlanMapper;
    @Autowired
    private RecipePlanItemsMapper recipePlanItemsMapper;
    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public LsResponse insertRecipePlan(User user,List<RecipePlan> recipePlen){
        LsResponse lsResponse = LsResponse.newInstance();
        SimpleDateFormat yearmd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            log.info("菜谱计划插入的方法正在执行，是由"+user.getUsername()+"进行的");
            String dinnerTime =  recipePlen.get(0).getDate();
            String startdate = "";
            String enddate = "";
            if (!("").equals(dinnerTime) && dinnerTime != null){
                startdate = dinnerTime + " 00:00:00";   //开始时间为就餐时间+00：00：00s
                enddate = Dates.getNextDay(4,yearmd.parse(startdate));//结束时间为就餐时间5天后+23：59：59
            }else {
                startdate = Dates.now("yyyy-MM-dd 00:00:00");
                enddate = Dates.getNextDay(4,Dates.now());
            }
            List<RecipePlan> rplist = recipePlanMapper.getRecipePlanDetailsList("",recipePlen.get(0).getCanteenId(),startdate,enddate);
            for (RecipePlan plan :rplist){
                recipePlanMapper.deleteRecipePlan(plan.getId());//真删
                recipePlanItemsMapper.deletePlanItemsByRecipePlanIdTrue(plan.getRecipePlanId());//真删
            }
            int planflag = 0;
            for (RecipePlan recipePlan:recipePlen){
                if (recipePlan.getPlanItemses().size() == 0 || recipePlan.getPlanItemses() == null){
                    continue;
                }
                Integer max = recipePlanMapper.getMaxId();
                if (max == null){
                    max = 0;
                }
                int number = max + 100000;
                recipePlan.setRecipePlanId("RP"+number);//设置菜谱计划编号
                recipePlan.setDinnerTime(Dates.toDate(recipePlan.getDate(),"yyyy-MM-dd"));//设置就餐时间
                recipePlan.setCreateUser(user.getUsername());
                recipePlan.setCreateTime(Dates.now());
                recipePlan.setUpdateUser(user.getUsername());
                recipePlan.setUpdateTime(Dates.now());
                recipePlan.setComment("餐厅编号为："+recipePlan.getCanteenId()+"的"+recipePlan.getDate()+"计划");
                planflag = recipePlanMapper.insertRecipePlan(recipePlan);
                if (planflag > 0 && recipePlan.getPlanItemses().size() > 0){
                    log.info("菜谱计划插入的方法正在执行，是由"+user.getUsername()+"进行的");
                    for (RecipePlanItems items : recipePlan.getPlanItemses()){items.setRecipePlanId(recipePlan.getRecipePlanId());

                        items.setRecipeId(items.getRecipe().getRecipeId());
                        if (items.getRecipe().getSurplus() != 0){//设置库存
                            items.setSurplus(items.getRecipe().getSurplus());
                        }else {
                            items.setSurplus(items.getSurplus());
                        }
                        if (items.getPriceDay() != 0){//设置菜品当天售卖的价格
                            items.setPriceDay(items.getPriceDay());
                        }else {
                            items.setPriceDay(items.getRecipe().getGuidePrice());
                        }
                        if (items.getMarketPriceDay() != 0){//设置菜品当天的市场价格
                            items.setMarketPriceDay((items.getMarketPriceDay()));
                        }else {
                            items.setMarketPriceDay(items.getRecipe().getMarketPrice());
                        }
                        log.info("正在设置is_hot字段 start");
                        if (items.isHot()){//设置是否是热销的
                            items.setHot(true);
                            log.info("hot:::::true");
                        }else if (items.getRecipe().isHot()){
                            items.setHot(items.getRecipe().isHot());
                            log.info("hot:::::"+items.getRecipe().isHot());
                        }
                        log.info("正在设置is_hot字段 end");
                        recipePlanItemsMapper.insertPlanItems(items);
                    }
                }
            }
            if (planflag  == 0) {
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_INSERTRECIPEPLAN_ERR.getMsg());
            }else {
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_INSERTRECIPEPLAN_SUCCESS.getMsg());//返回后台成功的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            lsResponse.setErrorCode("500");
            log.error("新建菜品计划失败，发生异常，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteRecipePlan(Integer id, User user){
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null){
                log.info("菜品计划删除的方法正在执行，是由"+user.getUsername()+"进行的,删除的是主键为"+id+"的菜品计划");
                RecipePlan recipePlan = recipePlanMapper.selectByPrimaryKey(id);
                if (recipePlan != null){
                    recipePlan.setUpdateTime(Dates.now());
                    recipePlan.setUpdateUser(user.getUsername());
                    int planflag = recipePlanMapper.deleteRecipePlanIsDelete(recipePlan);//根据菜品计划的主键id删除菜品计划主表中的信息，修改状态
                    int itemsflag = recipePlanItemsMapper.deletePlanItemsByRecipePlanIdIsDelete(recipePlan.getRecipePlanId());//根据菜品计划编号，删除菜品计划详情表中的信息,修改状态
                    if (planflag > 0 ){
                        lsResponse.setMessage(CodeMessage.RECIPEPLAN_DELETERECIPEPLAN_SUCCESS.getMsg());//返回前台成功的信息
                        log.info("菜品计划删除的方法正在执行，是由"+user.getUsername()+"进行的，成功删除数据");
                        if (itemsflag > 0){
                            log.info("{删除菜品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划",  JSON.toJSON(user.getUsername() + "商品计划详情编号为"+recipePlan.getRecipePlanId()+"删除成功" ));
                        }else {
                            log.info("{删除菜品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划", JSON.toJSON(user.getUsername() + "商品计划详情编号为"+recipePlan.getRecipePlanId()+"删除失败" ));
                        }
                    }else{
                        lsResponse.setSuccess(false);
                        lsResponse.setMessage(CodeMessage.RECIPEPLAN_DELETERECIPEPLAN_ERR.getMsg());//返回前台失败的信息
                        log.info("菜品计划删除的方法正在执行，是由"+user.getUsername()+"进行的，数据删除失败");
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜品计划删除时失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteRecipePlanOneRecipe(String recipePlanId, String recipeId, Integer recipeType, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipePlanId != null && recipeId != null && recipeType != null){
                log.info("{删除菜品计划中的某个菜品} is being executed. User = {"+user.getUsername()+"}", "删除菜品计划中的某个菜品",
                        JSON.toJSON( "正准备删除计划单号为："+recipePlanId+"中的,类型为"+recipeType+"的菜品编号为："+recipeId+"的菜品" ));
                int delFlag = recipePlanItemsMapper.deleteRecipePlanOneRecipeTrue(recipePlanId,recipeId,recipeType);
                List<RecipePlanItems> recipePlanItems = recipePlanItemsMapper.getPlanItemsByRecipePlanId(recipePlanId);
                if (recipePlanItems.size() == 0){
                    recipePlanItemsMapper.deletePlanItemsByRecipePlanIdTrue(recipePlanId);//真删
                    recipePlanMapper.deleteRecipePlanByRecipePlanIdTrue(recipePlanId);//真删
                }
                if (delFlag > 0){
                    lsResponse.setMessage("删除成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("删除失败");
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("计划单号或者是菜品编号或者是recipeTtype是null，故删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除菜品计划中的某个菜品失败,发生异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateRecipePlan(RecipePlan recipePlan, User user){
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("菜谱计划修改的方法正在执行，是由"+user.getUsername()+"进行的");
            recipePlan.setUpdateTime(Dates.now());
            recipePlan.setUpdateUser(user.getUsername());
            int planflag = recipePlanMapper.updateRecipePlan(recipePlan);
            if (planflag>0 && recipePlan.getPlanItemses().size() > 0){
                for (int i = 0;i< recipePlan.getPlanItemses().size();i++) {
                    int itemsflag = recipePlanItemsMapper.updatePlanItemsSurplus(recipePlan.getPlanItemses().get(i));
                    if (itemsflag >0) {
                        log.info("菜谱计划修改的方法正在执行，是由" + user.getUsername() + "进行的，成功修改的库存");
                        lsResponse.setMessage("修改菜谱计划详情时成功");//返回后台成功的信息
                    } else {
                        lsResponse.setSuccess(false);
                        lsResponse.setMessage("修改菜谱计划详情时失败");
                        log.info("菜谱计划修改的方法正在执行，是由" + user.getUsername() + "进行的，数据修改失败");
                    }
                }
            }else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_UPDATERECIPEPLAN_ERR.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("修改菜谱计划时发生异常，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateRecipePlanOneDay(RecipePlan recipePlan, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("菜谱计划修改的方法正在执行，是由"+user.getUsername()+"进行的");
            recipePlan.setUpdateTime(Dates.now());
            recipePlan.setUpdateUser(user.getUsername());
            int flag = recipePlanMapper.updateRecipePlan(recipePlan);
            if (flag>0 && recipePlan.getPlanItemses().size() > 0){
                for (int i = 0;i< recipePlan.getPlanItemses().size();i++) {
                    //if (recipePlan.getPlanItemses().get(i).getRecipe().getRecipeName().equals("")) break;
                    int itemsflag = recipePlanItemsMapper.updatePlanItemsSurplus(recipePlan.getPlanItemses().get(i));
                    if (itemsflag >0) {
                        log.info("菜谱计划修改的方法正在执行，是由" + user.getUsername() + "进行的，成功修改数据");
                        lsResponse.setMessage("修改菜谱计划成功");
                    } else {
                        lsResponse.setSuccess(false);
                        lsResponse.setMessage("修改菜谱计划失败");
                    }
                }
            }else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_UPDATERECIPEPLAN_ERR.getCode());
                log.error("修改菜谱计划失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("修改菜谱计划时发生异常，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipePlanListWebPageShow(User user,String canteenId, String eatTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String[] canteenIds = user.getCanteenIds().split(",");
            if (canteenId != null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            String startdate = "";
            String enddate = "";
            if (eatTime != null && !("").equals(eatTime)){
                startdate = eatTime + " 00:00:00";
                enddate = eatTime + " 23:59:59";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            String now = Dates.format(new Date(),"yyyy-MM-dd 00:00:00");

//            List<RecipePlan> recipePlen = recipePlanMapper.getRecipePlanBeforeNow(now);//将今天之前的列表查询出来，修改他们的状态为1，为已过期
//            if (recipePlen != null && recipePlen.size() >0){
//                for (RecipePlan plan : recipePlen){
//                    plan.setUpdateUser(user.getUsername());
//                    plan.setUpdateTime(Dates.now());
//                    recipePlanMapper.updateRecipePlanIsDelete(plan);
//                }
//            }

            int total = recipePlanMapper.getRecipePlanListCountWebPageShow(canteenId,now,startdate,enddate,canteenIds);
            List<RecipePlan> planList = recipePlanMapper.getRecipePlanListWebPageShow(canteenId,now,startdate,enddate,canteenIds,offSet,pageSize);
            for (RecipePlan recipePlan :planList){
                recipePlan.setStatusName(recipePlan.getDelete()?"已过期":"正常使用");
                List<RecipePlanItems> items = recipePlanItemsMapper.getPlanItemsByRecipePlanId(recipePlan.getRecipePlanId());
                if (items != null && items.size() >0){
                    recipePlan.setPlanItemses(items);
                }
                //Canteen canteen = canteenMapper.getCanteenByCanteenId(recipePlan.getCanteenId());得不到餐厅名称，不同的数据库
            }
            if (planList != null&& planList.size() > 0){
                lsResponse.setData(planList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("数据查询成功");
            }else{
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("数据查询失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("数据查询失败，发生异常"+e.getMessage());
            log.error("菜品计划WEB端，列表数据查询失败，发生异常，异常信息为:"+e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据菜品计划编号添加备注
     */
    @Override
    public LsResponse addCommentByRecipePlanId(String recipePlanId, String comment) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (comment != null && !"".equals(comment)){
                comment = new String(comment.getBytes("iso8859-1"),"utf-8");
            }else{
                comment = "";
            }
            int addflag =  recipePlanMapper.addCommentByRecipePlanId(recipePlanId,comment);
            if (addflag > 0){
                lsResponse.setMessage("备注添加成功！");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("备注添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("备注添加失败，失败原因为"+e.toString());//返回后台成功的信息
            log.error("菜品计划备注添加失败，失败原因为"+e.toString());
        }
        return lsResponse;
    }

    /**
     *菜品计划新增时，根据就餐时间查询就餐时间以后的5天数据
     */
    @Override
    public LsResponse getRecipePlanDetailsList(String keyword, String canteenId,String dinnerTime) {
        LsResponse lsResponse = LsResponse.newInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb  = new StringBuilder();
        try {
            if (keyword != null && !"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
            }else{
                keyword = "";
            }
            if (canteenId != null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            String startdate = "";
            String enddate = "";
            if (dinnerTime != null && !("").equals(dinnerTime)){
                startdate = dinnerTime + " 00:00:00";   //开始时间为就餐时间+00：00：00
                enddate = Dates.getNextDay(4,dateFormat.parse(startdate));//结束时间为就餐时间7天后+23：59：59
            }else {
                startdate = Dates.now("yyyy-MM-dd 00:00:00");
                enddate = Dates.getNextDay(4,Dates.now());
            }
            List<RecipePlan> recipePlen = recipePlanMapper.getRecipePlanDetailsList(keyword,canteenId,startdate,enddate);
            if (recipePlen.size() == 5){//如果goodPlen的size == 5，说明数据库中有近五天的计划，只需要将每天的date和weekday赋值即可！
                for (int i =0;i<5;i++){
                    recipePlen.get(i).setDate(Dates.getNextDayFormat(i,dateFormat.parse(startdate.substring(0,10))));
                    recipePlen.get(i).setWeekday(Dates.getWeekOfDate(Dates.toDate(recipePlen.get(i).getDate(),"yyyy-MM-dd")));
                }
                lsResponse.setData(recipePlen);
                lsResponse.setMessage("数据列表成功显示,一共五条数据");//返回后台成功的信息
                log.info("WEB端，菜品计划数据列表成功显示,一共五条数据");
            }else {
                List<RecipePlan> plans = new ArrayList<>();
                for (int i = 0;i < 5; i++){
                    RecipePlan recipePlan = new RecipePlan();
                    recipePlan.setDate(Dates.getNextDayFormat(i,dateFormat.parse(startdate.substring(0,10))));
                    recipePlan.setWeekday(Dates.getWeekOfDate(Dates.toDate(recipePlan.getDate(),"yyyy-MM-dd")));
                    recipePlan.setDinnerTime(dateFormat.parse(Dates.getNextDayFormat(i,dateFormat.parse(startdate.substring(0,10)))));
                    plans.add(recipePlan);
                }
                for (RecipePlan recipePlan : plans){
                    for (int i= 0;i < recipePlen.size() ;i++){
                        String time = recipePlan.getDate().replace("-","");
                        String pick = Dates.format(recipePlen.get(i).getDinnerTime(),"yyyyMMdd");
                        if (Long.valueOf(pick).equals(Long.valueOf(time))){
                            recipePlan.setRecipePlanId(recipePlen.get(i).getRecipePlanId());
                            recipePlan.setCanteenId(recipePlen.get(i).getCanteenId());
                            recipePlan.setPlanItemses(recipePlen.get(i).getPlanItemses());
                            break;
                        }else {
                            recipePlan.setPlanItemses(new ArrayList<>());
                        }
                    }
                    if (recipePlen.size() == 0){
                        //recipePlan.setDinnerTime(dateFormat.parse(startdate.substring(0,10)));
                        recipePlan.setPlanItemses(new ArrayList<>());
                    }
                }
                lsResponse.setData(plans);
                lsResponse.setMessage("数据列表成功显示");//返回后台成功的信息
                log.info("WEB端 数据列表成功显示，菜品计划数据库中的计划不足5条，共" + plans.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
            //lsResponse.setMessage("查询数据列表时，发生异常");
            log.error("菜品计划点击新增，请求5天的计划时，发生异常,异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 暂时没有用到！！！
     */
    @Override
    public LsResponse getRecipePlanDetails(String recipePlanId) {
        LsResponse lsResponse=new LsResponse();
        try {
            recipePlanId = new String(recipePlanId.getBytes("iso8859-1"),"utf-8");
            RecipePlan recipePlan = recipePlanMapper.getRecipePlanByRecipePlanId(recipePlanId);//根据菜品计划编号查询商品计划表
            List<RecipePlanItems> recipePlanItems = recipePlanItemsMapper.getPlanItemsByRecipePlanId(recipePlanId);//根据菜品计划编号查询菜品计划详情表
            if (recipePlanItems != null && recipePlanItems.size() > 0 ) {
                recipePlan.setPlanItemses(recipePlanItems);
            }
            if (recipePlan != null){
                lsResponse.setData(recipePlan);
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_GETRECIPEPLANDETAILS_SUCCESS.getMsg());
                log.info("菜品计划详情查看成功，计划单号为："+recipePlan);
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPEPLAN_GETRECIPEPLANDETAILS_ERR.getMsg());
                log.info("菜品计划详情查看成功，计划单号为："+recipePlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜品计划详情查看失败",e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据时间得到recipePlan
     * @param recipePlen
     * @param date  ，日期格式为 yyyy-MM-dd
     * @return
     */
    public RecipePlan getRecipePlanByDinnerTime(List<RecipePlan> recipePlen,String date){
        RecipePlan recipePlan = new RecipePlan();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0;i<recipePlen.size();i++){
            String formatdate = format.format(recipePlen.get(i).getDinnerTime());
            if (date.equals(formatdate)){
                recipePlan = recipePlen.get(i);
                return recipePlan;
            }
        }
        recipePlan = null;
        return recipePlan;
    }



    /*------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /*以下是H5画面用到的方法，方法补充*/
    @Override
    public LsResponse getEatingDetailsH5Show(Associator associator,String keyword,String canteenId, String dinnerTime, Integer categoryId, Integer eatType, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        StringBuilder sb  = new StringBuilder();
        try {
            if (dinnerTime != null && !("").equals(dinnerTime)){
                log.info("dinnerTime 为"+dinnerTime.toString());
                dinnerTime = new String (dinnerTime.getBytes("iso8859-1"),"utf-8");
               /* String now = Dates.now("yyyyMMdd");
                if (Long.valueOf(dinnerTime.replace("-","")) <= Long.valueOf(now)){//今天之前的计划设置为已失效
                    lsResponse.setAsFailure();
                    lsResponse.setErrorCode("200");
                    lsResponse.setMessage("亲~本餐厅计划已失效");
                    return lsResponse;
                }*/
            }else {
                 dinnerTime = Dates.now("yyyy-MM-dd");
//                Date dinnerDate = Dates.getAfterDate(Dates.now(),1);
//                dinnerTime = Dates.format(dinnerDate,"yyyy-MM-dd");
            }
            if (keyword != null && !"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
                categoryId = 0;
            }else{
                keyword = "";
            }
            if (canteenId != null && !("").equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else {
                canteenId = "";
            }
            if (eatType == null){
                eatType = 0;
            }
            if (categoryId == null){ //1表示是全部分类，设置为null表示查询所有得分类
                categoryId = 0;
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            //total =  recipePlanMapper.getRecipePlanByCanAndTimeCount(canteenId,dinnerTime);
            RecipePlan recipePlan = recipePlanMapper.getRecipePlanByCanAndTime(canteenId,dinnerTime);//根据餐厅和就餐时间查询 是否该时间有餐厅售卖计划
            if (recipePlan != null){
                log.info("餐厅编号为："+canteenId+"的"+dinnerTime+"计划为"+recipePlan.getRecipePlanId()+",早午晚为"+eatType+"，菜品分类为"+canteenId);
                total = recipePlanItemsMapper.getItemsByPlanIdAndTypeAndCateCount(keyword,recipePlan.getRecipePlanId(),eatType,categoryId);
                List<RecipePlanItems> recipePlanItemses = recipePlanItemsMapper.getItemsByPlanIdAndTypeAndCate(keyword,recipePlan.getRecipePlanId(),eatType,categoryId,offSet,pageSize);
                log.info("总数为"+total);
                if (recipePlanItemses != null && recipePlanItemses.size() >0){
                    String now = Dates.now("yyyyMMdd");
                    for (RecipePlanItems items : recipePlanItemses){
                        Integer count = recipeOrderMapper.getRecipeMonthSales(canteenId,items.getRecipeId());//查询的over表中的数据，近30天的
                        Cart cart = cartMapper.getCartByCanAndGoodIdAndAsso(canteenId,items.getRecipeId(),associator.getAssociatorId(),dinnerTime,eatType+1);//根据餐厅编号、商品编号、会员编号和取货时间,餐次查询购物车
                        if (count != null){
                            items.getRecipe().setMonthSale(count);
                        }
                        if (cart != null){
                            items.getRecipe().setNum(cart.getQuantity());
                            if (items.getRecipe().getNum() >0){
                                items.getRecipe().setChecked(true);
                            }
                        }
                        if (Long.valueOf(dinnerTime.replace("-","")) <  Long.valueOf(now)){
                            items.setSurplus(0);
                            items.getRecipe().setNum(0);
                        }
                    }
                    lsResponse.setMessage("您好，这是今天要出售的菜品");
                    lsResponse.setData(recipePlanItemses);
                    lsResponse.setTotalCount(total);
                    log.info("您好，这是今天要出售的菜品");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setErrorCode("201");
                    if (keyword != null && !"".equals(keyword)){
                        lsResponse.setMessage("亲~不好意思，本餐厅未搜到您想要的菜品");
                        log.error("关键字是"+keyword+",本餐厅未搜到您想要的菜品");
                    }else {
                        if (categoryId == 0){
                            switch (eatType){
                                case 0: lsResponse.setMessage("不好意思，暂时没有早餐提供");break;
                                case 1: lsResponse.setMessage("不好意思，暂时没有午餐提供");break;
                                case 2: lsResponse.setMessage("不好意思，暂时没有晚餐提供");break;
                                default:lsResponse.setMessage("亲~本餐厅该类目没有出售的菜品");break;
                            }
                        }else {
                            lsResponse.setMessage("亲~本餐厅该类目没有出售的菜品");
                        }
                        log.error("亲~本餐厅该类目暂时没有出售的菜品,数据库查询的数据为空的！！！");
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setErrorCode("202");
                if (eatType == 0){
                    lsResponse.setMessage("不好意思，暂时没有早餐提供");
                    log.info("不好意思，暂时没有早餐提供");
                }else if (eatType == 1){
                    lsResponse.setMessage("不好意思，暂时没有午餐提供");
                    log.info("不好意思，暂时没有午餐提供");
                }else if (eatType == 2){
                    lsResponse.setMessage("不好意思，暂时没有晚餐提供");
                    log.info("不好意思，暂时没有晚餐提供");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            //lsResponse.setMessage("不好意思，后台出错了，出错信息为："+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("H5端，查询菜品时，出错，出错信息为："+e.toString());
        }
        return lsResponse;
    }
    /*if (sortFlag.equals("sale")){
            Collections.sort(recipePlanItemses, new Comparator<RecipePlanItems>() {
                @Override
                public int compare(RecipePlanItems r1, RecipePlanItems r2) {
                    int i = r2.getRecipe().getMonthSale() - r1.getRecipe().getMonthSale();//首先按照销量排序
                    if (i == 0){
                        return Math.round(r1.getRecipe().getGuidePrice()) - Math.round(r2.getRecipe().getGuidePrice());//如果销量相同，继续按照价格进行排序，价格低的在前面
                    }
                    return i;//第一个参数：负整数      第二个参数：零或正整数。
                }
            });
        }*/

}
