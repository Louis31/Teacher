package com.haiku.wateroffer.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;

/**
 * Created by hyming on 2016/7/13.
 */
public interface IBaseModel {
    // 获取token回调
    interface GetTokenCallback extends IRequestCallback {
        // 获取token成功
        void getTokenSuccess();
    }

    // 获取token
    void getAccessToken(@NonNull GetTokenCallback callback);
}
