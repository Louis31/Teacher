package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 保证金的Contract
 * Created by hyming on 2016/7/13.
 */
public interface DepositContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        // 微信支付
        void weChatPayment();

        // 支付宝支付
        void alipayPayment();
    }
}
