package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.Deposit;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 保证金的Contract
 * Created by hyming on 2016/7/13.
 */
public interface DepositContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示支付宝页面
        void showAliPayView(Deposit bean);

        // 显示微信页面
        void showWechatPayView(Deposit bean);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 微信支付
        void weChatPayment(int uid);

        // 支付宝支付
        void alipayPayment(int uid);
    }
}
