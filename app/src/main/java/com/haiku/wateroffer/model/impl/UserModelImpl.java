package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Result;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IUserModel;

/**
 * User Model实现类
 * Created by hyming on 2016/7/6.
 */
public class UserModelImpl implements IUserModel {
    // 登陆
    @Override
    public void login(@NonNull final LoginCallback callback) {
        boolean isSuccess = true;

        /*XUtils.Post(UrlConstant.User.getLoginUrl(), ParamUtils.User.getLoginParams(), new XUtilsCallback(callback) {
            @Override
            public void onSuccess(Result result) {
                super.onSuccess(result);
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    callback.onLoginSuccess();
                } else {
                    callback.onLoginFail(result.getRetmsg());
                }
            }
        });*/

        if (isSuccess) {
            callback.onLoginSuccess();
        } else {
            callback.onLoginFail("");
        }
    }
}
