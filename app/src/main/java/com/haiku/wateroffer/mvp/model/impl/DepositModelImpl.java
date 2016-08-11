package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.haiku.wateroffer.bean.Deposit;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.WechatParams;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.net.MyXUtilsCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IDepositModel;

import java.util.Map;

/**
 * 保证金接口实现类
 * Created by hyming on 2016/7/13.
 */
public class DepositModelImpl extends BaseModelImpl implements IDepositModel {

    @Override
    public void getDepositOrder(final Map<String, Object> params, @NonNull final IDepositCallback callback) {
        XUtils.Post(UrlConstant.User.depositUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                int paymethod = (int)params.get("paymethod");
                if (paymethod == TypeConstant.PayType.WECHAT) {
                    JsonObject jobj = result.getRetmsg().getAsJsonObject();
                    String data = jobj.get("payment_package").toString();
                    String packageValue = jobj.get("payment_package").getAsJsonObject().get("package").getAsString();
                    WechatParams bean = GsonUtils.gsonToBean(data, WechatParams.class);
                    bean.setPackagevalue(packageValue);
                    callback.getOrderSuccess(bean);
                } else {
                    callback.getOrderSuccess(GsonUtils.gsonToBean(result.toMsgString(), Deposit.class));
                }
            }
        });
    }
}
