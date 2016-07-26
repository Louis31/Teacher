package com.haiku.wateroffer.mvp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.ui.ImageUtils;

/**
 * Created by hyming on 2016/7/26.
 */
public class UploadImageView extends FrameLayout {
    private Context mContext;
    private ImageView iv_image;
    private ImageView iv_error;

    public interface UploadImageViewListener {
        void onRemoveImageClick(UploadImageView v);
    }

    public UploadImageView(Context context) {
        this(context, null);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_upload_image, this);
        initViews();
    }

    private void initViews() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_error = (ImageView) findViewById(R.id.iv_error);

    }

    public void setImage(String url) {
        ImageUtils.showImage(mContext, url, iv_image);
    }

    public void setOnRemoveListener(final UploadImageViewListener listener) {
        iv_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemoveImageClick(UploadImageView.this);
            }
        });
    }
}
