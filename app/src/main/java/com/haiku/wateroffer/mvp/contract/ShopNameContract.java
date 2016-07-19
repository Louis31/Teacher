package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 添加/修改店铺名称Contract
 * Created by hyming on 2016/7/13.
 */
public interface ShopNameContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示添加店铺地址页面
        void showShopAddressActivity();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 添加店铺名称
        void addShopName(int uid, String shopName);
    }
}
