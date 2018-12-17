package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.model.lsyp.RecipeOrder;
import com.lswd.youpin.model.lsyp.RecipeStatisricsOrder;
import com.lswd.youpin.model.lsyp.Statistics;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/6/22.
 */
public interface RecipeOrderMapper {
    Integer selectOrderTmpCount(@Param(value = "keyword") String keyword, @Param(value = "canteenIds") List<String> canteenIds,
                                @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    List<RecipeOrder> selectOrderTmpAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
                                        @Param(value = "offSet") Integer offSet, @Param(value = "canteenIds") List<String> canteenIds,
                                        @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    Integer selectPaidCount(@Param(value = "keyword") String keyword, @Param(value = "canteenIds") List<String> canteenIds,
                            @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    List<RecipeOrder> selectPaidAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
                                    @Param(value = "offSet") Integer offSet, @Param(value = "canteenIds") List<String> canteenIds,
                                    @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    Integer selectOverCount(@Param(value = "keyword") String keyword, @Param(value = "canteenIds") List<String> canteenIds,
                            @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    List<RecipeOrder> selectOverAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
                                    @Param(value = "offSet") Integer offSet, @Param(value = "canteenIds") List<String> canteenIds,
                                    @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    Integer selectRefundCount(@Param(value = "keyword") String keyword, @Param(value = "canteenIds") List<String> canteenIds,
                              @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    List<RecipeOrder> selectRefundAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
                                      @Param(value = "offSet") Integer offSet, @Param(value = "canteenIds")  List<String> canteenIds,
                                      @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);


//    Integer selectCancelCount(@Param(value = "keyword") String keyword, @Param(value = "canteenId") String canteenId, @Param(value = "dataTime") Integer dataTime);
//
//
//    List<RecipeOrder> selectCancelAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
//                                      @Param(value = "offSet") Integer offSet, @Param(value = "canteenId") String canteenId, @Param(value = "dataTime") Integer dataTime);


    Integer selectOrdersCount(@Param(value = "keyword") String keyword, @Param(value = "canteenIds") List<String> canteenIds,
                              @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    List<RecipeOrder> selectOrdersAll(@Param(value = "keyword") String keyword, @Param(value = "pageSize") Integer pageSize,
                                      @Param(value = "offSet") Integer offSet, @Param(value = "canteenIds") List<String> canteenIds,
                                      @Param(value = "dataTime") Integer dataTime,@Param(value = "payType") Integer payType);

    Integer insertOrder(RecipeOrder recipeOrder);

    Integer selectOrderTmpLast();

    List<RecipeOrder> getSalesList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                   @Param(value = "canteenIds") List<String> canteenIds);

    List<RecipeOrder> getSalesRefundList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                         @Param(value = "canteenIds") List<String> canteenIds);

    List<RecipeStatisricsOrder> getSalesRecipeList(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                                                   @Param(value = "offSet") int offSet, @Param(value = "pageSize") Integer pageSize,
                                                   @Param(value = "canteenIds") List<String> canteenIds);

    Integer selectSalesCount(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                             @Param(value = "canteenIds") List<String> canteenIds);


/*zhenguanqi  write down */
   /* RecipeOrder getRecipeOrderByOrderID(@Param("orderID")String orderID,@Param("table")String table);//根据订单编号，表名查找
    int deleteByPrimaryOrderID(@Param("id")Integer id,@Param("table")String table);
    int insertRecipeOrder(@Param("recipeOrder") RecipeOrder recipeOrder,@Param("table")String table);
    int updateRecipeOrder(@Param("orderID")String orderID,@Param("table")String table);*/

    /**
     * 获取某个菜品的月销量
     */
    Integer getRecipeMonthSales(@Param(value = "canteenId") String canteenId, @Param(value = "recipeId") String recipeId);

    List<Statistics> getDanPin(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                               @Param(value = "canteenIds") String[] canteenIds);

    Integer getSalesOrderCount(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime,
                               @Param(value = "canteenId") String canteenId);

    Integer getTimeOrder(@Param(value = "dateTime") String dateTime, @Param(value = "canteenIds") String[] canteenIds);

    RecipeOrder selectRefundOrder( @Param(value = "orderId") String orderId);

    RecipeOrder selectOverOrder( @Param(value = "orderId") String orderId);

    RecipeOrder selectPaidOrder( @Param(value = "orderId") String orderId);

    RecipeOrder selectTempOrder(@Param(value = "orderId") String orderId);

    List<RecipeOrder> selectRefundOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                            @Param(value = "pageSize") Integer pageSize);

    List<RecipeOrder> selectOverOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                          @Param(value = "pageSize") Integer pageSize);

    List<RecipeOrder> selectPaidOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                          @Param(value = "pageSize") Integer pageSize);

    List<RecipeOrder> selectTempOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                          @Param(value = "pageSize") Integer pageSize);

    Integer selectRefundOrderListCount(Associator associator);

    Integer selectOverOrderListCount(Associator associator);

    Integer selectPaidOrderListCount(Associator associator);

    Integer selectTempOrderListCount(Associator associator);

    Integer selectOrderListCount(Associator associator);

    List<RecipeOrder> selectOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                      @Param(value = "pageSize") Integer pageSize);

    RecipeOrder getRecipeOrder(@Param("orderNo") String orderNo,@Param("table") String table);

    Integer deleteByOrderNo(@Param("orderNo") String orderNo,@Param("table") String table);

    Integer insertOrder(RecipeOrder recipeOrder,@Param("table") String table);

    Integer insertOrderPaid(RecipeOrder recipeOrder);

    Integer deleteOrderByOrderId(@Param(value = "orderId") String orderId);

    Integer insertCancelOrder(@Param(value = "recipeOrder") RecipeOrder recipeOrder, @Param(value = "associator") Associator associator);

    List<RecipeOrder> getOrderListWxRefund(@Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize,
                                           @Param(value = "canteenIds") List<String> canteenIds,
                                           @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    List<RecipeOrder> getOrderListWxOver(@Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize,
                                         @Param(value = "canteenIds") List<String> canteenIds,
                                         @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    List<RecipeOrder> getOrderListWxPaid(@Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize,
                                         @Param(value = "canteenIds") List<String> canteenIds,
                                         @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    List<RecipeOrder> getOrderListWxTmp(@Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize,
                                        @Param(value = "canteenIds") List<String> canteenIds,
                                        @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    List<RecipeOrder> getOrderListWxAll(@Param(value = "offSet") Integer offSet, @Param(value = "pageSize") Integer pageSize,
                                        @Param(value = "canteenIds") List<String> canteenIds,
                                        @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);

    Integer getOrderListWxRefundCount(@Param(value = "canteenIds") List<String> canteenIds, @Param(value = "startDate") Date startDate,
                                  @Param(value = "endDate") Date endDate);

    Integer getOrderListWxOverCount(@Param(value = "canteenIds") List<String> canteenIds, @Param(value = "startDate") Date startDate,
                                @Param(value = "endDate") Date endDate);

    Integer getOrderListWxPaidCount(@Param(value = "canteenIds") List<String> canteenIds, @Param(value = "startDate") Date startDate,
                                @Param(value = "endDate") Date endDate);

    Integer getOrderListWxTmpCount(@Param(value = "canteenIds") List<String> canteenIds, @Param(value = "startDate") Date startDate,
                               @Param(value = "endDate") Date endDate);

    Integer getOrderListWxAllCount(@Param(value = "canteenIds") List<String> canteenIds, @Param(value = "startDate") Date startDate,
                               @Param(value = "endDate") Date endDate);

    /*start  zhenguanqi   write*/
    RecipeOrder getOrderInPaidByOrderId(@Param(value = "recipeOrderId")String recipeOrderId);

    RecipeOrder getOrderInOverByOrderId(@Param(value = "recipeOrderId")String recipeOrderId);

    Integer deleteOrderInPaidByOrderIdTrue(@Param(value = "recipeOrderId")String recipeOrderId);

    Integer insertOrderInOver(RecipeOrder recipeOrder);

    Integer updateStatus(@Param(value = "orderId") String orderId);

    Integer selectEvaluateOrderListCount(Associator associator);

    List<RecipeOrder> selectEvaluateOrderList(@Param(value = "associator") Associator associator,
                                               @Param(value = "offSet") int offSet,@Param(value = "pageSize") Integer pageSize);

    Integer deleteStatus(@Param(value = "recipeOrder") RecipeOrder recipeOrder,@Param(value = "table") String table);

    RecipeOrder selectOrderByOrderId(@Param(value = "orderId") String orderId);

    List<RecipeOrder> payOrder(@Param(value = "canteenIds") List<String> canteenIds,
                               @Param(value = "startDate") Date startDate,@Param(value = "endDate") Date endDate);
   /*end  zhenguanqi   write*/
   Integer insertOrderRefund(RecipeOrder recipeOrder);

    Integer selectCanclOrderListCount(Associator associator);

    List<RecipeOrder> selectCancelOrderList(@Param(value = "associator") Associator associator, @Param(value = "offSet") int offSet,
                                            @Param(value = "pageSize") Integer pageSize);

    Float getMoneyPaidAll(@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
                          @Param(value = "canteenIds") List<String> canteenIds);

}
