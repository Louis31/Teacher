package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.util.net.IRequestCallback;

import java.util.List;
import java.util.Map;

/**
 * 商品Model接口,处理订单相关业务逻辑
 * Created by hyming on 2016/7/11.
 */
public interface IGoodsModel {
    // 商品列表模块相关回调
    interface GoodsListCallback extends IRequestCallback {
        // 获取列表数据成功
        void getListDataSuccess(List<Goods> list);
    }

    // 获取商品列表
    void getGoodsList(Map<String, Object> params, @NonNull GoodsListCallback callback);

    // 删除商品
    void deleteGoods(Map<String, Object> params, @NonNull GoodsListCallback callback);
}
