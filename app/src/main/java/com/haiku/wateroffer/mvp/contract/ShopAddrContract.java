package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 添加/修改店铺地址Contract
 * Created by hyming on 2016/7/13.
 */
public interface ShopAddrContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 成功回调
        void showSuccessView();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 添加店铺名称
        void addShopAddress(int uid, String area, String floorDetail);
    }
}
