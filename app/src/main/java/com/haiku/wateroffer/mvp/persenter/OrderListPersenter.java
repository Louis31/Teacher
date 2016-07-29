package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.contract.OrderListContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IOrderModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单列表模块Presenter实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderListPersenter implements OrderListContract.Presenter, IOrderModel.OrderListCallback {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_CANCEL = 2;// 取消配送
    private final int REQUEST_SEND = 3;// 派送
    private final int REQUEST_HAS_DELIVER = 4;// 是否有配送员
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
        requesType = REQUEST_LIST;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("status", status);
        params.put("key", key);
        params.put("pageno", pageno);

        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.getOrderList(params, this);
        }
    }

    // 取消订单
    @Override
    public void cancelOrder(int id, int uid) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_CANCEL;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", uid);
        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
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

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mOrderModel).getAccessToken(params, this);
        } else {
            mOrderModel.isHasDeliver(params, this);
        }
    }

    // 派送订单
    private void sendOrderOpera(int id, int uid) {
        requesType = REQUEST_SEND;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", uid);
        if (UserManager.isTokenEmpty()) {
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

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LIST) {
            mOrderModel.getOrderList(params, this);
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
            mView.refreshListView(TypeConstant.OrderOpera.CANCEL_DELIVER);
        } else if (requesType == REQUEST_SEND) {
            // 派单成功
            mView.refreshListView(TypeConstant.OrderOpera.SEND_ORDER);
        }
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
