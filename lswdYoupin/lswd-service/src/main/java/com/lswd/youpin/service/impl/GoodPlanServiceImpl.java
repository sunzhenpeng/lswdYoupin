package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Cart;
import com.lswd.youpin.model.lsyp.GoodPlan;
import com.lswd.youpin.model.lsyp.GoodPlanItems;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhenguanqi on 2017/6/15.
 */
@Service
public class GoodPlanServiceImpl implements GoodPlanService {
    private final Logger log = LoggerFactory.getLogger(GoodPlanServiceImpl.class);

    @Autowired
    private GoodPlanMapper goodPlanMapper;
    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private GoodOrderMapper goodOrderMapper;
    @Autowired
    private CartMapper cartMapper;

    /**
     * 新增商品计划
     */
    @Override
    public LsResponse addGoodPlan(List<GoodPlan> goodPlen, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        SimpleDateFormat yearmd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (goodPlen != null){
                log.info("{} is being executed. User = {}", "添加商品计划", JSON.toJSON(user.getUsername() + "准备追加新的商品计划" ));
                String pickingTime =  goodPlen.get(0).getDate();
                /*首先获取第一天的取货时间，根据第一天的时间将以后的计划以及计划详情查询出来，然后将其删除*/
                String startdate = "";
                String enddate = "";
                if (!("").equals(pickingTime) && pickingTime != null){
                    startdate = pickingTime + " 00:00:00";   //开始时间为就餐时间+00：00：00
                    enddate = Dates.getNextDay(4,yearmd.parse(startdate));//结束时间为就餐时间7天后+23：59：59
                }else {
                    startdate = Dates.now("yyyy-MM-dd 00:00:00");
                    enddate = Dates.getNextDay(4,Dates.now());
                }
                List<GoodPlan> plen = goodPlanMapper.getGoodPlanDetailsList("",goodPlen.get(0).getCanteenId(),startdate,enddate);
                for (GoodPlan plan :plen){
                    goodPlanMapper.deleteGoodPlanByGoodPlanId(plan.getGoodPlanId());//修改状态
                    goodPlanItemsMapper.deleteByGoodPlanId(plan.getGoodPlanId());//修改状态
                }
                int planflag = 0;
                //设置操作该表的一些用户信息
                for (GoodPlan goodPlan : goodPlen){
                    if (goodPlan.getGoodPlanItemses().size() == 0 || goodPlan.getGoodPlanItemses() == null){
                        continue;
                    }
                    Integer max = goodPlanMapper.getMaxId();
                    if (max == null){
                        max = 0;
                    }
                    int number = max + 100000;
                    goodPlan.setGoodPlanId("GP"+number);
                    goodPlan.setCreateUser(user.getUsername());
                    goodPlan.setCreateTime(Dates.now());
                    goodPlan.setUpdateUser(user.getUsername());
                    goodPlan.setUpdateTime(Dates.now());
                    goodPlan.setComment("餐厅编号为："+goodPlan.getCanteenId()+"的"+goodPlan.getDate()+"计划");
                    goodPlan.setPickingTime(Dates.toDate(goodPlan.getDate(),"yyyy-MM-dd"));
                    planflag = goodPlanMapper.insertGoodPlan(goodPlan);//主表插入
                    if (planflag > 0 && goodPlan.getGoodPlanItemses().size() > 0){
                        log.info("商品计划详情插入的方法正在执行，是由"+user.getUsername()+"进行的");
                        for (GoodPlanItems items : goodPlan.getGoodPlanItemses()){
                            items.setGoodPlanId(goodPlan.getGoodPlanId());
                            items.setGoodId(items.getGood().getGoodId());
                            if (items.getGood().getSurplus() != 0){//设置库存
                                items.setSurplus(items.getGood().getSurplus());
                            }else {
                                items.setSurplus(items.getSurplus());
                            }
                            if (items.getPriceDay() != 0){//设置销售价格
                                items.setPriceDay(items.getPriceDay());
                            }else {
                                items.setPriceDay(items.getGood().getPrice());
                            }
                            if (items.getMarketPriceDay() != 0){//设置市场价格
                                items.setMarketPriceDay((items.getMarketPriceDay()));
                            }else {
                                items.setMarketPriceDay(items.getGood().getMarketPrice());
                            }
                            if (items.isHot()){//设置是否是热销的
                                items.setHot(true);
                            }else {
                                items.setHot(items.getGood().isHot());
                            }
                            goodPlanItemsMapper.insertGoodPlanItems(items);//详情表插入
                        }
                    }
                }
                if (planflag == 1){
                    lsResponse.setMessage("商品计划添加成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品计划添加失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.info(user.getUsername()+"添加商品计划时,发生异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 删除商品计划,根据id进行删除
     */
    @Override
    public LsResponse deleteGoodPlan(Integer id, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null){
                log.info("{删除商品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划", JSON.toJSON( "正准备删除主键为"+id+"商品计划" ));
                GoodPlan goodPlan = goodPlanMapper.getGoodPlanById(id);
                if (goodPlan != null){
                    int planflag = goodPlanMapper.deleteByPrimaryKey(id);
                    int itemsflag = goodPlanItemsMapper.deleteByGoodPlanId(goodPlan.getGoodPlanId());
                    if (planflag > 0){
                        lsResponse.setMessage(CodeMessage.GOODPLAN_DELETEGOODPLAN_SUCCESS.getMsg());
                        log.info("{删除商品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划",  JSON.toJSON(user.getUsername() + "删除商品计划主键为"+id+"成功" ));
                        if (itemsflag > 0){
                            log.info("{删除商品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划",  JSON.toJSON(user.getUsername() + "商品计划详情编号为"+goodPlan.getGoodPlanId()+"删除成功" ));
                        }else {
                            log.info("{删除商品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划", JSON.toJSON(user.getUsername() + "商品计划详情编号为"+goodPlan.getGoodPlanId()+"删除失败" ));
                        }
                    }else{
                        log.info("{删除商品计划} is being executed. User = {"+user.getUsername()+"}", "删除商品计划",  JSON.toJSON(user.getUsername() + "删除商品计划主键为"+id+"失败" ));
                        lsResponse.setSuccess(false);
                        lsResponse.setMessage(CodeMessage.GOODPLAN_DELETEGOODPLAN_ERR.getMsg());
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除商品计划失败,发生异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 删除商品计划中的某个商品,根据商品计划单号和商品编号进行删除
     */
    @Override
    public LsResponse deleteGoodPlanOneGood(String goodPlanId, String goodId, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (goodPlanId != null && goodId != null){
                log.info("{删除商品计划中的某个商品} is being executed. User = {"+user.getUsername()+"}", "删除商品计划中的某个商品",
                        JSON.toJSON( "正准备删除计划单号为："+goodPlanId+"中的商品编号为："+goodId+"的商品" ));
                int delFlag = goodPlanItemsMapper.deleteGoodPlanOneGood(goodPlanId,goodId);
                List<GoodPlanItems> goodPlanItems = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlanId);
                if (goodPlanItems.size() == 0){
                    goodPlanMapper.deleteGoodPlanByGoodPlanId(goodPlanId);//修改状态
                    goodPlanItemsMapper.deleteByGoodPlanId(goodPlanId);//修改状态
                }
                if (delFlag > 0){
                    lsResponse.setMessage("删除成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("删除失败");
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("计划单号或者是商品编号是null，故删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除商品计划中的某个商品失败,发生异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 更新商品计划(修改一天的计划)
     */
    @Override
    public LsResponse updateGoodPlan(GoodPlan goodPlan, User user){
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "正准备更新商品计划" ));
            //设置修改该表的一些用户信息
            goodPlan.setUpdateUser(user.getUsername());
            goodPlan.setUpdateTime(Dates.now());
            goodPlan.setPickingTime(Dates.toDate(goodPlan.getDate(),"yyyy-MM-dd"));
            int planflag = goodPlanMapper.updateGoodPlan(goodPlan);
            goodPlanItemsMapper.deleteByGoodPlanId(goodPlan.getGoodPlanId());
            for (int i=0;i<goodPlan.getGoodPlanItemses().size();i++){
                int itemsflag = goodPlanItemsMapper.insertGoodPlanItems(goodPlan.getGoodPlanItemses().get(i));
                if (itemsflag > 0){
                    log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "更新第"+i+"商品计划成功" ));
                    //lsResponse.setMessage(CodeMessage.GOODPLAN_UPDATEGOODPLAN_SUCCESS.getMsg());
                }else{
                    log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "更新第"+i+"商品计划失败" ));
                   // lsResponse.setSuccess(false);
                   // lsResponse.setMessage(CodeMessage.GOODPLAN_UPDATEGOODPLAN_ERR.getMsg());
                }
            }
            if (planflag > 0){
                lsResponse.setMessage("修改商品计划成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("修改商品计划失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setMessage("更新商品计划是发生异常信息，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 更新商品计划(修改一天的计划)Web端，方法和上面的方法大致一样，用的是这个方法
     */
    @Override
    public LsResponse updateGoodPlanOneDay(GoodPlan goodPlan, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "正准备更新商品计划" ));
            goodPlan.setUpdateUser(user.getUsername());
            goodPlan.setUpdateTime(Dates.now());
            int flag = goodPlanMapper.updateGoodPlan(goodPlan);
            if (flag > 0 && goodPlan.getGoodPlanItemses().size() > 0){
                for (int i=0;i<goodPlan.getGoodPlanItemses().size();i++){
                    //if (goodPlan.getGoodPlanItemses().get(i).getGood().getGoodName().equals("")) break;
                    int itemsflag = goodPlanItemsMapper.updatePlanItemsOneDay(goodPlan.getGoodPlanItemses().get(i));
                    if (itemsflag > 0){
                        log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "成功修改商品计划中商品的库存、售卖价格、市场价格" ));
                        lsResponse.setMessage(CodeMessage.GOODPLAN_UPDATEGOODPLAN_SUCCESS.getMsg());
                    }else{
                        log.info("{} is being executed. User = {}", "更新商品计划", JSON.toJSON(user.getUsername() + "修改商品计划中商品的库存、售卖价格、市场价格失败" ));
                        lsResponse.setSuccess(false);
                        lsResponse.setMessage(CodeMessage.GOODPLAN_UPDATEGOODPLAN_ERR.getMsg());
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("修改商品库存、售卖价格、市场价格失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    /**
     * 查看商品计划列表,web段首页列表展示
     */
    @Override
    public LsResponse getGoodPlanListWebPageShow(User user,String canteenId,String pickingTime,Integer pageNum, Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        try {
            String[] canteenIds = user.getCanteenIds().split(",");
            if(canteenId!=null&&!"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            String starttime = "";
            String endtime = "";
            if(pickingTime!=null&&!"".equals(pickingTime)){
                starttime = pickingTime + " 00:00:00";
                endtime = pickingTime + " 23:59:59";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            String now = Dates.format(new Date(),"yyyy-MM-dd 00:00:00");
//            List<GoodPlan> goodPlen = goodPlanMapper.getGoodPlanBeforeNow(now);
//            if (goodPlen != null && goodPlen.size() >0){
//                for (GoodPlan plan :goodPlen){
//                    plan.setUpdateUser(user.getUsername());
//                    plan.setUpdateTime(Dates.now());
//                    goodPlanMapper.updateGoodPlanIsDelete(plan);
//                }
//            }
            int total = goodPlanMapper.getGoodPlanListCountWebPageShow(now,canteenId,starttime,endtime,canteenIds);
            List<GoodPlan> goodPlanlist = goodPlanMapper.getGoodPlanListWebPageShow(now,canteenId,starttime,endtime,canteenIds,offSet,pageSize);
            for (GoodPlan goodPlan : goodPlanlist){
                goodPlan.setStatusName(goodPlan.getDelete()?"已过期":"正常启用");
                List<GoodPlanItems> planItemses = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlan.getGoodPlanId());
                if (planItemses != null && planItemses.size() > 0){
                    goodPlan.setGoodPlanItemses(planItemses);
                }
            }
            if (goodPlanlist != null && goodPlanlist.size() > 0 ){
                lsResponse.setTotalCount(total);
                lsResponse.setData(goodPlanlist);
                lsResponse.setMessage(CodeMessage.GOODPLAN_GETGOODPLANLIST_SUCCESS.getMsg());
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage(CodeMessage.GOODPLAN_GETGOODPLANLIST_ERR.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("Web端，查询商品计划列表发生异常，异常信息为",e.toString());
        }
        return lsResponse;
    }

    /**
     * 查看商品计划详情(未用到!)
     */
    @Override
    public LsResponse getGoodPlanDetails(String goodPlanId) {
        LsResponse lsResponse=new LsResponse();
        try {
            goodPlanId = new String(goodPlanId.getBytes("iso8859-1"),"utf-8");
            GoodPlan goodPlan = goodPlanMapper.getGoodPlanByGoodPlanId(goodPlanId);//根据商品计划编号查询商品计划表
            List<GoodPlanItems> goodPlanItemslist = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlanId);//根据商品计划编号查询商品计划详情表
            if (goodPlanItemslist != null && goodPlanItemslist.size() > 0 ) {
                goodPlan.setGoodPlanItemses(goodPlanItemslist);
            }
            if (goodPlan != null){
                lsResponse.setData(goodPlan);
                lsResponse.setMessage(CodeMessage.GOODPLAN_GETGOODPLANDETAILS_SUCCESS.name());
                log.info("商品计划详情查看成功，计划单号为："+goodPlanId);
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOODPLAN_GETGOODPLANDETAILS_ERR.name());
                log.info("商品计划详情查看失败，计划单号为："+goodPlanId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取商品计划详情出错",e.toString());
        }
        return lsResponse;
    }

    /**
     * 点击商品计划新增时需要执行的方法，该方法的功能是，将最近一周的计划都查询出来
     */
    @Override
    public LsResponse getGoodPlanDetailsList(String keyword, String canteenId, String pickingTime)  {
        LsResponse lsResponse = LsResponse.newInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longdate = new SimpleDateFormat("yyyyMMdd");
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
            if (pickingTime != null && !("").equals(pickingTime)){
                startdate = pickingTime + " 00:00:00";   //开始时间为取货时间+00：00：00
                enddate = Dates.getNextDay(4,dateFormat.parse(startdate));//结束时间为取货时间7天后+23：59：59
            }else {
                startdate = Dates.now("yyyy-MM-dd 00:00:00");
                enddate = Dates.getNextDay(4,Dates.now());
            }
            List<GoodPlan> goodPlen = goodPlanMapper.getGoodPlanDetailsList(keyword,canteenId,startdate,enddate);
            if (goodPlen.size() == 5){//如果goodPlen的size == 5，说明数据库中有近五天的计划，只需要将每天的date和weekday赋值即可！
                for (int i =0;i<5;i++){
                    goodPlen.get(i).setDate(Dates.getNextDayFormat(i,dateFormat.parse(startdate.substring(0,10))));
                    goodPlen.get(i).setWeekday(Dates.getWeekOfDate(Dates.toDate(goodPlen.get(i).getDate(),"yyyy-MM-dd")));
                }
                lsResponse.setData(goodPlen);
                lsResponse.setMessage("数据列表成功显示,一共五条数据");//返回后台成功的信息
                log.info("WEB端，商品计划数据列表成功显示,一共五条数据");
            }else {
                List<GoodPlan> plans = new ArrayList<>();
                for (int i = 0;i < 5; i++){ //创建一个新的集，用来存储时间和周几
                    GoodPlan goodPlan = new GoodPlan();
                    goodPlan.setDate(Dates.getNextDayFormat(i,dateFormat.parse(startdate.substring(0,10))));
                    goodPlan.setWeekday(Dates.getWeekOfDate(Dates.toDate(goodPlan.getDate(),"yyyy-MM-dd")));
                    plans.add(goodPlan);
                }
                for (GoodPlan goodPlan : plans){
                    for (int i= 0;i < goodPlen.size() ;i++){
                        String time = goodPlan.getDate().replace("-","");
                        String pick = Dates.format(goodPlen.get(i).getPickingTime(),"yyyyMMdd");
                        if (Long.valueOf(pick).equals(Long.valueOf(time))){//如果取货日期 和 新集合中的日期相同，将改天的计划详情 赋值 给新集合，然后退出循环
                            goodPlan.setGoodPlanId(goodPlen.get(i).getGoodPlanId());
                            goodPlan.setPickingTime(goodPlen.get(i).getPickingTime());
                            goodPlan.setCanteenId(goodPlen.get(i).getCanteenId());
                            goodPlan.setGoodPlanItemses(goodPlen.get(i).getGoodPlanItemses());
                            break;
                        }else {
                            List<GoodPlanItems> goodPlanItemses = new ArrayList<>();
                            goodPlan.setGoodPlanItemses(goodPlanItemses);
                        }
                    }
                    if (goodPlen.size() == 0){//如果查询出来的集合的长度为0，new一个新的集合
                        goodPlan.setGoodPlanItemses(new ArrayList<>());
                    }
                }
                lsResponse.setData(plans);
                lsResponse.setMessage("数据列表成功显示");
                log.info("WEB端 数据列表成功显示，商品计划数据库中的计划不足5条，共"+plans.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            //lsResponse.setMessage("查询数据列表时，发生异常");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("商品计划点击新增，请求5天的计划时，发生异常,异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据商品计划编号添加商品计划备注
     */
    @Override
    public LsResponse addCommentByGoodPlanId(String goodPlanId, String comment) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (comment != null && !"".equals(comment)){
                comment = new String(comment.getBytes("iso8859-1"),"utf-8");
            }else{
                comment = "";
            }
            int addflag =  goodPlanMapper.addCommentByGoodPlanId(goodPlanId,comment);
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
            //lsResponse.setMessage("备注添加失败，失败原因为"+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("商品计划添加备注失败，失败原因为"+e.toString());
        }
        return lsResponse;
    }


/*-------------------------------------以下是！H5画面需要用到的方法！！！方法补充-------------------------------------*/
    /**！
     * @param canteenId
     * @param pickingTime
     * @param categoryId
     * @return
     */
    @Override
    public LsResponse getGoodPlanContentH5Show(Associator associator,String keyword,String canteenId, String pickingTime, Integer categoryId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        StringBuilder sb  = new StringBuilder();
        Integer total = 0;
        try {
            if (pickingTime != null && !("").equals(pickingTime)){
                log.info("pickingTime 为"+pickingTime.toString());
                pickingTime = new String(pickingTime.getBytes("iso8859-1"),"utf-8");
               /* String now = Dates.now("yyyyMMdd");
                if (Long.valueOf(pickingTime.replace("-","")) <  Long.valueOf(now)){//今天之前的计划设置为已失效
                    lsResponse.setMessage("亲~本餐厅计划已失效");
                    lsResponse.setErrorCode("200");
                    lsResponse.setAsFailure();
                    return lsResponse;
                }*/
            }else {
                pickingTime = Dates.now("yyyy-MM-dd");
               /* Date pickDate = Dates.getAfterDate(Dates.now(),1);
                pickingTime = Dates.format(pickDate,"yyyy-MM-dd");*/
            }
            if (keyword != null && !"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
                categoryId = 0;
            }else{
                keyword = "";
            }
            if(canteenId != null&&!"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                canteenId = "";
            }
            if (categoryId == null){ //1表示是全部分类，设置为null表示查询所有得分类
                categoryId = 0;
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            GoodPlan goodPlan = goodPlanMapper.getGoodPlanContentByCanAndTime(canteenId,pickingTime);
            if (goodPlan != null){
                total = goodPlanItemsMapper.getItemsByPlanIdAndCateCount(keyword,goodPlan.getGoodPlanId(),categoryId);
                List<GoodPlanItems> planItemses = goodPlanItemsMapper.getItemsByPlanIdAndCate(keyword,goodPlan.getGoodPlanId(),categoryId,offSet,pageSize);
                if (planItemses != null && planItemses.size() >0){
                    String now = Dates.now("yyyyMMdd");
                    for (GoodPlanItems items : planItemses){
                        items.getGood().setPrice(items.getPriceDay());
                        Integer count = goodOrderMapper.getGoodMonthSales(canteenId,items.getGoodId());//查询的over表中的数据，近30天的
                        Cart cart = cartMapper.getCartByCanAndGoodIdAndAsso(canteenId,items.getGoodId(),associator.getAssociatorId(),pickingTime,0);//根据餐厅编号、商品编号、会员编号和取货时间查询购物车
                        if (count != null){
                            items.getGood().setMonthSale(count);
                        }
                        if (cart != null) {
                            items.getGood().setNum(cart.getQuantity());
                            if (items.getGood().getNum() >0){
                                items.getGood().setChecked(true);
                            }
                        }
                        if (Long.valueOf(pickingTime.replace("-","")) <  Long.valueOf(now)){
                            items.setSurplus(0);
                            items.getGood().setNum(0);
                        }
                    }
                    Collections.sort(planItemses, new Comparator<GoodPlanItems>() {
                        @Override
                        public int compare(GoodPlanItems o1, GoodPlanItems o2) {
                            int i = o2.getGood().getMonthSale() - o1.getGood().getMonthSale();
                            if (i == 0){
                                return Math.round(o1.getGood().getPrice()) - Math.round(o2.getGood().getPrice());//如果销量相同，继续按照价格进行排序，价格低的在前面
                            }
                            return i;//第一个参数：负整数      第二个参数：零或正整数。
                        }
                    });
                    lsResponse.setMessage("您好，这是今天要出售的商品");
                    lsResponse.setTotalCount(total);
                    lsResponse.setData(planItemses);
                    log.info("您好，这是今天要出售的商品");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setErrorCode("201");
                    if (keyword != null && !"".equals(keyword)){
                        lsResponse.setMessage("亲~不好意思，本餐厅未搜到您想要的菜品");
                        log.info("关键字是"+keyword+",本餐厅未搜到您想要的商品");
                    }else{
                        lsResponse.setMessage("亲~，本餐厅该类目暂无商品出售");
                        log.info("亲~不好意思，本餐厅该类目暂无商品出售");
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setErrorCode("202");
                lsResponse.setMessage("亲~，本餐厅今天暂时没有商品售卖计划，请查看其他餐厅");
                log.info("亲~，本餐厅今天暂时没有商品售卖计划，请查看其他餐厅");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("H5端，获取商品计划出错",e.toString());
        }
        return lsResponse;
    }
}
