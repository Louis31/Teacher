package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.mvp.contract.ShopContract;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的店铺Presenter
 * Created by hyming on 2016/7/19.
 */
public class ShopPresenter implements ShopContract.Presenter, IUserModel.MyShopCallback {

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final ShopContract.View mView;

    public ShopPresenter(@NonNull IUserModel userModel, @NonNull ShopContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */

    @Override
    public void changeShopLogo(int uid, String data) {
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("data", data);
        mUserModel.changeShopLogo(params, this);
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mUserModel.changeShopLogo(params, this);
    }

    @Override
    public void uploadLogoSuccess(String logo) {
        mView.showLoadingDialog(false);
        mView.setLogo(logo);
    }

    // 成功回调
    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
