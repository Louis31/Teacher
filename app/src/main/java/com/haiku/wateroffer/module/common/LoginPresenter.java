package com.haiku.wateroffer.module.common;

import android.support.annotation.NonNull;

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

    // 登陆
    @Override
    public void login() {
        mView.showLoadingDialog(true);
        mUserModel.login(this);
    }

    // 获取验证码
    @Override
    public void getVerifyCode() {

    }

    // 登陆成功
    @Override
    public void onLoginSuccess() {
        mView.showLoadingDialog(false);
        mView.showMainActivity();
    }

    // 登陆失败
    @Override
    public void onLoginFail(String msg) {
        mView.showLoadingDialog(false);
        mView.showMessage(msg);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
    }
}
