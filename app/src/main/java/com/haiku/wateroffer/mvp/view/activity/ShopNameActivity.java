package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.contract.ShopNameContract;
import com.haiku.wateroffer.mvp.persenter.ShopNamePresenter;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 添加/修改店铺名称Activity
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_shop_name)
public class ShopNameActivity extends BaseActivity implements ShopNameContract.View {

    private ShopNameContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_shop_name)
    private EditText et_shop_name;

    @Event(R.id.btn_next)
    private void nextClick(View v) {
        String shopName = et_shop_name.getText().toString().trim();
        if (TextUtils.isEmpty(shopName)) {
            ToastUtils.getInstant().showToast(R.string.msg_shop_name_empty);
        } else {
            int uid = UserManager.getInstance().getUser().getUid();
            mPresenter.addShopName(uid, shopName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        new ShopNamePresenter(new ShopModelImpl(), this);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.shop_name, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });
    }

    @Override
    public void setPresenter(@NonNull ShopNameContract.Presenter presenter) {
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

    // 显示添加店铺地址页面
    @Override
    public void showShopAddressActivity() {
        startActivity(new Intent(mContext, ShopAddressActivity.class));
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }
}
