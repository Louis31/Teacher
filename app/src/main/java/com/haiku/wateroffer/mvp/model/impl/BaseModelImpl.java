package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.AccessToken;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.MyXUtilsCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IBaseModel;

import java.util.Map;

/**
 * Created by hyming on 2016/7/6.
 */
public class BaseModelImpl implements IBaseModel {
    // 获取token
    @Override
    public void getAccessToken(final Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.tokenUrl(), null, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                UserManager.getInstance().setToken(GsonUtils.gsonToBean(result.toMsgString(), AccessToken.class));
                callback.getTokenSuccess(params);
            }
        });
    }
}
