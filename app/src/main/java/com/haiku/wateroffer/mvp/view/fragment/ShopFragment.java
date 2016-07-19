package com.haiku.wateroffer.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.view.activity.ContributionActivity;
import com.haiku.wateroffer.mvp.view.activity.DeliverListActivity;
import com.haiku.wateroffer.mvp.view.activity.DepositActivity;
import com.haiku.wateroffer.mvp.view.activity.PhoneChangeActivity;
import com.haiku.wateroffer.mvp.view.activity.MyBillActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopAddressActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopNameActivity;

/**
 * 我的小店Fragment
 * Created by hyming on 2016/7/6.
 */
public class ShopFragment extends LazyFragment implements View.OnClickListener {
    private final int REQUEST_EDIT_NAME = 1;

    private Context mContext;
    private String mImagePath;

    private View rootView;

    private TextView tv_shop_name;// 店铺名称
    private ImageView iv_shop_logo;// 店铺logo

    private View llayout_address;// 店铺地址
    private View llayout_phone;// 店铺电话
    private View llayout_deliver_list;//　配送列表
    private View llayout_my_bill;// 我的订单
    private View llayout_deposit;// 保证金
    private View llayout_contribute;// 商家贡献值

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
        tv_shop_name = findView(rootView, R.id.tv_shop_name);
        iv_shop_logo = findView(rootView, R.id.iv_shop_logo);
        llayout_address = findView(rootView, R.id.llayout_address);
        llayout_phone = findView(rootView, R.id.llayout_phone);
        llayout_deliver_list = findView(rootView, R.id.llayout_deliver_list);
        llayout_my_bill = findView(rootView, R.id.llayout_my_bill);
        llayout_deposit = findView(rootView, R.id.llayout_deposit);
        llayout_contribute = findView(rootView, R.id.llayout_contribute);

        iv_shop_logo.setOnClickListener(this);
        tv_shop_name.setOnClickListener(this);
        llayout_address.setOnClickListener(this);
        llayout_phone.setOnClickListener(this);
        llayout_deliver_list.setOnClickListener(this);
        llayout_my_bill.setOnClickListener(this);
        llayout_deposit.setOnClickListener(this);
        llayout_contribute.setOnClickListener(this);
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
            // 店铺名称点击
            case R.id.tv_shop_name:
                Intent intent = new Intent(mContext, ShopNameActivity.class);
                intent.putExtra("isUpdate", true);
                intent.putExtra("shop_name", "测试名称");
                startActivityForResult(intent, REQUEST_EDIT_NAME);
                break;
            // 跳转到店铺地址界面
            case R.id.llayout_address:
                startActivity(new Intent(mContext, ShopAddressActivity.class));
                break;
            // 跳转编辑联系电话界面
            case R.id.llayout_phone:
                startActivity(new Intent(mContext, PhoneChangeActivity.class));
                break;
            // 配送列表
            case R.id.llayout_deliver_list:
                startActivity(new Intent(mContext, DeliverListActivity.class));
                break;
            // 我的账单
            case R.id.llayout_my_bill:
                startActivity(new Intent(mContext, MyBillActivity.class));
                break;
            // 跳转到保证金页面
            case R.id.llayout_deposit:
                startActivity(new Intent(mContext, DepositActivity.class));
                break;
            // 跳转到商家贡献值界面
            case R.id.llayout_contribute:
                startActivity(new Intent(mContext, ContributionActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_NAME && resultCode == Activity.RESULT_OK) {
            tv_shop_name.setText(data.getStringExtra("shop_name"));
        }
    }
}
