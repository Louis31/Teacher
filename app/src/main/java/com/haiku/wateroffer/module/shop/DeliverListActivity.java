package com.haiku.wateroffer.module.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.module.base.BaseActivity;
import com.haiku.wateroffer.ui.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 配送列表Activity
 * Created by hyming on 2016/7/16.
 */
@ContentView(R.layout.act_deliver_list)
public class DeliverListActivity extends BaseActivity {
    private final int TYPE_DELIVER = 0;
    private final int TYPE_EDIT = 1;
    private int mType;

    private int colorRed;
    private int colorBlack;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(value = R.id.tv_tab_deliver)
    private TextView tv_tab_deliver;

    @ViewInject(R.id.tv_tab_edit)
    private TextView tv_tab_edit;

    @Event(R.id.tv_tab_deliver)
    private void tabDeliverClick(View v) {
        changeTabView(TYPE_DELIVER);
    }

    @Event(R.id.tv_tab_edit)
    private void tabEditClick(View v) {
        changeTabView(TYPE_EDIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        mType = TYPE_DELIVER;
        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.delivery_list, true);
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
        if (type == TYPE_DELIVER) {
            tv_tab_deliver.setTextColor(colorRed);
            tv_tab_edit.setTextColor(colorBlack);
        }
        // 订单详情
        else {
            tv_tab_edit.setTextColor(colorRed);
            tv_tab_deliver.setTextColor(colorBlack);
        }
        mType = type;
    }
}
