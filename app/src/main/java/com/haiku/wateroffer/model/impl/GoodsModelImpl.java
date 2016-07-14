package com.haiku.wateroffer.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.ErrorCode;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.model.IGoodsModel;

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
        XUtils.Post(UrlConstant.Goods.listUrl(), ParamUtils.Goods.getListParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == ErrorCode.SUCCESS) {
                    List<Goods> list = new ArrayList<Goods>();
                    if (!GsonUtils.isJsonArrayEmpty(result.getRetmsg().getAsJsonArray())) {
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
