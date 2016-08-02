package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.MyXUtilsCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IOrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Order Model实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderModelImpl extends BaseModelImpl implements IOrderModel {
    // 获取订单列表
    @Override
    public void getOrderList(Map<String, Object> params, @NonNull final OrderListCallback callback) {
        XUtils.Get(UrlConstant.Order.listUrl(), ParamUtils.Order.getListParams(params), new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                List<OrderItem> list = new ArrayList<>();
                if (!result.getRetmsg().isJsonNull()) {
                    list = GsonUtils.gsonToList(result.toMsgString(), OrderItem.class);
                }
                callback.getListDataSuccess(list);
            }
        });
    }

    // 获取订单详情
    @Override
    public void getOrderInfo(Map<String, Object> params, @NonNull final OrderInfoCallback callback) {
        XUtils.Get(UrlConstant.Order.infoUrl(), ParamUtils.Order.getInfoParams(params), new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                OrderItem bean = GsonUtils.gsonToBean(result.toMsgString(), OrderItem.class);
                callback.getOrderInfoSuccess(bean);
            }
        });
    }

    // 取消订单
    @Override
    public void cancelOrder(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Order.cancelUrl(), ParamUtils.Order.cancelOrderParams(params), new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 派送订单
    @Override
    public void sendOrder(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Order.sendUrl(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                callback.onSuccess();
            }
        });
    }

    // 是否有配送员
    @Override
    public void isHasDeliver(final Map<String, Object> params, @NonNull final IOrderCallback callback) {
        XUtils.Get(UrlConstant.Deliver.isShopHasDeliver(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                JsonObject jobj = result.getRetmsg().getAsJsonObject();
                String judge = jobj.get("judge").getAsString();
                if (judge.equals("yes")) {
                    callback.checkHasDeliver(true, (Integer) params.get("order_id"), (Integer) params.get("uid"));
                    return;
                }
                callback.checkHasDeliver(false, -1, -1);
            }
        });
    }

    // 获取配送员订单列表
    @Override
    public void getDeliverOrder(Map<String, Object> params, @NonNull final OrderListCallback callback) {
        XUtils.Get(UrlConstant.Shop.getDeliverOrders(), params, new MyXUtilsCallback(callback) {
            @Override
            protected void onSuccessCallback(ResultData result) {
                List<OrderItem> list = new ArrayList<>();
                if (!result.getRetmsg().isJsonNull()) {
                    list = GsonUtils.gsonToList(result.toMsgString(), OrderItem.class);
                }
                callback.getListDataSuccess(list);
            }
        });
    }
}
