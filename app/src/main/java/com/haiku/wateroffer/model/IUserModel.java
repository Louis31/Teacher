package com.haiku.wateroffer.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;

/**
 * User Model接口,处理用户相关业务逻辑
 * Created by hyming on 2016/7/6.
 */
public interface IUserModel {
    // 登录模块相关回调
    interface LoginCallback extends IBaseModel.GetTokenCallback {
        // 登陆成功
        void onLoginSuccess();

        // 获取验证码成功
        void getVerifyCodeSuccess(String verifyCode);
    }

    // 登陆
    void login(String phone, String valicode, @NonNull LoginCallback callback);

    // 获取验证码
    void getVerifyCode(@NonNull String phone, @NonNull LoginCallback callback);
}
