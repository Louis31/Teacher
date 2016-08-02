package com.haiku.wateroffer.common.util.net;


import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.LogUtils;

/**
 * Created by hyming on 2016/7/7.
 */
public class MyXUtilsCallback extends XUtilsCallback<ResultData> {
    private final String TAG = "onSuccess";

    public MyXUtilsCallback(IRequestCallback callback) {
        super(callback);
    }

    @Override
    public void onSuccess(ResultData result) {
        super.onSuccess(result);
        LogUtils.showLogE(TAG, result.toString());
        if (result.isSuccess()) {
            onSuccessCallback(result);
        } else if (result.isTokenFail()) {
            cb.onTokenFail();
        } else {
            cb.onError(result.getRetcode(), App.getErrorMsg(result.getRetcode()));
        }
    }

    // 成功回调
    protected void onSuccessCallback(ResultData result) {

    }
}
