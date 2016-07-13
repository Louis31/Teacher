package com.haiku.wateroffer.module.shop;

import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.module.base.BaseActivity;
import com.haiku.wateroffer.ui.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 贡献值界面
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_contribution)
public class ContributionActivity extends BaseActivity implements Titlebar.OnReturnClickListener {

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }


    private void initViews() {
        mTitlebar.initDatas(R.string.contribution, true);
        mTitlebar.setListener(this);
    }

    @Override
    public void onReturnClick() {
        finish();
    }
}
