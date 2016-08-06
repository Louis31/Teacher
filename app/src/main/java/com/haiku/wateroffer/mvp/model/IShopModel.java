package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Contribution;
import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.List;
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

        void setOpenStatusSuccess(String status);
    }

    interface IShopQQCallback extends IRequestCallback {
        void getShopQQSuccess(String qq);
    }

    interface IContributionCallback extends IRequestCallback {
        void getContributionSuccess(List<Contribution> list);
    }

    // 获取验证码
    void getVerifyCode(Map<String, Object> params, @NonNull IRequestCallback callback);

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
    void changeShopPhone(Map<String, Object> param, @NonNull IRequestCallback callback);

    // 修改店铺logo
    void changeShopLogo(Map<String, Object> params, @NonNull ShopCallback callback);

    // 修改配送距离
    void changeShopRange(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 设置营业状态
    void setShopOpenStatus(Map<String, Object> params, @NonNull ShopCallback callback);

    // 获取店铺保证金状态
    void getShopMarginStatus(Map<String, Object> params, @NonNull final IRequestCallback callback);

    // 获取贡献值
    void getContribution(Map<String, Object> params, @NonNull final IContributionCallback callback);
}
