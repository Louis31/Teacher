package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
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
                if (result.isSuccess()) {
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
                    ShopInfo bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().toString(), ShopInfo.class);
                    callback.getShopInfoSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 添加店铺名称
    @Override
    public void addShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopNameUrl(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 修改店铺logoo
    @Override
    public void changeShopLogo(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopLogo(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    // ResultData{retcode=0, retmsg={"logoUrl":"http://sendwater.api.youdians.com/uploads/8/05f03c7b0f90889dc8a9ba8b158ecbf7."}}
                    String logoUrl = "";
                    if (!result.getRetmsg().isJsonNull()) {
                        JsonObject jObj = result.getRetmsg().getAsJsonObject();
                        logoUrl = jObj.get("logoUrl").getAsString();
                    }
                    callback.uploadLogoSuccess(logoUrl);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改配送距离
    @Override
    public void changeShopRange(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopRange(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 获取营业状态
    @Override
    public void getShopOpenStatus(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Get(UrlConstant.Shop.getShopOpenStatus(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    JsonObject jobj = result.getRetmsg().getAsJsonObject();
                    //返回结果：{"retcode":0,"retmsg":{"openStatus":营业状态（0是营业中，1是打烊）}}
                    callback.getOpenStatusSuccess(jobj.get("openStatus").getAsString());
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    @Override
    public void setShopOpenStatus(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Post(UrlConstant.Shop.setShopOpenStatus(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    JsonObject jobj = result.getRetmsg().getAsJsonObject();
                    //返回结果：{"retcode":0,"retmsg":{"openStatus":营业状态（0是营业中，1是打烊）}}
                    callback.getOpenStatusSuccess(jobj.get("openStatus").getAsString());
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
