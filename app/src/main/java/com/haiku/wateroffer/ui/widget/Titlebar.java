package com.haiku.wateroffer.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiku.wateroffer.R;


/**
 * 标题栏
 * Created by hyming on 2016/7/5.
 */
public class Titlebar extends FrameLayout {
    private TextView tv_title;
    private ImageView iv_back;
    private OnReturnClickListener mListener;

    public interface OnReturnClickListener {
        // 退出当前activity
        void onReturnClick();
    }

    public void setListener(OnReturnClickListener listener) {
        this.mListener = listener;
    }

    public Titlebar(Context context) {
        this(context, null);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        initViews();
    }

    // 初始化界面
    private void initViews() {
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    // 设置数据
    public void initDatas(Object title, boolean isShowBackIcon) {
        if (title instanceof Integer) {
            tv_title.setText((int) title);
        } else if (title instanceof String) {
            tv_title.setText((String) title);
        }

        if (!isShowBackIcon) {
            iv_back.setVisibility(View.INVISIBLE);
        } else {
            iv_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onReturnClick();
                    }
                }
            });
        }
    }

    // 设置标题
    public void setTitle(int resId) {
        tv_title.setText(resId);
    }
}
