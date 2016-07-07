package com.haiku.wateroffer.common.util.net;

import android.widget.Toast;

import com.haiku.onekeydeliveryseller.R;
import com.haiku.wateroffer.bean.Result;
import com.haiku.wateroffer.common.util.SystemUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

/**
 * Created by hyming on 2016/7/7.
 */
public class XUtilsCallback<T> implements Callback.CommonCallback<Result<T>> {
    private IRequestCallback callback;

    public XUtilsCallback(IRequestCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(Result<T> result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (ex instanceof HttpException) { // 网络错误
            HttpException httpEx = (HttpException) ex;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            String errorResult = httpEx.getResult();
            callback.onError(responseCode, responseMsg);
            // ...
            Toast.makeText(x.app(), responseMsg, Toast.LENGTH_SHORT).show();
        } else { // 其他错误
            // ...
            if (SystemUtils.isNetworkConnected(x.app())) {
                callback.onError(-1, x.app().getString(R.string.msg_server_error));
                Toast.makeText(x.app(), x.app().getString(R.string.msg_server_error), Toast.LENGTH_SHORT).show();
            } else {
                callback.onError(-1, x.app().getString(R.string.msg_netword_error));
                Toast.makeText(x.app(), x.app().getString(R.string.msg_netword_error), Toast.LENGTH_SHORT).show();
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
