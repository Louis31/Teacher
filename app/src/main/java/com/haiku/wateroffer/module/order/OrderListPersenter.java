package com.haiku.wateroffer.module.order;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.model.IOrderModel;

import java.util.List;

/**
 * 订单列表模块Presenter实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderListPersenter implements OrderListContract.Presenter, IOrderModel.OrderListCallback {

    @NonNull
    private final IOrderModel mOrderModel;
    @NonNull
    private final OrderListContract.View mView;

    public OrderListPersenter(@NonNull IOrderModel orderModel, @NonNull OrderListContract.View view) {
        this.mOrderModel = orderModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    // 获取列表数据
    @Override
    public void getListDatas() {
        mOrderModel.getOrderList(this);
    }

    // 获取列表数据成功
    @Override
    public void getListDataSuccess(List<OrderItem> list) {
        mView.showListView(list);
    }

    // 获取列表数据失败
    @Override
    public void getListDataFail(String msg) {
        mView.showMessage(msg);
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
