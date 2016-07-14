package com.haiku.wateroffer.module.shop;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.model.IShopModel;

import java.util.Map;

/**
 * 添加/修改店铺名称Presenter
 * Created by hyming on 2016/7/13.
 */
public class ShopNamePresenter implements ShopNameContract.Presenter, IShopModel.ShopNameCallback {

    @NonNull
    private final IShopModel mShopModel;
    @NonNull
    private final ShopNameContract.View mView;

    public ShopNamePresenter(@NonNull IShopModel shopModel, @NonNull ShopNameContract.View view) {
        this.mShopModel = shopModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    @Override
    public void addShopName(int uid, String shopName) {
        mView.showLoadingDialog(true);
        mShopModel.addShopName(uid, shopName, this);
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void onAddShopNameSuccess() {
        mView.showLoadingDialog(false);
        mView.showShopAddressActivity();
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
