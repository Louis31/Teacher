package com.haiku.wateroffer.module.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.module.base.BaseActivity;
import com.haiku.wateroffer.ui.widget.MyRefreshLayout;
import com.haiku.wateroffer.ui.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的账单Activity
 * Created by hyming on 2016/7/15.
 */
@ContentView(R.layout.act_my_bill)
public class MyBillActivity extends BaseActivity {
    private final int TYPE_DETAIL = 0;
    private final int TYPE_OVERVIEW = 1;
    private int mType;

    private int colorRed;
    private int colorBlack;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.tv_tab_detail)
    private TextView tv_tab_detail;

    @ViewInject(R.id.tv_tab_overview)
    private TextView tv_tab_overview;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout myRefreshLayout;

    @ViewInject(R.id.llayout_bill_overview)
    private View llayout_bill_overview;

    @Event(R.id.tv_tab_detail)
    private void tabDetailClick(View v) {
        changeTabView(TYPE_DETAIL);
    }

    @Event(R.id.tv_tab_overview)
    private void tabOverViewClick(View v) {
        changeTabView(TYPE_OVERVIEW);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        mType = TYPE_DETAIL;
        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.my_bill, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });
    }

    // 改变当前显示界面
    private void changeTabView(int type) {
        if (mType == type) {
            return;
        }
        // 账单明细
        if (type == TYPE_DETAIL) {
            tv_tab_detail.setTextColor(colorRed);
            tv_tab_overview.setTextColor(colorBlack);
            myRefreshLayout.setVisibility(View.VISIBLE);
            llayout_bill_overview.setVisibility(View.GONE);
        }
        // 账单概括
        else {
            tv_tab_overview.setTextColor(colorRed);
            tv_tab_detail.setTextColor(colorBlack);
            llayout_bill_overview.setVisibility(View.VISIBLE);
            myRefreshLayout.setVisibility(View.GONE);
        }
        mType = type;
    }
}
