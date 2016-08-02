package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.contract.ShopAddrContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加/修改店铺地址Presenter
 * Created by hyming on 2016/7/13.
 */
public class ShopAddrPresenter implements ShopAddrContract.Presenter, IRequestCallback {

    private final int RQTYPE_ADDR = 1;
    private final int RQTYPE_LOCAT = 2;
    private int requestType;
    private Map<String, Object> mParams;


    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final ShopAddrContract.View mView;

    public ShopAddrPresenter(@NonNull IUserModel userModel, @NonNull ShopAddrContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    @Override
    public void addShopAddress(int uid, String area, String floorDetail, String lat, String lng) {
        mView.showLoadingDialog(true);
        requestType = RQTYPE_ADDR;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("area", area);
        params.put("floorDetail", floorDetail);
        params.put("lat", lat);
        params.put("lng", lng);

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.addShopAddress(params, this);
        }
    }

    /*private void uploadLoaction() {
        requestType = RQTYPE_LOCAT;
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mUserModel).getAccessToken(mParams, this);
        } else {
            mUserModel.uploadLocation(mParams, this);
        }
    }*/

    /**
     * Callback 接口方法
     */
    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mUserModel.addShopAddress(params, this);
    }

    // 成功回调
    @Override
    public void onSuccess() {
       /* if (requestType == RQTYPE_ADDR) {
            uploadLoaction();
        } else {
            mView.showLoadingDialog(false);
            mView.showSuccessView();
        }*/

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
