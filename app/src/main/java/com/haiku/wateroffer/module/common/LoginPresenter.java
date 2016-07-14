package com.haiku.wateroffer.module.common;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.model.IBaseModel;
import com.haiku.wateroffer.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆模块Presenter
 * Created by hyming on 2016/7/6.
 */
public class LoginPresenter implements LoginContract.Presenter, IUserModel.LoginCallback {

    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_GET_VERIFY_CODE = 2;
    private int requesType;

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
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("valicode", valicode);
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_LOGIN;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.login(params, this);
        }
    }

    // 获取验证码
    @Override
    public void getVerifyCode(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_GET_VERIFY_CODE;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.getVerifyCode(params, this);
        }
    }

    /**
     * Callback 接口方法
     */

    // 登陆成功
    @Override
    public void onLoginSuccess() {
        mView.showLoadingDialog(false);
        // 判断是否为新用户
        boolean isNewUser = false;
        if (isNewUser) {

        } else {
            mView.showMainActivity();
        }
    }

    // 获取验证码成功
    @Override
    public void getVerifyCodeSuccess(String verifyCode) {
        mView.setVerifyCode(verifyCode);
    }

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LOGIN) {
            mUserModel.login(params, this);
        } else if (requesType == REQUEST_GET_VERIFY_CODE) {
            mUserModel.getVerifyCode(params, this);
        }
    }

    // 错误返回
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
