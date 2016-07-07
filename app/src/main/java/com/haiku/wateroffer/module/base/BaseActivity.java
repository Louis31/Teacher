package com.haiku.wateroffer.module.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.haiku.wateroffer.common.util.ui.ToastUtils;

import org.xutils.x;

/**
 * Activity基类
 * Created by hyming on 2016/7/5.
 */
public class BaseActivity extends Activity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        x.view().inject(this);
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
}
