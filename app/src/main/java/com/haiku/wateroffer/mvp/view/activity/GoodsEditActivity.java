package com.haiku.wateroffer.mvp.view.activity;

import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 添加/编辑商品Activity
 * Created by hyming on 2016/7/17.
 */
@ContentView(R.layout.act_goods_edit)
public class GoodsEditActivity extends BaseActivity {

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mTitlebar.initDatas("添加商品", true);
        mTitlebar.showRightTextView(R.string.save);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                super.onRightTextClick();
            }
        });
    }
}
