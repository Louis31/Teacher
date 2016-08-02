package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.SharedPreferencesUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.MyXUtilsCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
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
    // 登陆
    @Override
    public void login(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.loginUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                String jsonStr = result.getRetmsg().toString();
                SharedPreferencesUtils.save(SharedPreferencesUtils.USER, jsonStr);
                UserManager.getInstance().setUser(GsonUtils.gsonToBean(jsonStr, User.class));
                callback.onSuccess();
            }
        });
    }

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

    // 获取我的账单列表
    @Override
    public void getBillList(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.getBillList(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                List<Bill> list = new ArrayList<Bill>();
                if (!result.getRetmsg().isJsonNull()) {
                    list = GsonUtils.gsonToList(result.toMsgString(), Bill.class);
                }
                callback.getBillSuccess(list);

            }
        });
    }

    // 查询账单
    @Override
    public void searchBill(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.searchBill(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                Bill bean = GsonUtils.gsonToBean(result.toMsgString(), Bill.class);
                callback.searchBillSuccess(bean);
            }
        });
    }

    // 获取配送员列表
    @Override
    public void getDeliverList(Map<String, Object> params, @NonNull final DeliverSelectCallback callback) {
        XUtils.Get(UrlConstant.User.getDeliverList(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                List<Deliver> list = new ArrayList<Deliver>();
                if (!result.getRetmsg().isJsonNull()) {
                    list = GsonUtils.gsonToList(result.toMsgString(), Deliver.class);
                }
                callback.getDeliverListSuccess(list);
            }
        });
    }

    // 编辑配送员
    @Override
    public void changeDeliverStatus(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.editDeliver(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.changeStatusSuccess();
            }
        });
    }

    // 添加配送员
    @Override
    public void addDeliver(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.addDeliver(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.addDeliverSuccess(GsonUtils.gsonToBean(result.toMsgString(), Deliver.class));
            }
        });
    }

    // 修改店铺地址
    @Override
    public void addShopAddress(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopAddress(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    /*@Override
    public void uploadLocation(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.uploadLocation(), params, new XUtilsCallback<ResultData>(callback) {
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
    }*/
}
