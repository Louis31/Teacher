package com.haiku.wateroffer.module.order;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.module.base.BaseActivity;
import com.haiku.wateroffer.ui.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 订单详情Activity
 * Created by hyming on 2016/7/15.
 */
@ContentView(R.layout.act_order_detail)
public class OrderDetailActivity extends BaseActivity {
    private final int TYPE_STATUS = 0;
    private final int TYPE_DETAIL = 1;
    private int mType;

    private int colorRed;
    private int colorBlack;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(value = R.id.tv_tab_status)
    private TextView tv_tab_status;

    @ViewInject(value = R.id.tv_tab_detail)
    private TextView tv_tab_detail;

    @ViewInject(value = R.id.flayout_order_detail)
    private FrameLayout flayout_order_detail;

    @ViewInject(value = R.id.flayout_order_status)
    private FrameLayout flayout_order_status;

    @Event(value = R.id.tv_tab_status)
    private void tabStatusClick(View v) {
        changeTabView(TYPE_STATUS);
    }

    @Event(value = R.id.tv_tab_detail)
    private void tabDetailClick(View v) {
        changeTabView(TYPE_DETAIL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        mType = TYPE_STATUS;
        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.order_detail, true);
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
        // 订单状态
        if (type == TYPE_STATUS) {
            tv_tab_status.setTextColor(colorRed);
            tv_tab_detail.setTextColor(colorBlack);
            flayout_order_status.setVisibility(View.VISIBLE);
            flayout_order_detail.setVisibility(View.GONE);
        }
        // 订单详情
        else {
            tv_tab_detail.setTextColor(colorRed);
            tv_tab_status.setTextColor(colorBlack);
            flayout_order_detail.setVisibility(View.VISIBLE);
            flayout_order_status.setVisibility(View.GONE);
        }
    }
}
