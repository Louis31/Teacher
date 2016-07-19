package com.haiku.wateroffer.mvp.view.activity;

import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 编辑联系电话Activity
 * Created by hyming on 2016/7/14.
 */
@ContentView(R.layout.act_edit_phone)
public class EditPhoneActivity extends BaseActivity {


    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        // 创建Presenter
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.contact_number, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });
    }
}
