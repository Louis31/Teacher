package com.haiku.wateroffer.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ActivityUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.view.fragment.GoodsFragment;
import com.haiku.wateroffer.mvp.view.fragment.OrderFragment;
import com.haiku.wateroffer.mvp.view.fragment.ShopFragment;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 主界面Activity
 * Created by hyming on 2016/7/5.
 */
@ContentView(R.layout.act_main)
public class MainActivity extends FragmentActivity {
    private Context mContext;
    private String tabTexts[] = {"订单", "商品", "我的"};
    private int tabImages[] = {R.drawable.ic_back, R.drawable.ic_back, R.drawable.ic_back};
    private Class fragments[] = {OrderFragment.class, GoodsFragment.class, ShopFragment.class};

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityUtils.add(this);
        x.view().inject(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtils.getInstant().init(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtils.getInstant().destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.remove(this);
    }

    // 初始化界面
    private void initViews() {
        mTitlebar.initDatas(getString(R.string.title_order), false);
        mTitlebar.initAddIcon();
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onAddIconClick() {
                startActivity(new Intent(mContext, GoodsEditActivity.class));
            }
        });
        mTabHost.setup(this, getSupportFragmentManager(),
                R.id.main_content);
        for (int i = 0; i < tabTexts.length; i++) {
            TabSpec spec = mTabHost.newTabSpec(tabTexts[i]).setIndicator(getView(i));
            mTabHost.addTab(spec, fragments[i], null);
        }

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                tagChanged(tabId);
            }
        });
    }

    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.item_tab, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);
        //设置标题
        textView.setText(tabTexts[i]);
        //设置图标
        imageView.setImageResource(tabImages[i]);
        return view;
    }

    private void tagChanged(String tabId) {
        if (tabTexts[0].equals(tabId)) {
            mTitlebar.setTitle(R.string.title_order);
            mTitlebar.showAddIcon(false);
        } else if (tabTexts[1].equals(tabId)) {
            mTitlebar.setTitle(R.string.title_goods);
            mTitlebar.showAddIcon(true);
        } else if (tabTexts[2].equals(tabId)) {
            mTitlebar.setTitle(R.string.title_shop);
            mTitlebar.showAddIcon(false);
        }
    }
}
