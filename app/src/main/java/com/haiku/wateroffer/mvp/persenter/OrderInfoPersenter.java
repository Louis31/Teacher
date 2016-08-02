package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.contract.OrderInfoContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IOrderModel;

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
    private final int REQUEST_HAS_DELIVER = 4;
    private int requesType;
    private Map<String, Object> mTempParams;// 存储当前请求的参数
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
        requesType = REQUEST_INFO;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("order_id", order_id);
        if (!isTokenFail(params)) {
            mOrderModel.getOrderInfo(params, this);
        }
    }

    // 取消订单
    @Override
    public void cancelOrder(int id, int uid) {
        requesType = REQUEST_CANCEL;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", uid);
        if (!isTokenFail(params)) {
            mOrderModel.cancelOrder(params, this);
        }
    }

    // 派单
    @Override
    public void sendOrder(int id, int uid) {
        isHasDeliver(id, uid);
    }

    // 判断有没有已添加的配送员
    private void isHasDeliver(int order_id, int uid) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_HAS_DELIVER;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("order_id", order_id);

        if (!isTokenFail(params)) {
            mOrderModel.isHasDeliver(params, this);
        }
    }

    // 派送订单
    private void sendOrderOpera(int id, int uid) {
        requesType = REQUEST_SEND;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", uid);
        if (!isTokenFail(params)) {
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
        } else if (requesType == REQUEST_HAS_DELIVER) {
            mOrderModel.isHasDeliver(params, this);
        }
    }


    @Override
    public void checkHasDeliver(boolean isHas, int order_id, int uid) {
        if (isHas) {
            sendOrderOpera(order_id, uid);// 派送订单
        } else {
            mView.showLoadingDialog(false);
            mView.showDeliverView(); // 跳转配送员列表
        }
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        if (requesType == REQUEST_CANCEL) {
            // 取消配送成功
            mView.refreshView(TypeConstant.OrderOpera.CANCEL_DELIVER);
        } else if (requesType == REQUEST_SEND) {
            // 派单成功
            mView.refreshView(TypeConstant.OrderOpera.SEND_ORDER);
        }
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }

    @Override
    public void onTokenFail() {
        UserManager.cleanToken();
        ((IBaseModel) mOrderModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }
}
