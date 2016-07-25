package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.contract.DeliverOrderContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IOrderModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送员订单列表Presenter实现类
 * Created by hyming on 2016/7/25.
 */
public class DeliverOrderPersenter implements DeliverOrderContract.Presenter, IOrderModel.OrderListCallback {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_CANCEL = 2;// 取消配送
    private int requesType;

    @NonNull
    private final IOrderModel mOrderModel;
    @NonNull
    private final DeliverOrderContract.View mView;

    public DeliverOrderPersenter(@NonNull IOrderModel orderModel, @NonNull DeliverOrderContract.View view) {
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

    /**
     * Callback 接口方法
     */

    // 获取列表数据成功
    @Override
    public void getListDataSuccess(List<OrderItem> list) {
        mView.showListView(list);
    }

    @Override
    public void checkHasDeliver(boolean isHas, int order_id, int uid) {

    }

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LIST) {
            mOrderModel.getOrderList(params, this);
        } else if (requesType == REQUEST_CANCEL) {
            mOrderModel.cancelOrder(params, this);
        }
    }


    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        if (requesType == REQUEST_CANCEL) {
            // 取消配送成功
            //mView.refreshListView(TypeConstant.OrderOpera.CANCEL_DELIVER);
        }
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
