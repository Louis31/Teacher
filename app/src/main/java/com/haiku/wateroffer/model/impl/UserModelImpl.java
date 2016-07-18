package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IUserModel;

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
    public void login(Map<String, Object> params, @NonNull final LoginCallback callback) {
        XUtils.Post(UrlConstant.User.loginUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    UserManager.getInstance().setUser(GsonUtils.gsonToBean(result.getRetmsg().toString(), User.class));
                    callback.onLoginSuccess();
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }

    // 获取验证码
    @Override
    public void getVerifyCode(Map<String, Object> params, @NonNull final LoginCallback callback) {
        XUtils.Get(UrlConstant.smsCodeUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    callback.getVerifyCodeSuccess(result.getRetmsg().getAsString());
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
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
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    List<Bill> list = new ArrayList<Bill>();
                    if (!GsonUtils.isJsonArrayEmpty(result.getRetmsg().getAsJsonArray())) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().getAsString());
                    }
                    callback.getBillSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
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
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    Bill bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().getAsString(), Bill.class);
                    callback.searchBillSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
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
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    List<Deliver> list = new ArrayList<Deliver>();
                    if (!GsonUtils.isJsonArrayEmpty(result.getRetmsg().getAsJsonArray())) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().getAsString());
                    }
                    callback.getDeliverListSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }
}
