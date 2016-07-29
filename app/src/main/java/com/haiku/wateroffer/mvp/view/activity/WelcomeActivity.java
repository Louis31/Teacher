package com.haiku.wateroffer.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.mvp.base.BaseActivity;

import org.xutils.view.annotation.ContentView;

/**
 * 欢迎界面Activity
 * Created by hyming on 2016/7/5.
 */
@ContentView(R.layout.act_welcome)
public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();
            }
        }, 2500);
    }
}
