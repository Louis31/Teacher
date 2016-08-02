package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.GoodsCategory;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.contract.GoodsEditContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IGoodsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品编辑Presenter实现类
 * Created by hyming on 2016/7/26.
 */
public class GoodsEditPersenter implements GoodsEditContract.Presenter, IGoodsModel.IGoodsEditCallback {
    private final int REQUEST_UPLOAD = 1;
    private final int REQUEST_CATEGORY = 2;
    private final int REQUEST_ADD = 3;
    private final int REQUEST_MODIFY = 4;
    private final int REQUEST_INFO = 5;
    private int requesType;
    private Map<String, Object> mTempParams;// 存储当前请求的参数

    @NonNull
    private final IGoodsModel mGoodsModel;
    @NonNull
    private final GoodsEditContract.View mView;

    public GoodsEditPersenter(@NonNull IGoodsModel goodsModel, @NonNull GoodsEditContract.View view) {
        this.mGoodsModel = goodsModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter 接口方法
     */
    @Override
    public void uploadImage(String attach) {
        requesType = REQUEST_UPLOAD;
        mView.showLoadingDialog(true);
        Map<String, Object> params = new HashMap<>();
        params.put("attach", attach);
        if (!isTokenFail(params)) {
            mGoodsModel.uploadGoodsImage(params, this);
        }
    }

    @Override
    public void getCategory(int parent_id, int ishome) {
        requesType = REQUEST_CATEGORY;
        Map<String, Object> params = new HashMap<>();
        params.put("parent_id", parent_id);
        params.put("ishome", ishome);
        if (!isTokenFail(params)) {
            mGoodsModel.getGoodsCategory(params, this);
        }
    }

    @Override
    public void addGoods(int uid, String product_name, String product_images, String product_price, String product_store,
                         String product_description, String product_category, String product_buyingcycle, String product_personalamount,
                         String product_beyondprice) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_ADD;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("product_name", product_name);
        params.put("product_images", product_images);
        params.put("product_price", product_price);
        params.put("product_store", product_store);
        params.put("product_description", product_description);
        params.put("product_category", product_category);
        params.put("product_buyingcycle", product_buyingcycle);
        params.put("product_personalamount", product_personalamount);
        params.put("product_beyondprice", product_beyondprice);

        if (!isTokenFail(params)) {
            mGoodsModel.addGoods(params, this);
        }
    }

    @Override
    public void modifyGoods(int uid, String product_id, String product_name, String product_images, String product_price, String product_store,
                            String product_description, String product_category, String product_buyingcycle,
                            String product_personalamount, String product_beyondprice) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_MODIFY;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("product_id", product_id);
        params.put("product_name", product_name);
        params.put("product_images", product_images);
        params.put("product_price", product_price);
        params.put("product_store", product_store);
        params.put("product_description", product_description);
        params.put("product_category", product_category);
        params.put("product_buyingcycle", product_buyingcycle);
        params.put("product_personalamount", product_personalamount);
        params.put("product_beyondprice", product_beyondprice);

        if (!isTokenFail(params)) {
            mGoodsModel.modifyGoods(params, this);
        }
    }

    @Override
    public void getGoodsInfo(int product_id) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_INFO;
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", product_id);
        if (!isTokenFail(params)) {
            mGoodsModel.getGoodsInfo(params, this);
        }
    }

    /**
     * Callback 接口方法
     */

    @Override
    public void uploadImageSuccess(String url) {
        mView.showLoadingDialog(false);
        mView.setImageView(url);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        if (requesType == REQUEST_UPLOAD) {
            mGoodsModel.uploadGoodsImage(params, this);
        } else if (requesType == REQUEST_CATEGORY) {
            mGoodsModel.getGoodsCategory(params, this);
        } else if (requesType == REQUEST_ADD) {
            mGoodsModel.addGoods(params, this);
        } else if (requesType == REQUEST_MODIFY) {
            mGoodsModel.modifyGoods(params, this);
        } else if (requesType == REQUEST_INFO) {
            mGoodsModel.getGoodsInfo(params, this);
        }
    }

    @Override
    public void getCategorySuccess(List<GoodsCategory> list) {
        Integer[] ids = new Integer[list.size()];
        String[] names = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            GoodsCategory bean = list.get(i);
            ids[i] = bean.getCat_id();
            names[i] = bean.getCat_name();
        }
        mView.setCategoryList(ids, names);
    }

    @Override
    public void getGoodsInfoSuccess(Goods bean) {
        mView.showLoadingDialog(false);
        mView.setGoodsInfo(bean);
    }

    @Override
    public void updateInfoSuccess(Goods goods) {
        mView.showLoadingDialog(false);
        mView.showSuccessView(goods);
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showSuccessView(null);
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
        if (requesType == REQUEST_INFO) {
            mView.finishView();
        }
    }

    @Override
    public void onTokenFail() {
        UserManager.cleanToken();
        ((IBaseModel) mGoodsModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }
}
