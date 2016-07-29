package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 修改联系电话Contract
 * Created by hyming on 2016/7/19.
 */
public interface PhoneChangeContract {

    interface View extends BaseView<Presenter> {
        // 返回店铺界面
        void showShopView();

        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示信息
        void showMessage(String msg);

        void resetVerifyCodeView();
    }

    interface Presenter extends BasePresenter {
        // 登录操作
        void changePhone(int uid, String old_phone, String phone, String code);

        // 获取短信验证操作
        void getVerifyCode(String phone);
    }
}
