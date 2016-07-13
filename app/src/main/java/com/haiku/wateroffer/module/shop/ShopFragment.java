package com.haiku.wateroffer.module.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.module.base.LazyFragment;

/**
 * 我的小店Fragment
 * Created by hyming on 2016/7/6.
 */
public class ShopFragment extends LazyFragment implements View.OnClickListener {
    private Context mContext;

    private View rootView;
    private LinearLayout llayout_deposit;

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
        llayout_deposit = findView(rootView, R.id.llayout_deposit);
        llayout_deposit.setOnClickListener(this);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 跳转到保证金页面
            case R.id.llayout_deposit:
                startActivity(new Intent(mContext, DepositActivity.class));
                break;
        }
    }
}
