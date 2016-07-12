package com.haiku.wateroffer.module.goods;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.model.IGoodsModel;

import java.util.List;

/**
 * 商品列表模块Presenter实现类
 * Created by hyming on 2016/7/12.
 */
public class GoodsListPersenter implements GoodsListContract.Presenter, IGoodsModel.GoodsListCallback {

    @NonNull
    private final IGoodsModel mGoodsModel;
    @NonNull
    private final GoodsListContract.View mView;

    public GoodsListPersenter(@NonNull IGoodsModel goodsModel, @NonNull GoodsListContract.View view) {
        this.mGoodsModel = goodsModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    // 获取列表数据
    @Override
    public void getListDatas() {
        mGoodsModel.getGoodsList(this);
    }

    // 获取列表数据成功
    @Override
    public void getListDataSuccess(List<Goods> list) {
        mView.showListView(list);
    }

    // 获取列表数据失败
    @Override
    public void getListDataFail(String msg) {
        mView.showMessage(msg);
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
