package com.haiku.wateroffer.mvp.model;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.GoodsCategory;
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

    interface IGoodsEditCallback extends IRequestCallback {
        void uploadImageSuccess(String url);

        void getCategorySuccess(List<GoodsCategory> list);

        void getGoodsInfoSuccess(Goods bean);

        void updateInfoSuccess(Goods goods);
    }

    // 获取商品列表
    void getGoodsList(Map<String, Object> params, @NonNull GoodsListCallback callback);

    // 删除商品
    void deleteGoods(Map<String, Object> params, @NonNull GoodsListCallback callback);

    // 下架商品
    void offShelfGoods(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 上架商品
    void upShelfGoods(Map<String, Object> params, @NonNull IRequestCallback callback);

    // 上传商品图片
    void uploadGoodsImage(Map<String, Object> params, @NonNull IGoodsEditCallback callback);

    // 获取商品分类
    void getGoodsCategory(Map<String, Object> params, @NonNull IGoodsEditCallback callback);

    // 添加商品
    void addGoods(Map<String, Object> params, @NonNull IGoodsEditCallback callback);

    // 更新商品
    void modifyGoods(Map<String, Object> params, @NonNull IGoodsEditCallback callback);

    // 获取商品数据
    void getGoodsInfo(Map<String, Object> params, @NonNull IGoodsEditCallback callback);
}
