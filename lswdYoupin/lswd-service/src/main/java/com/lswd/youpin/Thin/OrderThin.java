package com.lswd.youpin.Thin;

        import com.lswd.youpin.model.Associator;
        import com.lswd.youpin.model.lsyp.Orders;
        import com.lswd.youpin.response.LsResponse;

        import java.util.List;
        import java.util.Map;

/**
 * Created by liuhao on 2017/8/30.
 */
public interface OrderThin {
    //    LsResponse addGenerateOrder(List<Map<String, Object>> carOrders, Associator associator);
    LsResponse addGenerateOrder(Orders orders, Associator associator);

    LsResponse getOrdersCount(Associator associator);
}
