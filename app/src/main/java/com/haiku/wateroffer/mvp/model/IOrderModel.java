package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.List;
import java.util.Map;

/**
 * 订单Model接口,处理订单相关业务逻辑
 * Created by hyming on 2016/7/11.
 */
public interface IOrderModel {
    interface IOrderCallback extends IRequestCallback {
        void checkHasDeliver(boolean isHas, int order_id, int uid);
    }

    // 订单列表模块相关回调
    interface OrderListCallback extends IOrderCallback {
        // 获取列表数据成功
        void getListDataSuccess(List<OrderItem> list);
    }

    //  订单详情模块相关回调
    interface OrderInfoCallback extends IOrderCallback {
        // 获取详情数据成功
        void getOrderInfoSuccess(OrderItem bean);
    }

    // 获取订单列表
    void getOrderList(Map<String, Object> params, @NonNull OrderListCallback callback);

    // 获取订单详情
    void getOrderInfo(Map<String, Object> params, @NonNull OrderInfoCallback callback);

    // 取消订单
    void cancelOrder(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 派送订单
    void sendOrder(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 是否有配送员
    void isHasDeliver(Map<String, Object> params, @NonNull IOrderCallback callback);

    // 获取配送员订单列表
    void getDeliverOrder(Map<String, Object> params, @NonNull OrderListCallback callback);
}
