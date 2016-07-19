package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.mvp.contract.ShopNameContract;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加/修改店铺名称Presenter
 * Created by hyming on 2016/7/13.
 */
public class ShopNamePresenter implements ShopNameContract.Presenter, IRequestCallback {

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final ShopNameContract.View mView;

    public ShopNamePresenter(@NonNull IUserModel userModel, @NonNull ShopNameContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    @Override
    public void addShopName(int uid, String shopName) {
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("shopname", shopName);
        mUserModel.addShopName(params, this);
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mUserModel.addShopName(params, this);
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showSuccessView();
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
