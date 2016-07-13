package com.haiku.wateroffer.module.common;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.model.IBaseModel;
import com.haiku.wateroffer.model.IUserModel;

/**
 * 登陆模块Presenter
 * Created by hyming on 2016/7/6.
 */
public class LoginPresenter implements LoginContract.Presenter, IUserModel.LoginCallback {

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final LoginContract.View mView;

    public LoginPresenter(@NonNull IUserModel userModel, @NonNull LoginContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    // 登陆
    @Override
    public void login(String phone, String valicode) {
        mView.showLoadingDialog(true);
        mUserModel.login(phone, valicode, this);
    }

    // 获取验证码
    @Override
    public void getVerifyCode(String phone) {
        mUserModel.getVerifyCode(phone, this);
    }

    // 获取token
    @Override
    public void getAccessToken() {
        ((IBaseModel) mUserModel).getAccessToken(this);
    }

    /**
     * Callback 接口方法
     */

    // 登陆成功
    @Override
    public void onLoginSuccess() {
        mView.showLoadingDialog(false);
        mView.showMainActivity();
    }

    // 获取验证码成功
    @Override
    public void getVerifyCodeSuccess(String verifyCode) {
        mView.setVerifyCode(verifyCode);
    }

    @Override
    public void getTokenSuccess() {

    }

    // 错误返回
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
