package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.UserManager;
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
    private final int REQUEST_STATUS_SET = 4;
    private final int REQUEST_DEPOSIT = 5;
    private int requesType;
    private Map<String, Object> mTempParams;// 存储当前请求的参数

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
        if (!isTokenFail(params)) {
            mShopModel.getShopInfo(params, this);
        }
    }

    @Override
    public void changeShopLogo(int uid, String data, String dlgStr) {
        requesType = REQUEST_LOGO;
        mView.showLoadingDialog(true, dlgStr);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("data", data);
        if (!isTokenFail(params)) {
            mShopModel.changeShopLogo(params, this);
        }
    }

    @Override
    public void setShopOpenStatus(int uid, String status, String dlgStr) {
        mView.showLoadingDialog(true, dlgStr);
        requesType = REQUEST_STATUS_SET;
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        params.put("status", status);
        if (!isTokenFail(params)) {
            mShopModel.setShopOpenStatus(params, this);
        }
    }

    @Override
    public void getShopMarginStatus(int uid) {
        requesType = REQUEST_DEPOSIT;
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        if (!isTokenFail(params)) {
            mShopModel.getShopMarginStatus(params, this);
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
        } else if (requesType == REQUEST_STATUS_SET) {
            mShopModel.setShopOpenStatus(params, this);
        } else if (requesType == REQUEST_DEPOSIT) {
            mShopModel.getShopMarginStatus(params, this);
        }
    }

    @Override
    public void getShopInfoSuccess(ShopInfo bean) {
        mView.setShopInfo(bean);
    }

    @Override
    public void uploadLogoSuccess(String logo) {
        mView.showLoadingDialog(false, "");
        mView.setLogo(logo);
    }

    @Override
    public void setOpenStatusSuccess(String status) {
        mView.showLoadingDialog(false, "");
        mView.setShopStatus(status);
    }

    // 成功回调
    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false, "");
        mView.showMessage(errorMsg);
    }

    @Override
    public void onTokenFail() {
        UserManager.cleanToken();
        ((IBaseModel) mShopModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mShopModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }
}
