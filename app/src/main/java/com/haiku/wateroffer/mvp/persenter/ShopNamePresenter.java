package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.mvp.contract.ShopNameContract;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加/修改店铺名称Presenter
 * Created by hyming on 2016/7/13.
 */
public class ShopNamePresenter implements ShopNameContract.Presenter, IRequestCallback {
    private final int REQUEST_ADD = 1;
    private final int REQUEST_CHANGE = 2;
    private int requesType;

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
        requesType = REQUEST_ADD;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("shopname", shopName);
        // mShopModel.addShopName(params, this);
    }

    @Override
    public void changeShopName(int uid, String shopName) {
        requesType = REQUEST_CHANGE;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        params.put("shopname", shopName);
        mShopModel.changeShopName(params, this);
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_ADD) {
            // mShopModel.changeShopName(params, this);
        } else if (requesType == REQUEST_CHANGE) {
            mShopModel.changeShopName(params, this);
        }
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
