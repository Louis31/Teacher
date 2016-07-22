package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IOrderModel;
import com.haiku.wateroffer.mvp.contract.OrderInfoContract;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单详情模块Presenter实现类
 * Created by hyming on 2016/7/17.
 */
public class OrderInfoPersenter implements OrderInfoContract.Presenter, IOrderModel.OrderInfoCallback {
    private final int REQUEST_INFO = 1;
    private final int REQUEST_CANCEL = 2;
    private final int REQUEST_SEND = 3;
    private int requesType;

    @NonNull
    private final IOrderModel mOrderModel;
    @NonNull
    private final OrderInfoContract.View mView;

    public OrderInfoPersenter(@NonNull IOrderModel orderModel, @NonNull OrderInfoContract.View view) {
        this.mOrderModel = orderModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    // 获取订单详情
    @Override
    public void getOrderInfo(int uid, int order_id) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("order_id", order_id);
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_INFO;
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.getOrderInfo(params, this);
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
    @Override
    public void getOrderInfoSuccess(OrderItem bean) {
        mView.setOrderInfo(bean);
    }

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_INFO) {
            mOrderModel.getOrderInfo(params, this);
        } else if (requesType == REQUEST_CANCEL) {
            mOrderModel.cancelOrder(params, this);
        } else if (requesType == REQUEST_SEND) {
            mOrderModel.sendOrder(params, this);
        }
    }

    // 成功回调
    @Override
    public void onSuccess() {

    }


    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
