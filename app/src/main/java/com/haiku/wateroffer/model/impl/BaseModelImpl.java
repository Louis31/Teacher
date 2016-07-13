package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.AccessToken;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IBaseModel;

/**
 * Created by hyming on 2016/7/6.
 */
public class BaseModelImpl implements IBaseModel {
    private final String TAG = "BaseModelImpl";

    // 获取token
    @Override
    public void getAccessToken(@NonNull final GetTokenCallback callback) {
        XUtils.Get(UrlConstant.tokenUrl(), null, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    UserManager.getInstance().setToken(GsonUtils.gsonToBean(result.getRetmsg().toString(), AccessToken.class));
                    callback.getTokenSuccess();
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }
}
