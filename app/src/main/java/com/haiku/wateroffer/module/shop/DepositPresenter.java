package com.haiku.wateroffer.module.shop;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.model.IDepositModel;

/**
 * 保证金页面Presenter实现类
 * Created by hyming on 2016/7/13.
 */
public class DepositPresenter implements DepositContract.Presenter {

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
    public void weChatPayment() {

    }

    // 支付宝支付
    @Override
    public void alipayPayment() {

    }
}
