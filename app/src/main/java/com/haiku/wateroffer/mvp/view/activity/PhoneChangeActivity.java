package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.data.ValidatorUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.PhoneChangeContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.PhoneChangePresenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 编辑联系电话Activity
 * Created by hyming on 2016/7/14.
 */
@ContentView(R.layout.act_phone_change)
public class PhoneChangeActivity extends BaseActivity implements PhoneChangeContract.View {

    private boolean isGettingVerifyCode;// 标记正在获取验证码
    private PhoneChangeContract.Presenter mPresenter;

    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_phone_old)
    private EditText et_phone_old;

    @ViewInject(R.id.et_phone_new)
    private EditText et_phone_new;

    @ViewInject(R.id.et_verify_code)
    private EditText et_verify_code;

    @ViewInject(R.id.tv_verify_code)
    private TextView tv_verify_code;

    // 获取短信验证码
    @Event(R.id.tv_verify_code)
    private void verifyCodeClick(View v) {
        String phone = et_phone_old.getText().toString().trim();
        // 判断手机号码格式是否正确
        if (!ValidatorUtils.isMobile(phone)) {
            ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
        } else if (!isGettingVerifyCode) {
            isGettingVerifyCode = true;
            tv_verify_code.setTextColor(getResources().getColor(R.color.red));
            mPresenter.getVerifyCode(phone);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        // 创建Presenter
        new PhoneChangePresenter(new UserModelImpl(), this);
    }

    @Override
    public void setPresenter(@NonNull PhoneChangeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.contact_number, true);
        mTitlebar.showRightTextView(getString(R.string.save));
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                // 保存修改
                // 判断输入的数据是否正确
                String phone = et_phone_new.getText().toString().trim();
                String valicode = et_verify_code.getText().toString().trim();// 判断手机号码格式是否正确
                if (!ValidatorUtils.isMobile(phone)) {
                    ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
                } else if (TextUtils.isEmpty(valicode)) {
                    ToastUtils.getInstant().showToast(R.string.msg_verifycode_invalid);
                } else {
                    int uid = UserManager.getInstance().getUser().getUid();
                    mPresenter.changePhone(uid, phone, valicode);
                }
            }
        });
    }

    // 显示/隐藏加载对话框
    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_submiting));
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    // 设置verifyCode
    @Override
    public void setVerifyCode(String verifyCode) {
        et_verify_code.setText(verifyCode);
        tv_verify_code.setTextColor(getResources().getColor(R.color.black));
        isGettingVerifyCode = false;
    }

    // 显示信息
    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
        tv_verify_code.setTextColor(getResources().getColor(R.color.black));
        isGettingVerifyCode = false;
    }

    // 返回店铺界面
    @Override
    public void showShopView() {
        finish();
    }
}
