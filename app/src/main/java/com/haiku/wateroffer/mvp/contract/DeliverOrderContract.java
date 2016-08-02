package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 配送员订单列表的Contract
 * Created by hyming on 2016/7/25.
 */
public interface DeliverOrderContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示列表界面
        void showListView(List<OrderItem> list);

        // 更新列表界面
        void refreshListView();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getListDatas(int uid, int mid);

        // 取消派单
        void cancelOrder(int id, int uid,int did);
    }
}
