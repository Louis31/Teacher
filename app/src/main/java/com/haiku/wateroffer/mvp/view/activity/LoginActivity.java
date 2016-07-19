package com.haiku.wateroffer.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.data.ValidatorUtils;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.contract.LoginContract;
import com.haiku.wateroffer.mvp.persenter.LoginPresenter;
import com.haiku.wateroffer.mvp.base.BaseActivity;

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

    private boolean isGettingVerifyCode;// 标记正在获取验证码
    private LoginContract.Presenter mPresenter;
    private SweetAlertDialog mDialog;

    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    @ViewInject(R.id.et_verify_code)
    private EditText et_verify_code;

    @ViewInject(R.id.tv_verify_code)
    private TextView tv_verify_code;

    // 获取短信验证码
    @Event(R.id.tv_verify_code)
    private void verifyCodeClick(View v) {
        String phone = et_phone.getText().toString().trim();
        // 判断手机号码格式是否正确
        if (!ValidatorUtils.isMobile(phone)) {
            ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
        } else if (!isGettingVerifyCode) {
            isGettingVerifyCode = true;
            tv_verify_code.setTextColor(getResources().getColor(R.color.red));
            mPresenter.getVerifyCode(phone);
        }
    }

    // 登录按钮点击事件
    @Event(R.id.btn_login)
    private void loginClick(View v) {
        String phone = et_phone.getText().toString().trim();
        String valicode = et_verify_code.getText().toString().trim();// 判断手机号码格式是否正确
        if (!ValidatorUtils.isMobile(phone)) {
            ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
        } else if (TextUtils.isEmpty(valicode)) {
            ToastUtils.getInstant().showToast(R.string.msg_verifycode_invalid);
        } else {
            mPresenter.login(phone, valicode);
        }
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

        // startActivity(new Intent(mContext, ShopAddressActivity.class));
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

    @Override
    public void setVerifyCode(String verifyCode) {
        et_verify_code.setText(verifyCode);
        tv_verify_code.setTextColor(getResources().getColor(R.color.black));
        isGettingVerifyCode = false;
    }

    // 显示错误信息
    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
        tv_verify_code.setTextColor(getResources().getColor(R.color.black));
        isGettingVerifyCode = false;
    }
}
