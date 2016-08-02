package com.haiku.wateroffer.common.util.net;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.SystemUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.constant.BaseConstant;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

/**
 * Created by hyming on 2016/7/7.
 */
public class XUtilsCallback<ResultType> implements Callback.CommonCallback<ResultType> {
    protected IRequestCallback cb;

    public XUtilsCallback(IRequestCallback cb) {
        this.cb = cb;
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
            if (cb != null) {
                cb.onError(responseCode, responseMsg);
            }
            // ...
        } else { // 其他错误
            // ...
            if (SystemUtils.isNetworkConnected(x.app())) {
                if (cb != null) {
                    cb.onError(BaseConstant.SERVER_ERROR, x.app().getString(R.string.err_server_error));
                }
            } else {
                if (cb != null) {
                    cb.onError(BaseConstant.METWORD_ERROR, x.app().getString(R.string.err_netword_error));
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
