package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.TypeConstant;
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
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    List<OrderItem> list = new ArrayList<>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), OrderItem.class);
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
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
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    OrderItem bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().getAsString(), OrderItem.class);
                    callback.getOrderInfoSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 取消订单
    @Override
    public void cancelOrder(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Order.cancelUrl(), ParamUtils.Order.cancelOrderParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 派送订单
    @Override
    public void sendOrder(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.Order.sendUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 是否有配送员
    @Override
    public void isHasDeliver(final Map<String, Object> params, @NonNull final OrderListCallback callback) {
        XUtils.Get(UrlConstant.User.getDeliverList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    if (!result.getRetmsg().isJsonNull()) {
                        List<Deliver> list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), Deliver.class);
                        for (Deliver bean : list) {
                            if (TypeConstant.Deliver.CONTINUE == bean.getDiliveryman_status()) {
                                callback.checkHasDeliver(true, (Integer) params.get("order_id"), (Integer) params.get("uid"));
                                return;
                            }
                        }
                    }
                    callback.checkHasDeliver(false, -1, -1);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取配送员订单列表
    @Override
    public void getDeliverOrder(Map<String, Object> params, @NonNull final OrderListCallback callback) {
        XUtils.Get(UrlConstant.Deliver.getOrderList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    List<OrderItem> list = new ArrayList<>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), OrderItem.class);
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
