package com.haiku.wateroffer.common.util.net;

import java.util.Map;

/**
 * Created by hyming on 2016/7/7.
 */
public interface IRequestCallback {
    // 获取token成功
    void getTokenSuccess(Map<String, Object> params);

    // token失效
    void onTokenFail();

    // 成功回调
    void onSuccess();

    // 错误回调
    void onError(int errorCode, String errorMsg);
}
