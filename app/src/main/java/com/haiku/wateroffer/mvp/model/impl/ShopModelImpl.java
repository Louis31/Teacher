package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.Map;

/**
 * User Model实现类
 * Created by hyming on 2016/7/6.
 */
public class ShopModelImpl extends BaseModelImpl implements IShopModel {
    private final String TAG = "ShopModelImpl";

    // 获取验证码
    @Override
    public void getVerifyCode(Map<String, Object> params, @NonNull final IPhoneCallback callback) {
        XUtils.Get(UrlConstant.smsCodeUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.getVerifyCodeSuccess(result.getRetmsg().getAsString());
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取店铺信息
    @Override
    public void getShopInfo(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Get(UrlConstant.Shop.shopInfoUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺名称
    @Override
    public void changeShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopNameUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取店铺QQ
    @Override
    public void getShopQQ(Map<String, Object> params, @NonNull final IShopQQCallback callback) {
        XUtils.Get(UrlConstant.Shop.getShopQQUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    JsonObject jobj = result.getRetmsg().getAsJsonObject();
                    String qq = jobj.get("qq").getAsString();
                    callback.getShopQQSuccess(qq);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺QQ
    @Override
    public void changeShopQQ(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopQQUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺联系电话
    @Override
    public void changeShopPhone(Map<String, Object> params, @NonNull final IPhoneCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopPhoneUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
