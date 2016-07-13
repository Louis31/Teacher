package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.AccessToken;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
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
public class UserModelImpl extends BaseModelImpl implements IUserModel {
    private final String TAG = "UserModelImpl";

    // 登陆
    @Override
    public void login(String phone, String valicode, @NonNull final LoginCallback callback) {
        XUtils.Post(UrlConstant.User.loginUrl(), ParamUtils.User.getLoginParams(phone, valicode), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    UserManager.getInstance().setUser(GsonUtils.gsonToBean(result.getRetmsg().toString(), User.class));
                    callback.onLoginSuccess();
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }

    // 获取验证码
    @Override
    public void getVerifyCode(@NonNull String phone, @NonNull final LoginCallback callback) {
        XUtils.Get(UrlConstant.smsCodeUrl(), ParamUtils.getSmsCodeParams(phone), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    callback.getVerifyCodeSuccess(result.getRetmsg().getAsString());
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }
}
