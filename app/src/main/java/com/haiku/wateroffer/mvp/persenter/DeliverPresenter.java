package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.contract.DeliverContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送列表的Presenter
 * Created by hyming on 2016/7/20.
 */
public class DeliverPresenter implements DeliverContract.Presenter, IUserModel.DeliverCallback {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_CHANGE = 2;
    private final int REQUEST_ADD = 3;
    private int requesType;

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final DeliverContract.View mView;

    public DeliverPresenter(@NonNull IUserModel userModel, @NonNull DeliverContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter
     */
    @Override
    public void getDeliverList(int uid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);

        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_LIST;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.getDeliverList(params, this);
        }
    }

    @Override
    public void changeDeliverStatus(int diliveryman_id, String phone, int status) {
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("diliveryman_id", diliveryman_id);
        params.put("phone", phone);
        params.put("status", status);

        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_CHANGE;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.changeDeliverStatus(params, this);
        }
    }

    @Override
    public void addDeliver(int uid, String phone) {
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("phone", phone);
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_ADD;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.addDeliver(params, this);
        }
    }

    /**
     * Callback
     */
    // 获取配送列表成功
    @Override
    public void getDeliverListSuccess(List<Deliver> list) {
        mView.showListView(list);
    }

    // 修改状态成功
    @Override
    public void changeStatusSuccess() {
        mView.showLoadingDialog(false);
        mView.updateListView(null);
    }

    @Override
    public void addDeliverSuccess(Deliver deliver) {
        mView.showLoadingDialog(false);
        mView.updateListView(deliver);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LIST) {
            mUserModel.getDeliverList(params, this);
        } else if (requesType == REQUEST_CHANGE) {
            mUserModel.changeDeliverStatus(params, this);
        } else {
            mUserModel.addDeliver(params, this);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
