package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

/**
 * 登录模块Contract
 * Created by hyming on 2016/7/6.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showSuccessView();

        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        void resetVerifyCodeView();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 登录操作
        void login(String phone, String valicode);

        // 获取短信验证操作
        void getVerifyCode(String phone);
    }
}
