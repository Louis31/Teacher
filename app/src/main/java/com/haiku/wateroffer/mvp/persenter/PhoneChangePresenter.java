package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.contract.PhoneChangeContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改联系电话Presenter
 * Created by hyming on 2016/7/19
 */
public class PhoneChangePresenter implements PhoneChangeContract.Presenter, IShopModel.IPhoneCallback {

    private final int REQUEST_CHANGE_PHONE = 1;
    private final int REQUEST_VERIFY_CODE = 2;
    private int requesType;

    @NonNull
    private final IShopModel mShopModel;
    @NonNull
    private final PhoneChangeContract.View mView;

    public PhoneChangePresenter(@NonNull IShopModel shopModel, @NonNull PhoneChangeContract.View view) {
        this.mShopModel = shopModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    // 修改联系电话
    @Override
    public void changePhone(int uid, String old_phone, String phone, String code) {
        requesType = REQUEST_CHANGE_PHONE;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("id", uid);
        params.put("old_phone", old_phone);
        params.put("phone", phone);
        params.put("code", code);
        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.changeShopPhone(params, this);
        }
    }

    // 获取验证码
    @Override
    public void getVerifyCode(String phone) {
        requesType = REQUEST_VERIFY_CODE;
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.getVerifyCode(params, this);
        }
    }

    /**
     * Callback 接口方法
     */
    // 获取验证码成功
    @Override
    public void getVerifyCodeSuccess(String verifyCode) {
        mView.setVerifyCode(verifyCode);
    }

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_CHANGE_PHONE) {
            mShopModel.changeShopPhone(params, this);
        } else if (requesType == REQUEST_VERIFY_CODE) {
            mShopModel.getVerifyCode(params, this);
        }
    }

    // 登陆成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showShopView();
    }

    // 错误返回
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
