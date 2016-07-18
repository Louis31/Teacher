package com.haiku.wateroffer.module.order;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.model.IBaseModel;
import com.haiku.wateroffer.model.IOrderModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单列表模块Presenter实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderListPersenter implements OrderListContract.Presenter, IOrderModel.OrderListCallback {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_CANCEL = 2;
    private final int REQUEST_SEND = 3;
    private int requesType;

    @NonNull
    private final IOrderModel mOrderModel;
    @NonNull
    private final OrderListContract.View mView;

    public OrderListPersenter(@NonNull IOrderModel orderModel, @NonNull OrderListContract.View view) {
        this.mOrderModel = orderModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    // 获取列表数据
    @Override
    public void getListDatas(int uid, String status, String key, int pageno) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("status", status);
        params.put("key", key);
        params.put("pageno", pageno);

        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_LIST;
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.getOrderList(params, this);
        }
    }

    // 取消订单
    @Override
    public void cancelOrder() {
        Map<String, Object> params = new HashMap<>();
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_CANCEL;
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.cancelOrder(params, this);
        }
    }

    // 派单
    @Override
    public void sendOrder() {
        Map<String, Object> params = new HashMap<>();
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_SEND;
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.sendOrder(params, this);
        }
    }

    /**
     * Callback 接口方法
     */

    // 获取列表数据成功
    @Override
    public void getListDataSuccess(List<OrderItem> list) {
        mView.showListView(list);
    }

    // 取消订单成功
    @Override
    public void cancelOrderSuccess() {

    }

    // 派送订单成功
    @Override
    public void sendOrderSuccess() {

    }

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LIST) {
            mOrderModel.getOrderList(params, this);
        } else if (requesType == REQUEST_CANCEL) {
            mOrderModel.cancelOrder(params, this);
        } else if (requesType == REQUEST_SEND) {
            mOrderModel.sendOrder(params, this);
        }
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
