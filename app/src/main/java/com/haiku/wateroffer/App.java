package com.haiku.wateroffer;

import android.app.Application;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyming on 2016/7/5.
 */
public class App extends Application {
    private static App instance;
    private static Map<Integer, String> mErrors;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 开启日志
        LogUtils.isShowLog = false;
        //Xutils初始化
        x.Ext.init(this);

        XmlResourceParser xrp = getResources().getXml(R.xml.error);
        try {
            mErrors = StringUtils.parserErrorXml(xrp);
        } catch (Exception e) {
            Log.e("App", "parser error.xml error");
        }
    }

    public static App getInstance() {
        return instance;
    }

    public static String getErrorMsg(int code) {
        String errorMsg = mErrors.get(code);
        LogUtils.showLogE("ErrorCode", "code = " + code + " - msg = " + errorMsg);
        return errorMsg;
    }
}
