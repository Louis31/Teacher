package com.haiku.wateroffer.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by JP on 2016/3/17.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxbee71cab9a09c0d9", false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int code = resp.errCode;
        switch (code) {
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "您取消了支付", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "支付失败 - " + resp.errCode, Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }
}
