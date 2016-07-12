package com.haiku.wateroffer.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;

/**
 * User Model接口,处理用户相关业务逻辑
 * Created by hyming on 2016/7/6.
 */
public interface IUserModel {
    // 登录模块相关回调
    interface LoginCallback extends IRequestCallback {
        // 登陆成功
        void onLoginSuccess();

        // 登陆失败
        void onLoginFail(String msg);
    }

    // 登陆
    void login(@NonNull LoginCallback callback);
}
