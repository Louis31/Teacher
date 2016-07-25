package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.mvp.contract.ShopContract;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的店铺Presenter
 * Created by hyming on 2016/7/19.
 */
public class ShopPresenter implements ShopContract.Presenter, IShopModel.ShopCallback {

    //  @NonNull
    // private final IUserModel mUserModel;
    @NonNull
    private final IShopModel mShopModel;
    @NonNull
    private final ShopContract.View mView;

    public ShopPresenter(@NonNull IShopModel shopModel, @NonNull ShopContract.View view) {
        this.mShopModel = shopModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    // 获取店铺信息
    @Override
    public void getShopInfo(int uid) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        mShopModel.getShopInfo(params, this);
    }

    /* @Override
    public void changeShopLogo(int uid, String data) {
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("data", data);
        mUserModel.changeShopLogo(params, this);
    }*/

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
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
