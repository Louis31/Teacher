package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.Map;

/**
 * Created by hyming on 2016/7/13.
 */
public interface IBaseModel {
    // 获取token
    void getAccessToken(Map<String, Object> params, @NonNull IRequestCallback callback);
}
