package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 我的店铺Contract
 * Created by hyming on 2016/7/19.
 */
public interface ShopContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        void setShopInfo(ShopInfo bean);

        void setShopStatus(String status);

        // 设置店铺logo
        void setLogo(String logo);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 修改店铺logo
        void changeShopLogo(int uid, String data);

        // 获取店铺信息
        void getShopInfo(int uid);

        // 获取营业状态
        void getShopOpenStatus(int uid);
    }
}
