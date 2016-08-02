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

        // 修改状态成功
        void changeStatusSuccess();

        // 添加配送员成功
        void addDeliverSuccess(Deliver deliver);
    }

    // 登陆
    void login(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 获取验证码
    void getVerifyCode(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 获取我的账单明细
    void getBillList(Map<String, Object> params, @NonNull MyBillCallback callback);

    // 查询账单
    void searchBill(Map<String, Object> params, @NonNull MyBillCallback callback);

    // 获取配送员列表
    void getDeliverList(Map<String, Object> params, @NonNull DeliverCallback callback);

    // 编辑配送员
    void changeDeliverStatus(Map<String, Object> params, @NonNull DeliverCallback callback);

    // 添加配送员
    void addDeliver(Map<String, Object> params, @NonNull DeliverCallback callback);

    // 修改店铺地址
    void addShopAddress(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 上传用户的位置
    //void uploadLocation(Map<String, Object> params, @NonNull final IRequestCallback callback);
}
