package com.haiku.wateroffer.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.List;

/**
 * 商品Model接口,处理订单相关业务逻辑
 * Created by hyming on 2016/7/11.
 */
public interface IGoodsModel {
    // 商品列表模块相关回调
    interface GoodsListCallback extends IRequestCallback {
        // 获取列表数据成功
        void getListDataSuccess(List<Goods> list);

        // 获取列表数据失败
        void getListDataFail(String msg);
    }

    // 获取商品列表
    void getGoodsList(@NonNull GoodsListCallback callback);
}
