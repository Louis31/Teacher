package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User Model实现类
 * Created by hyming on 2016/7/6.
 */
public class UserModelImpl extends BaseModelImpl implements IUserModel {
    private final String TAG = "UserModelImpl";

    // 登陆
    @Override
    public void login(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.loginUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    UserManager.getInstance().setUser(GsonUtils.gsonToBean(result.getRetmsg().toString(), User.class));
                    // TODO 设置默认uid
                    //UserManager.getInstance().getUser().setUid(1431);
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取验证码
    @Override
    public void getVerifyCode(Map<String, Object> params, @NonNull final GetVerifyCodeCallback callback) {
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

    // 获取我的账单列表
    @Override
    public void getBillList(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.getBillList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    List<Bill> list = new ArrayList<Bill>();
                    JsonArray jArry = result.getRetmsg().getAsJsonArray();
                    if (!GsonUtils.isJsonArrayEmpty(jArry)) {
                        list = GsonUtils.gsonToList(jArry.toString(), Bill.class);
                    }
                    callback.getBillSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 查询账单
    @Override
    public void searchBill(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.searchBill(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    Bill bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().toString(), Bill.class);
                    callback.searchBillSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取配送员列表
    @Override
    public void getDeliverList(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.getDeliverList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    List<Deliver> list = new ArrayList<Deliver>();
                    JsonArray jArry = result.getRetmsg().getAsJsonArray();
                    if (!GsonUtils.isJsonArrayEmpty(jArry)) {
                        list = GsonUtils.gsonToList(jArry.toString(), Deliver.class);
                    }
                    callback.getDeliverListSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 编辑配送员
    @Override
    public void changeDeliverStatus(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.editDeliver(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.changeStatusSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺名称
    @Override
    public void addShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopNameUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺电话
    @Override
    public void changePhone(Map<String, Object> params, @NonNull final GetVerifyCodeCallback callback) {
        XUtils.Post(UrlConstant.User.changePhone(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺logo
    public void changeShopLogo(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.changeShopLogo(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
