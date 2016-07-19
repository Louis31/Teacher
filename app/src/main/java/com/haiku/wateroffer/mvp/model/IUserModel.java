package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.List;
import java.util.Map;

/**
 * User Model接口,处理用户相关业务逻辑
 * Created by hyming on 2016/7/6.
 */
public interface IUserModel {
    // 登录模块相关回调
    interface LoginCallback extends IRequestCallback {
        // 登陆成功
        void onLoginSuccess();

        // 获取验证码成功
        void getVerifyCodeSuccess(String verifyCode);
    }

    // 我的账单回调
    interface MyBillCallback extends IRequestCallback {
        // 获取账单数据成功
        void getBillSuccess(List<Bill> list);

        // 查询账单成功
        void searchBillSuccess(Bill bean);
    }

    // 配送列表回调
    interface DeliverCallback extends IRequestCallback {
        // 获取配送列表成功
        void getDeliverListSuccess(List<Deliver> list);
    }

    // 登陆
    void login(Map<String, Object> params, @NonNull LoginCallback callback);

    // 获取验证码
    void getVerifyCode(Map<String, Object> params, @NonNull LoginCallback callback);

    // 获取我的账单明细
    void getBillList(Map<String, Object> params, @NonNull MyBillCallback callback);

    // 查询账单
    void searchBill(Map<String, Object> params, @NonNull MyBillCallback callback);

    // 获取配送员列表
    void getDeliverList(Map<String, Object> params, @NonNull DeliverCallback callback);

    // 修改店铺名称
    void addShopName(Map<String, Object> params, @NonNull IRequestCallback callback);
}
