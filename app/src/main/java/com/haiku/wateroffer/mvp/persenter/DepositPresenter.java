package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Deposit;
import com.haiku.wateroffer.bean.WechatParams;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.contract.DepositContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IDepositModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 保证金页面Presenter实现类
 * Created by hyming on 2016/7/13.
 */
public class DepositPresenter implements DepositContract.Presenter, IDepositModel.IDepositCallback {
    private final int REQUEST_WECHAT = 1;
    private final int REQUEST_ALI = 2;
    private int requesType;
    private Map<String, Object> mTempParams;// 存储当前请求的参数

    @NonNull
    private final IDepositModel mDepositModel;
    @NonNull
    private final DepositContract.View mView;


    public DepositPresenter(@NonNull IDepositModel depositModel, @NonNull DepositContract.View view) {
        this.mDepositModel = depositModel;
        this.mView = view;
        mView.setPresenter(this);
    }


    // 微信支付
    @Override
    public void weChatPayment(int uid) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_WECHAT;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("paymethod", TypeConstant.PayType.WECHAT);
        if (!isTokenFail(params)) {
            mDepositModel.getDepositOrder(params, this);
        }
    }

    // 支付宝支付
    @Override
    public void alipayPayment(int uid) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_ALI;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("paymethod", TypeConstant.PayType.ALI);
        if (!isTokenFail(params)) {
            mDepositModel.getDepositOrder(params, this);
        }
    }

    @Override
    public void getOrderSuccess(Deposit bean) {
        mView.showLoadingDialog(false);
        mView.showAliPayView(bean);
    }

    @Override
    public void getOrderSuccess(WechatParams bean) {
        mView.showLoadingDialog(false);
        mView.showWechatPayView(bean);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mDepositModel.getDepositOrder(params, this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }

    @Override
    public void onTokenFail() {
        UserManager.cleanToken();
        ((IBaseModel) mDepositModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mDepositModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }
}
