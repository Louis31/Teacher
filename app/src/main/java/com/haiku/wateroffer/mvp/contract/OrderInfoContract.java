package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.bean.OrderStatus;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 订单详情模块的Contract类
 * Created by hyming on 2016/7/17.
 */
public interface OrderInfoContract {
    interface View extends BaseView<Presenter> {
        // 设置信息订单信息
        void setOrderInfo(OrderItem bean);

        void setOrderStatus(List<OrderStatus> list);

        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 跳转配送员列表
        void showDeliverView();

        // 更新当前界面
        void refreshView(int type);

        // 显示消息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取订单逻辑
        void getOrderStatus(int uid, int order_id);

        // 获取订单详情
        void getOrderInfo(int uid, int order_id);

        // 取消订单
        void cancelOrder(int id, int uid);

        // 派单
        void sendOrder(int id, int uid);
    }
}
