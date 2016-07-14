package com.haiku.wateroffer.model;

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
    // 订单列表模块相关回调
    interface OrderListCallback extends IRequestCallback {
        // 获取列表数据成功
        void getListDataSuccess(List<OrderItem> list);
    }

    // 获取订单列表
    void getOrderList(Map<String, Object> params, @NonNull OrderListCallback callback);
}
