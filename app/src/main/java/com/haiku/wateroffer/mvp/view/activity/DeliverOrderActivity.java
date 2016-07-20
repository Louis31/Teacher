package com.haiku.wateroffer.mvp.view.activity;

import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 配送员订单列表界面
 * Created by hyming on 2016/7/20.
 */
@ContentView(R.layout.act_common_list)
public class DeliverOrderActivity extends BaseActivity {
    private List<OrderItem> mDatas;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {

    }

    private void initViews() {
        String name = getIntent().getStringExtra("deliver_name");
        mTitlebar.initDatas(name + "配送列表", true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });
    }
}
