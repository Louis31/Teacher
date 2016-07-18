package com.haiku.wateroffer.module.shop;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.model.IUserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的账单Presenter实现类
 * Created by hyming on 2016/7/18.
 */
public class MyBillPresenter implements MyBillContract.Presenter, IUserModel.MyBillCallback {

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final MyBillContract.View mView;

    public MyBillPresenter(@NonNull IUserModel userModel, @NonNull MyBillContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter
     */
    @Override
    public void getListDatas(int uid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        mUserModel.getBillList(params, this);
    }

    // 查询账单
    @Override
    public void searchBill(int uid, String from, String to, String diliverymanName) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("from", from);
        params.put("to", to);
        params.put("diliverymanName", diliverymanName);
    }

    /**
     * Callback
     */
    // 获取账单成功
    @Override
    public void getBillSuccess(List<Bill> list) {
        mView.showListView(list);
    }

    // 查询账单成功
    @Override
    public void searchBillSuccess(Bill bean) {
        mView.showSearchResult(bean);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mUserModel.getBillList(params, this);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
