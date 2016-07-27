package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.Map;

/**
 * 店铺相关逻辑处理
 * Created by hyming on 2016/7/25.
 */
public interface IShopModel {
    // 店铺相关回调
    interface ShopCallback extends IRequestCallback {
        // 获取店铺信息
        void getShopInfoSuccess(ShopInfo bean);

        // 上传logo成功
        void uploadLogoSuccess(String logo);

        void getOpenStatusSuccess(String status);
    }

    // 联系电话回调
    interface IPhoneCallback extends IRequestCallback {
        void getVerifyCodeSuccess(String verifyCode);
    }

    interface IShopQQCallback extends IRequestCallback {
        void getShopQQSuccess(String qq);
    }

    // 获取验证码
    void getVerifyCode(Map<String, Object> params, @NonNull IPhoneCallback callback);

    // 获取店铺信息
    void getShopInfo(Map<String, Object> params, @NonNull ShopCallback callback);

    // 添加店铺名称
    void addShopName(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 修改店铺名称
    void changeShopName(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 获取店铺QQ
    void getShopQQ(Map<String, Object> params, @NonNull IShopQQCallback callback);

    // 修改店铺QQ
    void changeShopQQ(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 修改店铺联系电话
    void changeShopPhone(Map<String, Object> param, @NonNull IPhoneCallback callback);

    // 修改店铺logo
    void changeShopLogo(Map<String, Object> params, @NonNull ShopCallback callback);

    // 修改配送距离
    void changeShopRange(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 获取营业状态
    void getShopOpenStatus(Map<String, Object> params, @NonNull ShopCallback callback);
}
