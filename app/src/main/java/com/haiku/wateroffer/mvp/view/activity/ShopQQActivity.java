package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.ShopQQContract;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopQQPresenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 添加/修改店铺QQActivity
 * Created by hyming on 2016/7/25.
 */
@ContentView(R.layout.act_shop_qq)
public class ShopQQActivity extends BaseActivity implements ShopQQContract.View {

    private int uid;
    private ProgressDialog mDialog;
    private ShopQQContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_shop_qq)
    private EditText et_shop_qq;

    //@ViewInject(R.id.progressBar)
    //private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        new ShopQQPresenter(new ShopModelImpl(), this);
        //mPresenter.getQQNumber(uid);
    }

    @Override
    public void setPresenter(@NonNull ShopQQContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.shop_qq, true);
        mTitlebar.showRightTextView(getString(R.string.save));
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                String qq = et_shop_qq.getText().toString().trim();
                if (TextUtils.isEmpty(qq)) {
                    ToastUtils.getInstant().showToast(R.string.msg_shop_qq_empty);
                } else {
                    // 保存修改
                    mPresenter.changeQQNumber(uid, qq);
                }
            }
        });

        et_shop_qq.setText(getIntent().getStringExtra("qq"));
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
    public void setShopQQ(String qq) {
        //progressBar.setVisibility(View.GONE);
        et_shop_qq.setText(qq);
    }

    @Override
    public void showSuccessView() {
        Intent data = new Intent();
        data.putExtra("qq", et_shop_qq.getText().toString().trim());
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void showMessage(String msg) {
        //progressBar.setVisibility(View.GONE);
        ToastUtils.getInstant().showToast(msg);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeybord();
        return super.onTouchEvent(event);
    }

    // 关闭keybord
    public void closeKeybord() {
        KeyBoardUtils.closeKeybord(et_shop_qq, this);
    }
}
