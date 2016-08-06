package com.haiku.wateroffer.mvp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.haiku.wateroffer.bean.ShopInfo;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.FileUtils;
import com.haiku.wateroffer.common.util.data.SharedPreferencesUtils;
import com.haiku.wateroffer.common.util.ui.ActivityUtils;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.contract.ShopContract;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopPresenter;
import com.haiku.wateroffer.mvp.view.activity.ContributionActivity;
import com.haiku.wateroffer.mvp.view.activity.DeliverListActivity;
import com.haiku.wateroffer.mvp.view.activity.DeliverRangeActivity;
import com.haiku.wateroffer.mvp.view.activity.DepositActivity;
import com.haiku.wateroffer.mvp.view.activity.LoginActivity;
import com.haiku.wateroffer.mvp.view.activity.MyBillActivity;
import com.haiku.wateroffer.mvp.view.activity.PhoneChangeActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopAddressActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopNameActivity;
import com.haiku.wateroffer.mvp.view.activity.ShopQQActivity;
import com.haiku.wateroffer.mvp.view.dialog.ActionSheetDialog;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;

import java.io.File;

/**
 * 我的小店Fragment
 * Created by hyming on 2016/7/6.
 */
public class ShopFragment extends LazyFragment implements View.OnClickListener, ShopContract.View {
    private Context mContext;
    private int uid;

    private ShopInfo mShopInfo;
    private String mImagePath;
    private Uri mImageUri;

    private ShopContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    private View rootView;

    private TextView tv_shop_name;// 店铺名称
    private ImageView iv_shop_logo;// 店铺logo
    private TextView tv_shop_status;
    private ImageView iv_shop_status;

    private View llayout_address;// 店铺地址
    private View llayout_phone;// 店铺电话
    private View llayout_deliver_range;// 配送范围
    private View llayout_deliver_list;//　配送列表
    private View llayout_my_bill;// 我的订单
    private View llayout_deposit;// 保证金
    private View llayout_contribute;// 商家贡献值
    private View llayout_qq;// 店铺QQ
    private View llayout_open_status;
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
            new ShopPresenter(new ShopModelImpl(), this);
            mPresenter.getShopInfo(uid);
            mPresenter.getShopMarginStatus(uid);
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
        uid = UserManager.getInstance().getUser().getUid();
    }

    // 初始化界面
    private void initViews() {
        tv_shop_name = findView(rootView, R.id.tv_shop_name);
        iv_shop_logo = findView(rootView, R.id.iv_shop_logo);
        tv_shop_status = findView(rootView, R.id.tv_shop_status);
        iv_shop_status = findView(rootView, R.id.iv_shop_status);
        llayout_address = findView(rootView, R.id.llayout_address);
        llayout_phone = findView(rootView, R.id.llayout_phone);
        llayout_deliver_range = findView(rootView, R.id.llayout_deliver_range);
        llayout_deliver_list = findView(rootView, R.id.llayout_deliver_list);
        llayout_my_bill = findView(rootView, R.id.llayout_my_bill);
        llayout_deposit = findView(rootView, R.id.llayout_deposit);
        llayout_contribute = findView(rootView, R.id.llayout_contribute);
        llayout_qq = findView(rootView, R.id.llayout_qq);
        llayout_open_status = findView(rootView, R.id.llayout_open_status);
        tv_logout = findView(rootView, R.id.tv_logout);

        iv_shop_logo.setOnClickListener(this);
        tv_shop_name.setOnClickListener(this);
        llayout_address.setOnClickListener(this);
        llayout_phone.setOnClickListener(this);
        llayout_deliver_range.setOnClickListener(this);
        llayout_deliver_list.setOnClickListener(this);
        llayout_my_bill.setOnClickListener(this);
        llayout_deposit.setOnClickListener(this);
        llayout_contribute.setOnClickListener(this);
        llayout_qq.setOnClickListener(this);
        llayout_open_status.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

        ImageUtils.showCircleImage(getContext(), R.drawable.ic_image_loading, iv_shop_logo);
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
                if (mShopInfo != null) {
                    Intent iShopName = new Intent(mContext, ShopNameActivity.class);
                    iShopName.putExtra("isUpdate", true);
                    iShopName.putExtra("shop_name", mShopInfo.getShopName());
                    startActivityForResult(iShopName, BaseConstant.REQUEST_EDIT_SHOP_NAME);
                }
                break;
            // 跳转到店铺地址界面
            case R.id.llayout_address:
                if (mShopInfo != null) {
                    Intent intent = new Intent(mContext, ShopAddressActivity.class);
                    intent.putExtra("isUpdate", true);
                    intent.putExtra("area", mShopInfo.getArea());
                    intent.putExtra("area_detail", mShopInfo.getFloorDetail());
                    intent.putExtra("latitude", mShopInfo.getLat());
                    intent.putExtra("longitude", mShopInfo.getLng());
                    startActivityForResult(intent, BaseConstant.REQUEST_EDIT_SHOP_ADDR);
                }

                break;
            // 跳转编辑联系电话界面
            case R.id.llayout_phone:
                if (mShopInfo != null) {
                    Intent iPhone = new Intent(mContext, PhoneChangeActivity.class);
                    iPhone.putExtra("phone", mShopInfo.getPhone());
                    startActivityForResult(iPhone, BaseConstant.REQUEST_EDIT_SHOP_PHONE);
                }
                break;
            // 配送范围
            case R.id.llayout_deliver_range:
                if (mShopInfo != null) {
                    Intent intent = new Intent(mContext, DeliverRangeActivity.class);
                    intent.putExtra("area", mShopInfo.getArea());
                    intent.putExtra("range", mShopInfo.getRange());
                    intent.putExtra("latitude", mShopInfo.getLat());
                    intent.putExtra("longitude", mShopInfo.getLng());
                    startActivityForResult(intent, BaseConstant.REQUEST_EDIT_RANGE);
                }

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
                startActivityForResult(new Intent(mContext, DepositActivity.class), BaseConstant.REQUEST_EDIT_DEPOSIT);
                break;
            // 跳转到商家贡献值界面
            case R.id.llayout_contribute:
                startActivity(new Intent(mContext, ContributionActivity.class));
                break;
            // 跳转到QQ页面
            case R.id.llayout_qq:
                if (mShopInfo != null) {
                    Intent iQQ = new Intent(mContext, ShopQQActivity.class);
                    iQQ.putExtra("qq", mShopInfo.getQq());
                    startActivityForResult(iQQ, BaseConstant.REQUEST_EDIT_SHOP_QQ);
                }
                break;
            // 设置营业状态
            case R.id.llayout_open_status:
                if (mShopInfo != null) {
                    if (TypeConstant.ShopStatus.OPEN.equals(mShopInfo.getStatus())) {
                        mPresenter.setShopOpenStatus(uid, TypeConstant.ShopStatus.CLOSE, getString(R.string.dlg_submiting));
                    } else {
                        mPresenter.setShopOpenStatus(uid, TypeConstant.ShopStatus.OPEN, getString(R.string.dlg_submiting));
                    }
                }
                break;
            // 注销登录
            case R.id.tv_logout:
                new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_logout))
                        .setCancelable(false)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferencesUtils.save(SharedPreferencesUtils.USER, "");
                                ActivityUtils.cleanActivitys();
                                startActivity(new Intent(mContext, LoginActivity.class));
                            }
                        }).setNegativeButton("取消", null).show();
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
            String shopName = data.getStringExtra("shop_name");
            mShopInfo.setShopName(shopName);
            tv_shop_name.setText(shopName);
        }
        // 修改店铺电话
        else if (requestCode == BaseConstant.REQUEST_EDIT_SHOP_PHONE && resultCode == Activity.RESULT_OK) {
            mShopInfo.setPhone(data.getStringExtra("phone"));
        }
        // 修改店铺qq
        else if (requestCode == BaseConstant.REQUEST_EDIT_SHOP_QQ && resultCode == Activity.RESULT_OK) {
            mShopInfo.setQq(data.getStringExtra("qq"));
        }
        // 修改店铺地址
        else if (requestCode == BaseConstant.REQUEST_EDIT_SHOP_ADDR && resultCode == Activity.RESULT_OK) {
            String area = data.getStringExtra("area");
            String area_detail = data.getStringExtra("area_detail");
            String lat = data.getStringExtra("latitude");
            String lng = data.getStringExtra("longitude");
            mShopInfo.setLat(lat);
            mShopInfo.setLng(lng);
            mShopInfo.setArea(area);
            mShopInfo.setFloorDetail(area_detail);
        }
        // 修改配送范围
        else if (requestCode == BaseConstant.REQUEST_EDIT_RANGE && resultCode == Activity.RESULT_OK) {
            mShopInfo.setRange(data.getStringExtra("range"));
        }
        // 保证金
        else if (requestCode == BaseConstant.REQUEST_EDIT_DEPOSIT && resultCode == Activity.RESULT_OK) {
            UserManager.getInstance().setIsPayDeposit(true);
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
           /* if (bmp != null)
                bmp.recycle();*/
            FileUtils.deleteFile(mImagePath);
            mImagePath = "";
            // 上传图片
            int uid = UserManager.getInstance().getUser().getUid();
            mPresenter.changeShopLogo(uid, base64, getString(R.string.dlg_upload_image));
        }
    }

    @Override
    public void showLoadingDialog(boolean isShow, String dlgStr) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", dlgStr);
            mDialog.show();
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void setShopInfo(ShopInfo bean) {
        mShopInfo = bean;
        tv_shop_name.setText(bean.getShopName());
        ImageUtils.showCircleImage(getContext(), bean.getIcon(), iv_shop_logo);
        setShopStatus(bean.getStatus());
    }

    @Override
    public void setLogo(String logo) {
        if (mShopInfo != null) {
            mShopInfo.setIcon(logo);
        }
        ImageUtils.showCircleImage(getContext(), logo, iv_shop_logo);
    }

    @Override
    public void setShopStatus(String status) {
        mShopInfo.setStatus(status);
        if (TypeConstant.ShopStatus.OPEN.equals(status)) {
            // 营业中
            tv_shop_status.setText(getString(R.string.shop_status_open));
            iv_shop_status.setImageResource(R.drawable.switch_on);
        } else if (TypeConstant.ShopStatus.CLOSE.equals(status)) {
            // 打烊
            tv_shop_status.setText(getString(R.string.shop_status_close));
            iv_shop_status.setImageResource(R.drawable.switch_off);
        }    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }
}
