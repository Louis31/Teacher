package com.haiku.wateroffer.module.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.module.base.LazyFragment;

/**
 * 我的小店Fragment
 * Created by hyming on 2016/7/6.
 */
public class ShopFragment extends LazyFragment implements View.OnClickListener {
    private Context mContext;
    private String mImagePath;

    private View rootView;
    private ImageView iv_shop_logo;// 店铺logo

    private LinearLayout llayout_phone;// 店铺电话
    private LinearLayout llayout_deposit;// 保证金

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_shop, container, false);
            initDatas();
            initViews();
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    // 初始化数据
    private void initDatas() {

    }

    // 初始化界面
    private void initViews() {
        iv_shop_logo = findView(rootView, R.id.iv_shop_logo);
        llayout_phone = findView(rootView, R.id.llayout_phone);
        llayout_deposit = findView(rootView, R.id.llayout_deposit);

        iv_shop_logo.setOnClickListener(this);
        llayout_phone.setOnClickListener(this);
        llayout_deposit.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 店铺LOGO点击
            case R.id.iv_shop_logo:
                mImagePath = mContext.getExternalCacheDir()
                        .getAbsolutePath()
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                DialogUtils.makePhotoPickDialog(getActivity(), mImagePath).show();
                break;
            // 跳转编辑联系电话界面
            case R.id.llayout_phone:
                break;
            // 跳转到保证金页面
            case R.id.llayout_deposit:
                startActivity(new Intent(mContext, DepositActivity.class));
                break;
        }
    }
}
