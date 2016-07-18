package com.haiku.wateroffer.common.util.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.common.util.data.StringUtils;
import com.haiku.wateroffer.ui.dialog.ActionSheetDialog;
import com.haiku.wateroffer.ui.dialog.ActionSheetDialog.*;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 对话框工具类
 * Created by hyming on 2016/6/17.
 */
public class DialogUtils {
    public final static int TAKE_PHOTO = 1;// 相机照相
    public final static int PICK_PHOTO = 2;// 相册选取

    // 显示加载对话框
    public static SweetAlertDialog makeLoadingDialog(Context cxt, String title) {
        SweetAlertDialog dialog = new SweetAlertDialog(cxt, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#F03B40"));
        dialog.setTitleText(title);
        dialog.setCancelable(false);
        return dialog;
    }

    // 显示日期对话框
    public static void showDateDialog(Context cxt, final TextView tv) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());////为Calendar对象设置时间为当前日期
        int year = calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        final int month = calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        new DatePickerDialog(cxt,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv.setText(StringUtils.formatDate(year, monthOfYear + 1, dayOfMonth));
                    }
                }, year, month, day).show();
    }

    // 显示选择图片对话框
    public static ActionSheetDialog makePhotoPickDialog(final Activity act, final String imagePath) {
        ActionSheetDialog dialog = new ActionSheetDialog(act);
        dialog.builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("相册选取", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                doPickPicture(act);// 相册获取
                            }
                        })
                .addSheetItem("相机拍照", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                doTakePhoto(act, imagePath);// 相机拍照
                            }
                        });
        return dialog;
    }

    /**
     * 相机拍照
     */
    private static void doTakePhoto(Activity act, String imagePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(imagePath);
        // 设置输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        act.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 相册获取
     */
    private static void doPickPicture(Activity act) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        act.startActivityForResult(intent, PICK_PHOTO);
    }


}
