package com.haiku.wateroffer.module.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.model.impl.DepositModelImpl;
import com.haiku.wateroffer.module.base.BaseActivity;
import com.haiku.wateroffer.ui.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 保证金页面
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_deposit)
public class DepositActivity extends BaseActivity implements Titlebar.OnReturnClickListener, DepositContract.View {

    private DepositContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        // 创建Presenter
        new DepositPresenter(new DepositModelImpl(), this);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.deposit, true);
        mTitlebar.setListener(this);
    }

    @Override
    public void setPresenter(@NonNull DepositContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onReturnClick() {
        finish();
    }
}
