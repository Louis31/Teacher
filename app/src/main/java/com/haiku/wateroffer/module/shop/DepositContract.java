package com.haiku.wateroffer.module.shop;

import com.haiku.wateroffer.module.base.BasePresenter;
import com.haiku.wateroffer.module.base.BaseView;

/**
 * Created by hyming on 2016/7/13.
 */
public class DepositContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        // 微信支付
        void weChatPayment();

        // 支付宝支付
        void alipayPayment();
    }
}
