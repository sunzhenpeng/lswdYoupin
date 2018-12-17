package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.dao.lsypgen.GoodOrderMapperGen;
import com.lswd.youpin.model.AssociatorAccount;
import com.lswd.youpin.model.lsyp.GoodOrder;
import com.lswd.youpin.model.lsyp.GoodStatisticsOrder;
import com.lswd.youpin.model.lsyp.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GoodOrderMapper extends GoodOrderMapperGen {

    int insertTmpOrder(GoodOrder goodOrder);

    /*订单列表WEB端显示*/
    int getCount(@Param(value = "canIds") String[] canIds, @Param(value = "keyword") String keyword, @Param(value = "associatorId") String associatorId,
                 @Param(value = "canteenId") String canteenId, @Param(value = "dateflag") Integer dateflag,@Param(value = "payType") Integer payType,@Param(value = "orderTime") String orderTime, @Param(value = "table") String table);

    List<GoodOrder> getList(@Param(value = "canIds") String[] canIds, @Param(value = "keyword") String keyword, @Param(value = "associatorId") String associatorId,
                            @Param(value = "canteenId") String canteenId, @Param(value = "dateflag") Integer dateflag,@Param(value = "payType") Integer payType,
                            @Param(value = "orderTime") String orderTime, @Param(value = "table") String table,
                            @Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);


    /*订单列表 H5 页面显示*/
    int getCountH5(@Param(value = "associatorId") String associatorId, @Param(value = "canteenId") String canteenId,@Param(value = "status") Integer status, @Param(value = "table") String table);

    List<GoodOrder> getListH5(@Param(value = "associatorId") String associatorId, @Param(value = "canteenId") String canteenId, @Param(value = "status") Integer status,@Param(value = "table") String table,
                            @Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);


    /*订单所有表中的数据，使用视图，便于分页*/
    /*int getAllCount(@Param(value = "keyword")String keyword,@Param(value = "associatorId")String associatorId,
                    @Param(value = "canteenId")String canteenId,@Param(value = "dateflag")Integer dateflag,@Param(value = "view")String view);
    List<GoodOrder> getAllList(@Param(value = "keyword")String keyword, @Param(value = "associatorId")String associatorId,
                               @Param(value = "canteenId")String canteenId,@Param(value = "dateflag")Integer dateflag, @Param(value = "view")String view,
                               @Param(value = "offset")Integer offset, @Param(value = "pageSize")Integer pageSize);*/

    /*订单数据统计，用到的方法*/
   /* int getStatisticsCount(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                           @Param(value = "canteenId") String canteenId, @Param(value = "table")String table);
    List<GoodOrder> getStatisticsList(@Param(value = "startTime") Date startTime,@Param(value = "endTime") Date endTime,
                                      @Param(value = "canteenId") String canteenId, @Param(value = "table")String table);*/

    GoodOrder getGoodOrderByGoodOrderId(@Param(value = "goodOrderId") String goodOrderId, @Param(value = "table") String table);//根据订单编号操作商品订单


    int deleteByPrimaryId(@Param(value = "id") Integer id, @Param(value = "table") String table);//该方法主要用来删除临时表中的数据

    int deleteGoodOrderByGoodOrderId(@Param(value = "goodOrderId") String goodOrderId,@Param(value = "status") Integer status, @Param(value = "table") String table);//根据订单编号删除，修改状态

    int deleteGoodOrderByGoodOrderIdTrue(@Param(value = "goodOrderId") String goodOrderId, @Param(value = "table") String table);//根据订单编号删除，真的删除

    int insertGoodOrderTmp(@Param(value = "goodOrder") GoodOrder goodOrder, @Param(value = "table") String table);

    int insertGoodOrder(@Param(value = "goodOrder") GoodOrder goodOrder, @Param(value = "table") String table);

    int updateGoodOrderStatus(@Param(value = "goodOrder") GoodOrder goodOrder, @Param(value = "table") String table);

    Integer selectOrderTmpLast();

    List<GoodOrder> getSalesList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                 @Param(value = "canteenIds") List<String> canteenIds);


    List<GoodOrder> getSalesRefundList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                       @Param(value = "canteenIds") List<String> canteenIds);

    List<GoodStatisticsOrder> getGoodPaidList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                              @Param(value = "canteenIds") List<String> canteenIds, @Param(value = "offSet") Integer offSet,
                                              @Param(value = "pageSize") Integer pageSize);

    int getGoodPaidCount(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                         @Param(value = "canteenIds") List<String> canteenIds);

    /**
     * 获取某个商品的月销量
     */
    Integer getGoodMonthSales(@Param(value = "canteenId") String canteenId, @Param(value = "goodId") String goodId);

    List<GoodOrder> getDanPin(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                               @Param(value = "canteenIds") String[] canteenIds);

    int getSalesOrderCount(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime, @Param(value = "canteenId") String canteenId);

    int getTimeOrder(@Param(value = "dateTime") String dateTime, @Param(value = "canteenIds") String[] canteenIds);

    GoodOrder getGoodOrder(@Param(value = "orderNo") String orderNo,@Param(value = "table") String table);

    Integer updateStatus(@Param(value = "orderId") String orderId);

    List<GoodOrder> payOrder(@Param(value = "canteenIds") List<String> canteenIds,
                             @Param(value = "startDate") Date startDate,@Param(value = "endDate") Date endDate);

    List<GoodOrder> selectGoodPaidAllList(@Param(value = "date") Date date);

    Float getMoneyPaidAll(@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
                          @Param(value = "canteenIds") List<String> canteenIds);
}