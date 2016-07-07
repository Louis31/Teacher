package com.haiku.wateroffer.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.haiku.onekeydeliveryseller.R;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.model.impl.UserModelImpl;
import com.haiku.wateroffer.module.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 登录界面Activity
 * Created by hyming on 2016/7/5.
 */
@ContentView(R.layout.act_login)
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private SweetAlertDialog mDialog;

    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    @ViewInject(R.id.et_verify_code)
    private EditText et_verify_code;

    // 登录按钮点击事件
    @Event(R.id.btn_login)
    private void loginClick(View v) {
        mPresenter.login();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter
        new LoginPresenter(new UserModelImpl(), this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示主页
    @Override
    public void showMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    // 显示/隐藏加载对话框
    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = DialogUtils.makeLoadingDialog(mContext, getString(R.string.dlg_loging));
            mDialog.show();
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    // 显示错误信息
    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }
}
