package com.haiku.wateroffer.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.SharedPreferencesUtils;
import com.haiku.wateroffer.common.util.data.ValidatorUtils;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.LoginContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.LoginPresenter;

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
            closeKeybord();
            mPresenter.getVerifyCode(phone);
        }
    }

    // 登录按钮点击事件
    @Event(R.id.btn_login)
    private void loginClick(View v) {
        /*User user = new User();
        UserManager.getInstance().setUser(user);
        // TODO 设置默认uid
        UserManager.getInstance().getUser().setUid(1431);
        UserManager.getInstance().getUser().setUsertype("old");
        AccessToken token = new AccessToken();
        token.setAccess_token("86f3a89a7f66d69f2bc078360c5b58d293d3d290");

        UserManager.getInstance().setToken(token);
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
        */

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
        // 判断用户是否已经登陆过
        String userStr = SharedPreferencesUtils.load(SharedPreferencesUtils.USER);
        if (!TextUtils.isEmpty(userStr)) {
            UserManager.getInstance().setUser(GsonUtils.gsonToBean(userStr, User.class));
            showSuccessView();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    // 显示主页
    @Override
    public void showSuccessView() {
        // TODO 判断是否为新用户
        if (UserManager.getInstance().getUser().isNew()) {
            startActivity(new Intent(mContext, ShopNameActivity.class));
        } else {
            startActivity(new Intent(mContext, MainActivity.class));
        }
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
    public void resetVerifyCodeView() {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeybord();
        return super.onTouchEvent(event);
    }

    // 关闭keybord
    public void closeKeybord() {
        KeyBoardUtils.closeKeybord(et_phone, this);
        KeyBoardUtils.closeKeybord(et_verify_code, this);
    }
}
