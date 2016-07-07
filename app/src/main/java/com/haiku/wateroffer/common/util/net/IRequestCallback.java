package com.haiku.wateroffer.common.util.net;

/**
 * Created by hyming on 2016/7/7.
 */
public interface IRequestCallback {
    // 错误回调
    void onError(int errorCode,String errorMsg);
}
