package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 添加/修改店铺QQContract
 * Created by hyming on 2016/7/25.
 */
public interface ShopQQContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 成功回调
        void showSuccessView();

        void setShopQQ(String qq);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 添加/修改qq
        void changeQQNumber(int id, String qq);

        // 获取qq
        void getQQNumber(int id);
    }
}
