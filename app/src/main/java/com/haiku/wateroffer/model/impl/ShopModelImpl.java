package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IShopModel;
import com.haiku.wateroffer.model.IUserModel;

/**
 * Shop Model接口实现
 * Created by hyming on 2016/7/13.
 */
public class ShopModelImpl implements IShopModel {
    private final String TAG = "ShopModelImpl";

    // 设置店铺名称
    @Override
    public void addShopName(int uid, String shopName, @NonNull final ShopNameCallback callback) {
        XUtils.Post(UrlConstant.User.addShopNameUrl(), ParamUtils.User.getAddShopNameParams(uid, shopName), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    callback.onAddShopNameSuccess();
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }
}
