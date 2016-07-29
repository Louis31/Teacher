package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.contract.LoginContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆模块Presenter
 * Created by hyming on 2016/7/6.
 */
public class LoginPresenter implements LoginContract.Presenter, IRequestCallback {

    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_VERIFY_CODE = 2;
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
        requesType = REQUEST_LOGIN;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("valicode", valicode);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.login(params, this);
        }
    }

    // 获取验证码
    @Override
    public void getVerifyCode(String phone) {
        requesType = REQUEST_VERIFY_CODE;
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.getVerifyCode(params, this);
        }
    }

    /**
     * Callback 接口方法
     */

    // 获取token成功
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_LOGIN) {
            mUserModel.login(params, this);
        } else if (requesType == REQUEST_VERIFY_CODE) {
            mUserModel.getVerifyCode(params, this);
        }
    }

    // 登陆成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        if (requesType == REQUEST_VERIFY_CODE) {
            mView.resetVerifyCodeView();
        } else {
            mView.showSuccessView();
        }
    }

    // 错误返回
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        if (requesType == REQUEST_VERIFY_CODE) {
            mView.resetVerifyCodeView();
        }
        mView.showMessage(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
