package com.haiku.wateroffer.module.order;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.module.base.BasePresenter;
import com.haiku.wateroffer.module.base.BaseView;

/**
 * 订单详情模块的Contract类
 * Created by hyming on 2016/7/17.
 */
public class OrderInfoContract {
    interface View extends BaseView<Presenter> {
        // 设置信息订单信息
        void setOrderInfo(OrderItem bean);

        // 显示消息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取订单详情
        void getOrderInfo(int uid, int order_id);

        // 取消订单
        void cancelOrder();

        // 派单
        void sendOrder();
    }
}
