package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.GoodsCategory;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 商品编辑模块的Contract类
 * Created by hyming on 2016/7/26
 */
public interface GoodsEditContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        void setImageView(String url);

        void setCategoryList(Integer[] ids, String[] names);

        // 成功回调
        void showSuccessView();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        void uploadImage(String attach);

        void getCategory(int parent_id, int ishome);

        void addGoods(int uid, String product_name, String product_images, int product_price,
                      int product_store, String product_description, String product_category, String product_buyingcycle,
                      int product_personalamount, String product_beyondprice);

        void modifyGoods(int uid, String product_name, String product_images, int product_price,
                         int product_store, String product_description, String product_category, String product_buyingcycle,
                         int product_personalamount, String product_beyondprice);
    }
}
