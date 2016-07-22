package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.ShopNameContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopNamePresenter;
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

    private boolean isUpdate;
    private String mShopName;
    private ShopNameContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_shop_name)
    private EditText et_shop_name;

    @ViewInject(R.id.btn_next)
    private Button btn_next;

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
        initDatas();
        initViews();
        new ShopNamePresenter(new UserModelImpl(), this);
    }

    private void initDatas() {
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
    }


    private void initViews() {
        mTitlebar.initDatas(R.string.shop_name, true);
        if (isUpdate) {
            btn_next.setVisibility(View.GONE);
            et_shop_name.setText(getIntent().getStringExtra("shop_name"));
            mTitlebar.showRightTextView(getString(R.string.save));
            mTitlebar.setListener(new TitlebarListenerAdapter() {
                @Override
                public void onReturnIconClick() {
                    finish();
                }

                @Override
                public void onRightTextClick() {
                    String shopName = et_shop_name.getText().toString().trim();
                    if (TextUtils.isEmpty(shopName)) {
                        ToastUtils.getInstant().showToast(R.string.msg_shop_name_empty);
                    } else {
                        // 保存修改
                        int uid = UserManager.getInstance().getUser().getUid();
                        mShopName = shopName;
                        mPresenter.addShopName(uid, shopName);
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

    @Override
    public void showSuccessView() {
        if (isUpdate) {
            // 更新成功,返回页面
            Intent data = new Intent();
            data.putExtra("shop_name", mShopName);
            setResult(Activity.RESULT_OK, data);
            finish();
        } else {
            // 显示添加店铺地址页面
            startActivity(new Intent(mContext, ShopAddressActivity.class));
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
        KeyBoardUtils.closeKeybord(et_shop_name, this);
    }
}
