package com.haiku.wateroffer.common.util.ui;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haiku.wateroffer.R;

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
}
