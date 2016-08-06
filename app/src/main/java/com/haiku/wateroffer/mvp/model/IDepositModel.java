package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Deposit;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.Map;

/**
 * 保证金Model接口
 * Created by hyming on 2016/7/13.
 */
public interface IDepositModel {

    interface IDepositCallback extends IRequestCallback {
        // 获取保证金订单
        void getOrderSuccess(Deposit bean);
    }

    // 获取保证金订单
    void getDepositOrder(Map<String, Object> params, @NonNull IDepositCallback callback);
}
