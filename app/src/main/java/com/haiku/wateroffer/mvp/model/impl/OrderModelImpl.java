package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IOrderModel;

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
                    List<OrderItem> list = new ArrayList<>();
                    JsonArray jArry = result.getRetmsg().getAsJsonArray();
                    if (!GsonUtils.isJsonArrayEmpty(jArry)) {
                        list = GsonUtils.gsonToList(jArry.toString(),OrderItem.class);
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }

    // 获取订单详情
    @Override
    public void getOrderInfo(Map<String, Object> params, @NonNull final OrderInfoCallback callback) {
        XUtils.Get(UrlConstant.Order.infoUrl(), ParamUtils.Order.getInfoParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    OrderItem bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().getAsString(), OrderItem.class);
                    callback.getOrderInfoSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), result.getRetmsg().getAsString());
                }
            }
        });
    }

    // 取消订单
    @Override
    public void cancelOrder(Map<String, Object> params, @NonNull OrderOperateCallback callback) {

    }

    // 派送订单
    @Override
    public void sendOrder(Map<String, Object> params, @NonNull OrderOperateCallback callback) {

    }
}
