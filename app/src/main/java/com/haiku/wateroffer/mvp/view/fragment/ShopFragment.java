package com.haiku.wateroffer.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.FileUtils;
import com.haiku.wateroffer.common.util.ui.ActivityUtils;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.contract.ShopContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopPresenter;
import com.haiku.wateroffer.mvp.view.activity.ContributionActivity;
import com.haiku.wateroffer.mvp.view.activity.DeliverListActivity;
import com.haiku.wateroffer.mvp.view.activity.DepositActivity;
import com.haiku.wateroffer.mvp.view.activity.LoginActivity;
import com.haiku.wateroffer.mvp.view.activity.MyBillActivity;
import com.haiku.wateroffer.mvp.view.activity.PhoneChangeActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopAddressActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopNameActivity;
import com.haiku.wateroffer.mvp.view.dialog.ActionSheetDialog;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 我的小店Fragment
 * Created by hyming on 2016/7/6.
 */
public class ShopFragment extends LazyFragment implements View.OnClickListener, ShopContract.View {
    private Context mContext;
    private String mImagePath;
    private Uri mImageUri;

    private ShopContract.Presenter mPresenter;
    private SweetAlertDialog mDialog;

    private View rootView;

    private TextView tv_shop_name;// 店铺名称
    private ImageView iv_shop_logo;// 店铺logo

    private View llayout_address;// 店铺地址
    private View llayout_phone;// 店铺电话
    private View llayout_deliver_list;//　配送列表
    private View llayout_my_bill;// 我的订单
    private View llayout_deposit;// 保证金
    private View llayout_contribute;// 商家贡献值
    private View tv_logout;// 注销

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
            new ShopPresenter(new UserModelImpl(), this);
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void setPresenter(@NonNull ShopContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        tv_logout = findView(rootView, R.id.tv_logout);

        iv_shop_logo.setOnClickListener(this);
        tv_shop_name.setOnClickListener(this);
        llayout_address.setOnClickListener(this);
        llayout_phone.setOnClickListener(this);
        llayout_deliver_list.setOnClickListener(this);
        llayout_my_bill.setOnClickListener(this);
        llayout_deposit.setOnClickListener(this);
        llayout_contribute.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
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
                new ActionSheetDialog(mContext).builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("相机拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        doTakePhoto();// 相机拍照
                                    }
                                }).addSheetItem("相册选取", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                doPickPicture();// 相册获取
                            }
                        })
                        .show();
                break;
            // 店铺名称点击
            case R.id.tv_shop_name:
                Intent iShopName = new Intent(mContext, ShopNameActivity.class);
                iShopName.putExtra("isUpdate", true);
                iShopName.putExtra("shop_name", "测试名称");
                startActivityForResult(iShopName, BaseConstant.REQUEST_EDIT_SHOP_NAME);
                break;
            // 跳转到店铺地址界面
            case R.id.llayout_address:
                Intent iShopAddr = new Intent(mContext, ShopAddressActivity.class);
                iShopAddr.putExtra("isUpdate", true);
                startActivity(iShopAddr);
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
            // 注销登录
            case R.id.tv_logout:
                ActivityUtils.cleanActivitys();
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }

    /**
     * 相机拍照
     */
    private void doTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mImagePath);
        // 设置输出路径
        mImageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, BaseConstant.REQUEST_TAKE_PHOTO);
    }

    /**
     * 相册获取
     */
    private void doPickPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, BaseConstant.REQUEST_PICK_PHOTO);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 修改店铺名称
        if (requestCode == BaseConstant.REQUEST_EDIT_SHOP_NAME && resultCode == Activity.RESULT_OK) {
            tv_shop_name.setText(data.getStringExtra("shop_name"));
        }
        // 手机拍照
        else if (requestCode == BaseConstant.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            cropImageUri(mImageUri, 480, 480, BaseConstant.REQUEST_CROP_PICTURE);
        }
        // 相册获取
        else if (requestCode == BaseConstant.REQUEST_PICK_PHOTO) {
            if (data != null && data.getData() != null) {
                mImageUri = data.getData();
                cropImageUri(mImageUri, 480, 480, BaseConstant.REQUEST_CROP_PICTURE);
            }
        }
        // 裁剪图片
        else if (requestCode == BaseConstant.REQUEST_CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = ImageUtils.decodeUriAsBitmap(mContext, mImageUri);
            String base64 = ImageUtils.imgToBase64(bmp);
            if (bmp != null)
                bmp.recycle();
            FileUtils.deleteFile(mImagePath);
            mImagePath = "";
            // 上传图片
            int uid = UserManager.getInstance().getUser().getUid();
            mPresenter.changeShopLogo(uid, base64);
        }
    }

    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = DialogUtils.makeLoadingDialog(mContext, getString(R.string.dlg_upload_image));
            mDialog.show();
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void setLogo() {
        ToastUtils.getInstant().showToast("修改图片成功");
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }
}
