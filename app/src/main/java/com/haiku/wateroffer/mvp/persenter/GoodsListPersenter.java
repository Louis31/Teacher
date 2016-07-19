package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IGoodsModel;
import com.haiku.wateroffer.mvp.contract.GoodsListContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Presenter 接口方法
     */
    // 获取列表数据
    @Override
    public void getListDatas(int uid, int status, int pageno) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("status", status);
        params.put("pageno", pageno);

        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.getGoodsList(params, this);
        }
    }

    /**
     * Callback 接口方法
     */
    // 获取列表数据成功
    @Override
    public void getListDataSuccess(List<Goods> list) {
        mView.showListView(list);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mGoodsModel.getGoodsList(params, this);
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showMessage(errorMsg);
    }
}
