package com.haiku.wateroffer.common.util.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by hyming on 2016/6/14.
 */
public class ToastUtils {
    private static ToastUtils _instant;
    private Toast toast;

    public static ToastUtils getInstant() {
        if (_instant == null) {
            synchronized (ToastUtils.class) {
                if (_instant == null) {
                    _instant = new ToastUtils();
                }
            }
        }
        return _instant;
    }

    // 初始化方法
    public void init(Context cxt) {
        toast = Toast.makeText(cxt, "", Toast.LENGTH_SHORT);
    }

    public void showToast(int resId) {
        if (toast != null) {
            toast.setText(resId);
            toast.show();
        }

    }

    public void showToast(String str) {
        if (toast != null) {
            toast.setText(str);
            toast.show();
        }
    }

    // 销毁方法
    public void destroy() {
        if (toast != null)
            toast.cancel();
        toast = null;
    }
}
