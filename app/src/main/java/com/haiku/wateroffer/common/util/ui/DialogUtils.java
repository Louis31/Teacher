package com.haiku.wateroffer.common.util.ui;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 对话框工具类
 * Created by hyming on 2016/6/17.
 */
public class DialogUtils {

    // 显示加载对话框
    public static SweetAlertDialog makeLoadingDialog(Context cxt, String title) {
        SweetAlertDialog dialog = new SweetAlertDialog(cxt, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#F03B40"));
        dialog.setTitleText(title);
        dialog.setCancelable(false);
        return dialog;
    }

}
