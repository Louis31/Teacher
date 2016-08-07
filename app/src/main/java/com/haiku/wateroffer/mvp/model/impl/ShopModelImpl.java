package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.haiku.wateroffer.bean.Contribution;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.MyXUtilsCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User Model实现类
 * Created by hyming on 2016/7/6.
 */
public class ShopModelImpl extends BaseModelImpl implements IShopModel {
    // 获取验证码
    @Override
    public void getVerifyCode(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.smsCodeUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 获取店铺信息
    @Override
    public void getShopInfo(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Get(UrlConstant.Shop.shopInfoUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                ShopInfo bean = GsonUtils.gsonToBean(result.toMsgString(), ShopInfo.class);
                callback.getShopInfoSuccess(bean);
            }
        });
    }

    // 添加店铺名称
    @Override
    public void addShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopNameUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 修改店铺名称
    @Override
    public void changeShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopNameUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 获取店铺QQ
    @Override
    public void getShopQQ(Map<String, Object> params, @NonNull final IShopQQCallback callback) {
        XUtils.Get(UrlConstant.Shop.getShopQQUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                JsonObject jobj = result.getRetmsg().getAsJsonObject();
                String qq = jobj.get("qq").getAsString();
                callback.getShopQQSuccess(qq);
            }
        });
    }

    // 修改店铺QQ
    @Override
    public void changeShopQQ(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopQQUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 修改店铺联系电话
    @Override
    public void changeShopPhone(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopPhoneUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 修改店铺logoo
    @Override
    public void changeShopLogo(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopLogo(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                String logoUrl = "";
                if (!result.getRetmsg().isJsonNull()) {
                    JsonObject jObj = result.getRetmsg().getAsJsonObject();
                    logoUrl = jObj.get("logoUrl").getAsString();
                }
                callback.uploadLogoSuccess(logoUrl);
            }
        });
    }

    // 修改配送距离
    @Override
    public void changeShopRange(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Shop.changeShopRange(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    @Override
    public void setShopOpenStatus(Map<String, Object> params, @NonNull final ShopCallback callback) {
        XUtils.Post(UrlConstant.Shop.setShopOpenStatus(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                JsonObject jobj = result.getRetmsg().getAsJsonObject();
                //返回结果：{"retcode":0,"retmsg":{"openStatus":营业状态（0是营业中，1是打烊）}}
                callback.setOpenStatusSuccess(jobj.get("openStatus").getAsString());
            }
        });
    }


    @Override
    public void getShopMarginStatus(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.Shop.getShopMarginStatus(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                JsonObject jobj = result.getRetmsg().getAsJsonObject();
                String status = jobj.get("status").getAsString();
                if (status.equals("yes")) {
                    UserManager.getInstance().setIsPayDeposit(true);
                } else {
                    UserManager.getInstance().setIsPayDeposit(false);
                }
                callback.onSuccess();
            }
        });
    }

    @Override
    public void getContribution(Map<String, Object> params, @NonNull final IContributionCallback callback) {
        XUtils.Get(UrlConstant.Shop.getContribution(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                JsonObject jobj = result.getRetmsg().getAsJsonObject();
                JsonElement jEle = jobj.get("data");
                List<Contribution> list = new ArrayList<>();
                if (!jEle.isJsonNull()) {
                    list = GsonUtils.gsonToList(jEle.toString(), Contribution.class);
                }
                callback.getContributionSuccess(list);
            }
        });
    }
}
