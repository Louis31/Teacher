package com.haiku.wateroffer.common.listener;

/**
 * 订单列表相关事件
 * Created by hyming on 2016/7/15.
 */
public interface OrderListListener {
    // 查看订单详情
    void onOrderDetailClick(int pos);

    // 取消配送
    void onOrderCancelClick(int pos);

    // 派单
    void onOrderSendClick(int pos);
}
