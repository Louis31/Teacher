package com.haiku.wateroffer.common.util.data;

import android.util.Log;

/**
 * Created by hyming on 2016/6/24.
 */
public class LogUtils {
    public static boolean isShowLog = false;

    public static void showLogI(String TAG, String msg) {
        if (isShowLog)
            Log.i(TAG, msg);
    }

    public static void showLogE(String TAG, String msg) {
        if (isShowLog)
            Log.e(TAG, msg);
    }
}
