package com.haiku.wateroffer.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.model.IBaseModel;

/**
 * Shop Model接口,处理店铺相关业务逻辑
 * Created by hyming on 2016/7/13.
 */
public interface IShopModel {
    // 店铺名称回调
    interface ShopNameCallback extends IRequestCallback {
        // 添加名称成功
        void onAddShopNameSuccess();
    }

    // 设置店铺名称
    void addShopName(int uid, String shopName, @NonNull ShopNameCallback callback);
}
