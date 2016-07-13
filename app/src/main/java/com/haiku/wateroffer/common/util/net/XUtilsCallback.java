package com.haiku.wateroffer.common.util.net;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.SystemUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.constant.ErrorCode;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

/**
 * Created by hyming on 2016/7/7.
 */
public class XUtilsCallback<ResultType> implements Callback.CommonCallback<ResultType> {
    private IRequestCallback callback;

    public XUtilsCallback(IRequestCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(ResultType result) {
        LogUtils.showLogE("XUtilsCallback", "onSuccess");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtils.showLogE("XUtilsCallback", "onError");
        if (ex instanceof HttpException) { // 网络错误
            HttpException httpEx = (HttpException) ex;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            String errorResult = httpEx.getResult();
            if (callback != null) {
                callback.onError(responseCode, responseMsg);
            }
            // ...
        } else { // 其他错误
            // ...
            if (SystemUtils.isNetworkConnected(x.app())) {
                if (callback != null) {
                    callback.onError(ErrorCode.SERVER_ERROR, x.app().getString(R.string.msg_server_error));
                }
            } else {
                if (callback != null) {
                    callback.onError(ErrorCode.METWORD_ERROR, x.app().getString(R.string.msg_netword_error));
                }
            }
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onFinished() {
    }
}
