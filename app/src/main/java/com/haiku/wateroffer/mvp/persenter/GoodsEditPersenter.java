package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.GoodsCategory;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.contract.GoodsEditContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IGoodsModel;

import java.util.ArrayList;
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
    private int requesType;

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
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.uploadGoodsImage(params, this);
        }
    }

    @Override
    public void getCategory(int parent_id, int ishome) {
        requesType = REQUEST_CATEGORY;
        Map<String, Object> params = new HashMap<>();
        params.put("parent_id", parent_id);
        params.put("ishome", ishome);
        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.getGoodsCategory(params, this);
        }
    }

    @Override
    public void addGoods(int uid, String product_name, String product_images, int product_price, int product_store,
                         String product_description, String product_category, String product_buyingcycle, int product_personalamount,
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

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.addGoods(params, this);
        }
    }

    @Override
    public void modifyGoods(int uid, String product_name, String product_images, int product_price, int product_store,
                            String product_description, String product_category, String product_buyingcycle,
                            int product_personalamount, String product_beyondprice) {
        mView.showLoadingDialog(true);
        requesType = REQUEST_MODIFY;
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

        if (UserManager.isTokenEmpty()) {
            ((IBaseModel) mGoodsModel).getAccessToken(params, this);
        } else {
            mGoodsModel.modifyGoods(params, this);
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
        }
    }

    @Override
    public void getCategorySuccess(List<GoodsCategory> list) {
        List<String> category = new ArrayList<>();
        for (GoodsCategory bean : list) {
            category.add(bean.getCat_name());
        }
        mView.setCategoryList(category);
    }

    // 成功回调
    @Override
    public void onSuccess() {
        mView.showLoadingDialog(false);
        mView.showSuccessView();
    }

    // 错误回调
    @Override
    public void onError(int errorCode, String errorMsg) {
        mView.showLoadingDialog(false);
        mView.showMessage(errorMsg);
    }
}
