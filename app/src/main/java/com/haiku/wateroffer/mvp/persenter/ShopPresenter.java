package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.mvp.contract.ShopContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的店铺Presenter
 * Created by hyming on 2016/7/19.
 */
public class ShopPresenter implements ShopContract.Presenter, IShopModel.ShopCallback {
    private final int REQUEST_INFO = 1;
    private final int REQUEST_LOGO = 2;
    private final int REQUEST_RANGE = 3;
    private final int REQUEST_STATUS = 4;
    private int requesType;
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
        requesType = REQUEST_INFO;
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.getShopInfo(params, this);
        }
    }

    @Override
    public void changeShopLogo(int uid, String data) {
        requesType = REQUEST_LOGO;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("data", data);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.changeShopLogo(params, this);
        }
    }

    @Override
    public void changeShopRange(int uid, String range) {
        requesType = REQUEST_RANGE;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        params.put("qq", range);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.changeShopRange(params, this);
        }
    }

    @Override
    public void getShopOpenStatus(int uid) {
        requesType = REQUEST_STATUS;
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.getShopOpenStatus(params, this);
        }
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_INFO) {
            mShopModel.getShopInfo(params, this);
        } else if (requesType == REQUEST_LOGO) {
            mShopModel.changeShopLogo(params, this);
        } else if (requesType == REQUEST_RANGE) {
            mShopModel.changeShopRange(params, this);
        } else if (requesType == REQUEST_STATUS) {
            mShopModel.getShopOpenStatus(params, this);
        }
    }

    @Override
    public void getShopInfoSuccess(ShopInfo bean) {
        mView.setShopInfo(bean);
    }

    @Override
    public void uploadLogoSuccess(String logo) {
        mView.showLoadingDialog(false);
        mView.setLogo(logo);
    }

    @Override
    public void getOpenStatusSuccess(String status) {
        mView.setShopStatus(status);
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showMessage("修改成功");
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
