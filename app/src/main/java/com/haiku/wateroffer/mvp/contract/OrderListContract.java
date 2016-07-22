package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 订单列表模块的Contract类
 * Created by hyming on 2016/7/11.
 */
public interface OrderListContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示列表界面
        void showListView(List<OrderItem> list);

        // 取消订单成功
        void refreshListView(int type);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getListDatas(int uid, String status, String key, int pageno);

        // 取消订单
        void cancelOrder(int id, int uid) ;

        // 派单
        void sendOrder(int id, int uid);
    }
}
