package com.haiku.wateroffer.module.common;

import com.haiku.wateroffer.module.base.BasePresenter;
import com.haiku.wateroffer.module.base.BaseView;

/**
 * 登录模块的Contract类
 * Created by hyming on 2016/7/6.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        // 显示主页
        void showMainActivity();

        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示信息
        void showMessage(String msg);

    }

    interface Presenter extends BasePresenter {
        // 登录操作
        void login();

        // 获取短信验证操作
        void getVerifyCode();
    }
}
