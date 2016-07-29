package com.haiku.wateroffer.common.util.ui;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理工具类
 * Created by hyming on 2016/6/14.
 */
public class ActivityUtils {
    private static List<Activity> mActivitys = new ArrayList<Activity>();

    // 添加Activity
    public static void add(Activity act) {
        mActivitys.add(act);
    }

    // 关闭Activity
    public static void remove(Activity act) {
        mActivitys.remove(act);
        //act.finish();
    }

    // 清除所有Activity
    public static void cleanActivitys() {
        for (int i = 0; i < mActivitys.size(); i++) {
            if (mActivitys.get(i) != null) {
                mActivitys.get(i).finish();
            }
        }
        mActivitys.clear();
    }

    // 退出所有Activity
    public static void exitClient() {
        for (int i = 0; i < mActivitys.size(); i++) {
            if (mActivitys.get(i) != null) {
                mActivitys.get(i).finish();
            }
        }
        mActivitys.clear();
       /* ActivityManager activityMgr = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(ctx.getPackageName());*/
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}
