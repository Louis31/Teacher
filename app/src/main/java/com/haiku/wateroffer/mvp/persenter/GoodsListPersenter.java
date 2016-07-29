package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.contract.GoodsListContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IGoodsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品列表模块Presenter实现类
 * Created by hyming on 2016/7/12.
 */
public class GoodsListPersenter implements GoodsListContract.Presenter, IGoodsModel.GoodsListCallback {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_DELETE = 2;// 删除
    private final int REQUEST_OFF_SHELF = 3;// 下架
    private final int REQUEST_UP_SHELF = 4;// 上架
    private int requesType;

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
        requesType = REQUEST_LIST;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("status", status);
        params.put("pageno", pageno);

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.getGoodsList(params, this);
        }
    }

    // 删除商品
    @Override
    public void deleteGoods(int uid, int product_id) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_DELETE;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("product_id", product_id);

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.deleteGoods(params, this);
        }
    }

    // 下架商品
    @Override
    public void offShelfGoods(int uid, int product_id) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_OFF_SHELF;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("product_id", product_id);

        if (UserManager.isTokenEmpty()) {
            // 获取token
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.offShelfGoods(params, this);
        }
    }

    // 上架商品
    @Override
    public void upShelfGoods(int uid, int product_id) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_UP_SHELF;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("product_id", product_id);

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.upShelfGoods(params, this);
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
        if (requesType == REQUEST_LIST) {
            mGoodsModel.getGoodsList(params, this);
        } else if (requesType == REQUEST_DELETE) {
            mGoodsModel.deleteGoods(params, this);
        } else if (requesType == REQUEST_OFF_SHELF) {
            mGoodsModel.offShelfGoods(params, this);
        } else if (requesType == REQUEST_UP_SHELF) {
            mGoodsModel.upShelfGoods(params, this);
        }
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        if (requesType == REQUEST_DELETE) {
            // 删除成功
            mView.refreshListView(TypeConstant.GoodsOpera.DELETE_GOODS);
        } else if (requesType == REQUEST_OFF_SHELF) {
            // 下架成功
            mView.refreshListView(TypeConstant.GoodsOpera.OFF_SHELF);
        } else if (requesType == REQUEST_UP_SHELF) {
            // 上架成功
            mView.refreshListView(TypeConstant.GoodsOpera.UP_SHELF);
        }
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
