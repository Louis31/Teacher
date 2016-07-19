package com.haiku.wateroffer.common.util.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haiku.wateroffer.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 图片工具类
 * Created by hyming on 2016/7/14.
 */
public class ImageUtils {

    private final static int LOADING_IMG = R.drawable.ic_image_loading;// 正在加载图片
    private final static int ERROR_IMG = R.drawable.ic_image_loading;// 错误图片

    // 加载图片
    public static void showImage(Fragment fragment, String url, ImageView iv) {
        Glide.with(fragment).load(url).placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(iv);
    }

    // 加载图片
    public static void showImage(Activity activity, String url, ImageView iv) {
        Glide.with(activity).load(url).placeholder(LOADING_IMG)
                .error(ERROR_IMG).into(iv);
    }

    /**
     * 图片转Base64
     *
     * @param bitmap
     * @return
     */
    public static String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            //bitmap not found!!
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            //30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap decodeUriAsBitmap(Context cxt, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cxt.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
