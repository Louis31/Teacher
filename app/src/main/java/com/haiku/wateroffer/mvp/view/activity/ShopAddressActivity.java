package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.ShopAddrContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopAddrPresenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 店铺地址Activity
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_shop_address)
public class ShopAddressActivity extends BaseActivity implements ShopAddrContract.View {

    private boolean isUpdate;
    private ShopAddrContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_address)
    private EditText et_address;

    @ViewInject(R.id.tv_address)
    private TextView tv_address;

    @Event(R.id.iv_location)
    private void onLocationClick(View v) {
        startActivity(new Intent(mContext, AddressActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        new ShopAddrPresenter(new UserModelImpl(), this);
    }

    private void initDatas() {
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.shop_address, true);
        if (isUpdate) {
            mTitlebar.showRightTextView(getString(R.string.save));
            mTitlebar.setListener(new TitlebarListenerAdapter() {
                @Override
                public void onReturnIconClick() {
                    finish();
                }

                @Override
                public void onRightTextClick() {
                    String area = tv_address.getText().toString();
                    String detail = et_address.getText().toString().trim();

                    if (TextUtils.isEmpty(area) || TextUtils.isEmpty(detail)) {
                        ToastUtils.getInstant().showToast(R.string.msg_addr_invalid);
                    } else {

                    }
                }
            });
        } else {
            mTitlebar.setListener(new TitlebarListenerAdapter() {
                @Override
                public void onReturnIconClick() {
                    finish();
                }
            });
        }
    }

    @Override
    public void setPresenter(@NonNull ShopAddrContract.Presenter presenter) {
        this.mPresenter = presenter;
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

    @Override
    public void showSuccessView() {
        if (isUpdate) {
            // 更新成功,返回页面
            finish();
        } else {
            // TODO 添加店铺地址成功
        }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeybord();
        return super.onTouchEvent(event);
    }

    // 关闭keybord
    public void closeKeybord() {
        KeyBoardUtils.closeKeybord(et_address, this);
    }
}
