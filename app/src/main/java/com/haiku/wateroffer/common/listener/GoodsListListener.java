package com.haiku.wateroffer.common.listener;

/**
 * 商品列表相关事件
 * Created by hyming on 2016/7/18.
 */
public interface GoodsListListener {
    // 删除商品
    void onGoodsDeleteClick(int pos);

    // 上架商品
    void onGoodsUpShelfClick(int pos);

    // 下架商品
    void onGoodsOffShelfClick(int pos);

    // 编辑商品
    void onGoodsEditClick(int pos);
}
