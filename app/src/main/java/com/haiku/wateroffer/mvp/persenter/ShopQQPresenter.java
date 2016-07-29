package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.contract.ShopQQContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加/修改店铺QQPresenter
 * Created by hyming on 2016/7/13.
 */
public class ShopQQPresenter implements ShopQQContract.Presenter, IShopModel.IShopQQCallback {
    private final int REQUEST_GET = 1;
    private final int REQUEST_CHANGE = 2;
    private int requesType;
    @NonNull
    private final IShopModel mShopModel;
    @NonNull
    private final ShopQQContract.View mView;

    public ShopQQPresenter(@NonNull IShopModel shopModel, @NonNull ShopQQContract.View view) {
        this.mShopModel = shopModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    @Override
    public void changeQQNumber(int id, String qq) {
        requesType = REQUEST_CHANGE;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("qq", qq);
        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.changeShopQQ(params, this);
        }
    }

    @Override
    public void getQQNumber(int id) {
        requesType = REQUEST_GET;
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mShopModel).getAccessToken(params, this);
        } else {
            mShopModel.getShopQQ(params, this);
        }
    }

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_GET) {
            mShopModel.getShopQQ(params, this);
        } else if (requesType == REQUEST_CHANGE) {
            mShopModel.changeShopQQ(params, this);
        }
    }

    @Override
    public void getShopQQSuccess(String qq) {
        mView.setShopQQ(qq);
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showSuccessView();
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
