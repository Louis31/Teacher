package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IOrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Order Model实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderModelImpl implements IOrderModel {
    private final String TAG = "OrderModelImpl";

    // 获取订单列表
    @Override
    public void getOrderList(Map<String, Object> params, @NonNull final OrderListCallback callback) {
        XUtils.Get(UrlConstant.Order.listUrl(), ParamUtils.Order.getListParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    result.getRetmsg().getAsJsonArray();
                    List<OrderItem> list = new ArrayList<OrderItem>();
                    if (GsonUtils.isJsonArrayEmpty(result.getRetmsg().getAsJsonArray())) {
                    } else {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().getAsString());
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });

    }
}
