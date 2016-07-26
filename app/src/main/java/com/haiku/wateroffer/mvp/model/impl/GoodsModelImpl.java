package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IGoodsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Goods Model实现类
 * Created by hyming on 2016/7/11.
 */
public class GoodsModelImpl implements IGoodsModel {
    private final String TAG = "GoodsModelImpl";

    // 获取商品列表
    @Override
    public void getGoodsList(Map<String, Object> params, @NonNull final GoodsListCallback callback) {
        XUtils.Get(UrlConstant.Goods.listUrl(), ParamUtils.Goods.getListParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    List<Goods> list = new ArrayList<>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), Goods.class);
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 删除商品
    @Override
    public void deleteGoods(Map<String, Object> params, @NonNull final GoodsListCallback callback) {
        XUtils.Get(UrlConstant.Goods.deleteUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 下架商品
    @Override
    public void offShelfGoods(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.Goods.offShelfUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 上架商品
    @Override
    public void upShelfGoods(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.Goods.upShelfUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
