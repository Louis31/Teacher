package com.haiku.wateroffer;

import android.app.Application;

import com.haiku.wateroffer.common.util.data.LogUtils;

import org.xutils.x;

/**
 * Created by hyming on 2016/7/5.
 */
public class APPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 开启日志
        LogUtils.isShowLog = true;
        //Xutils初始化
        x.Ext.init(this);
    }
}
