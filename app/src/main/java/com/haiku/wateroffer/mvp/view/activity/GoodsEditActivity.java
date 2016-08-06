package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.GoodsImage;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.data.FileUtils;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.ActionConstant;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.GoodsEditContract;
import com.haiku.wateroffer.mvp.model.impl.GoodsModelImpl;
import com.haiku.wateroffer.mvp.persenter.GoodsEditPersenter;
import com.haiku.wateroffer.mvp.view.dialog.ActionSheetDialog;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
import com.haiku.wateroffer.mvp.view.widget.FlowLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;
import com.haiku.wateroffer.mvp.view.widget.UploadImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加/编辑商品Activity
 * Created by hyming on 2016/7/17.
 */
@ContentView(R.layout.act_goods_edit)
public class GoodsEditActivity extends BaseActivity implements GoodsEditContract.View {
    private String product_id;
    private boolean isUpdate;// 当前是否为编辑界面
    private String mImagePath;
    private String mDialogStr;
    private List<String> mImageUrlList;
    //private List<String> mCategoryList;
    private Integer[] categoryIds = new Integer[]{};
    private String[] categoryNames = new String[]{};
    private GoodsEditContract.Presenter mPresenter;

    private AlertDialog mChoseDateDialog;
    private AlertDialog mChoseCategoryDialog;
    private IOSAlertDialog mOverDialog;
    private IOSAlertDialog mLimitDialog;
    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_goods_price)
    private EditText et_goods_price;

    @ViewInject(R.id.et_goods_stock)
    private EditText et_goods_stock;

    @ViewInject(R.id.et_goods_name)
    private EditText et_goods_name;

    @ViewInject(R.id.et_goods_describe)
    private EditText et_goods_describe;

    @ViewInject(R.id.tv_limit_count)
    private TextView tv_limit_count;

    @ViewInject(R.id.tv_overrange)
    private TextView tv_overrange;

    @ViewInject(R.id.tv_date_limit)
    private TextView tv_date_limit;

    @ViewInject(R.id.tv_category)
    private TextView tv_category;

    @ViewInject(R.id.flayout_image)
    private FlowLayout flayout_image;

    // 添加商品图片
    private void addImageClick() {
        if (mImageUrlList.size() >= 6) {
            return;
        }
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
    }

    // 每天点击
    @Event(R.id.rlayout_person)
    private void personClick(View v) {
        if (mChoseDateDialog == null) {
            Builder builder = new Builder(this);
            builder.setTitle("购买周期");
            final ChoiceOnClickListener choiceListener =
                    new ChoiceOnClickListener();
            builder.setSingleChoiceItems(R.array.date_cycle, 0, choiceListener);

            DialogInterface.OnClickListener btnListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            int choiceWhich = choiceListener.getWhich();
                            String hobbyStr =
                                    getResources().getStringArray(R.array.date_cycle)[choiceWhich];
                            tv_date_limit.setTag(choiceWhich + 1);
                            tv_date_limit.setText(hobbyStr);
                        }
                    };
            builder.setPositiveButton("确定", btnListener);
            mChoseDateDialog = builder.create();
        }
        mChoseDateDialog.show();
    }

    // 限购点击
    @Event(R.id.rlayout_limit)
    private void limitClick(View v) {
        if (mLimitDialog == null) {
            String content = tv_limit_count.getText().toString().replace("件", "");
            mLimitDialog = new IOSAlertDialog(mContext).builder();
            mLimitDialog.setTitle(getString(R.string.dlg_limit_title)).setMsg(getString(R.string.dlg_limit_tip))
                    .setInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL)
                    .setInputContent(content)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.confirm),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 获取到输入数据
                                    String value = mLimitDialog.getInputValue();
                                    if (!TextUtils.isEmpty(value))
                                        tv_limit_count.setText(value + "件");
                                    mLimitDialog.closeKeyBoard(mContext);
                                    closeKeybord();
                                }
                            })
                    .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            closeKeybord();
                        }
                    });
        }
        mLimitDialog.show();
    }

    // 超额点击
    @Event(R.id.rlayout_overrange)
    private void overrangeClick(View v) {
        if (mOverDialog == null) {
            String content = tv_overrange.getText().toString().replace("元", "");
            mOverDialog = new IOSAlertDialog(mContext).builder();
            mOverDialog.setTitle(getString(R.string.dlg_overrange_title)).setMsg(getString(R.string.dlg_overrange_tip))
                    .setInput(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .setInputContent(content)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.confirm),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 获取到输入数据
                                    String value = mOverDialog.getInputValue();
                                    if (!TextUtils.isEmpty(value))
                                        tv_overrange.setText(value + "元");
                                    mOverDialog.closeKeyBoard(mContext);
                                    closeKeybord();
                                }
                            })
                    .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOverDialog.closeKeyBoard(mContext);
                            closeKeybord();
                        }
                    });
        }
        mOverDialog.show();
    }

    // 分类点击
    @Event(R.id.rlayout_classify)
    private void categoryClick(View v) {
        if (mChoseCategoryDialog == null) {
            Builder builder = new Builder(this);
            builder.setTitle("选择分类");
            final ChoiceOnClickListener choiceListener =
                    new ChoiceOnClickListener();
            builder.setSingleChoiceItems(categoryNames, 0, choiceListener);

            DialogInterface.OnClickListener btnListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            int choiceWhich = choiceListener.getWhich();
                            tv_category.setTag(categoryIds[choiceWhich]);
                            tv_category.setText(categoryNames[choiceWhich]);
                        }
                    };
            builder.setPositiveButton("确定", btnListener);
            mChoseCategoryDialog = builder.create();
        }
        mChoseCategoryDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();

        new GoodsEditPersenter(new GoodsModelImpl(), this);
        mPresenter.getCategory(0, 0);
        if (isUpdate) {
            int product_id = getIntent().getIntExtra("product_id", -1);
            mDialogStr = getString(R.string.dlg_getting);
            mPresenter.getGoodsInfo(product_id);
        }
    }

    @Override
    public void setPresenter(@NonNull GoodsEditContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initDatas() {
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        mImageUrlList = new ArrayList<String>();
    }

    private void initViews() {
        if (isUpdate) {
            mTitlebar.initDatas(getString(R.string.goods_edit), true);
            mTitlebar.showRightTextView(getString(R.string.save));
        } else {
            mTitlebar.initDatas(getString(R.string.goods_add), true);
            mTitlebar.showRightTextView(getString(R.string.up_shelf));
        }

        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                checkValidate();
            }
        });

        ImageView iv = new ImageView(mContext);
        iv.setImageResource(R.drawable.ic_camera);
        int px = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_size);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(px, px);
        lp.rightMargin = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_margin_right);
        lp.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_margin_bottom);
        iv.setLayoutParams(lp);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageClick();
            }
        });
        flayout_image.addView(iv);
    }

    private void checkValidate() {
        // 若必填项商品名称、商品图片、商品描述、分类任意一项或几项没填，弹出提示：填写完整信息后方可上架，2S后消失
        String price = et_goods_price.getText().toString().trim();
        String stock = et_goods_stock.getText().toString().trim();
        String name = et_goods_name.getText().toString().trim();
        String describe = et_goods_describe.getText().toString().trim();

        if (price.equals("0")) {
            ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
        } else if (stock.equals("0")) {
            ToastUtils.getInstant().showToast(R.string.msg_stock_invalid);
        } else if (TextUtils.isEmpty(price) || TextUtils.isEmpty(stock) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(describe) || mImageUrlList.size() == 0) {
            ToastUtils.getInstant().showToast(R.string.msg_goods_invalid);
        } else {
            int uid = UserManager.getInstance().getUser().getUid();
            String product_images = "";
            for (int i = 0; i < mImageUrlList.size(); i++) {
                product_images += mImageUrlList.get(i);
                if (i != mImageUrlList.size() - 1) {
                    product_images += ",";
                }
            }
            String category = tv_category.getTag() + "";
            String buyingcycle = tv_date_limit.getTag() + "";
            if (TextUtils.isEmpty(buyingcycle) || buyingcycle.equals("null")) {
                buyingcycle = "";
            }
            String personalamountStr = tv_limit_count.getText().toString();
            personalamountStr = personalamountStr.replace("件", "");

            String beyondprice = tv_overrange.getText().toString();
            beyondprice = beyondprice.replace("元", "");
            if (isUpdate) {
                mDialogStr = getString(R.string.dlg_updating);
                mPresenter.modifyGoods(uid, product_id, name, product_images, price, stock, describe, category, buyingcycle, personalamountStr, beyondprice);
            } else {
                mDialogStr = getString(R.string.dlg_submiting);
                mPresenter.addGoods(uid, name, product_images, price, stock, describe, category, buyingcycle, personalamountStr, beyondprice);
            }
        }
    }

    private class ChoiceOnClickListener implements DialogInterface.OnClickListener {
        private int which = 0;

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            this.which = which;
        }

        public int getWhich() {
            return which;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeybord();
        return super.onTouchEvent(event);
    }

    // 关闭keybord
    public void closeKeybord() {
        KeyBoardUtils.closeKeybord(et_goods_name, this);
        KeyBoardUtils.closeKeybord(et_goods_price, this);
        KeyBoardUtils.closeKeybord(et_goods_stock, this);
        KeyBoardUtils.closeKeybord(et_goods_describe, this);
    }

    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {// getString(R.string.dlg_submiting)
            mDialog = ProgressDialog.show(mContext, "", mDialogStr);
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void setCategoryList(Integer[] ids, String[] names) {
        categoryIds = ids;
        categoryNames = names;
    }

    @Override
    public void setImageView(String url) {
        addImageView(url);
        FileUtils.deleteFile(mImagePath);
        mImagePath = "";
    }

    private void addImageView(String url) {
        mImageUrlList.add(url);
        UploadImageView imageView = new UploadImageView(mContext);
        imageView.setTag(url);
        imageView.setImage(url);
        int px = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_size);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(px, px);
        lp.rightMargin = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_margin_right);
        lp.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.goods_add_image_margin_bottom);
        imageView.setLayoutParams(lp);

        imageView.setOnRemoveListener(new UploadImageView.UploadImageViewListener() {
            @Override
            public void onRemoveImageClick(UploadImageView v) {
                mImageUrlList.remove(v.getTag());
                flayout_image.removeView(v);
            }
        });
        flayout_image.addView(imageView, 0);
    }

    @Override
    public void setGoodsInfo(Goods bean) {
        product_id = bean.getProduct_id() + "";
        et_goods_name.setText(bean.getProduct_name());
        et_goods_price.setText(bean.getSell_price());
        et_goods_stock.setText(bean.getProduct_instocks());
        et_goods_describe.setText(bean.getProduct_breif());

        tv_limit_count.setText(bean.getProduct_personalamount());
        tv_overrange.setText(bean.getProduct_beyondprice());

        String cycle = bean.getProduct_buyingcycle();
        if (!TextUtils.isEmpty(cycle) && !cycle.equals("null")) {
            tv_date_limit.setTag(cycle);
            if (cycle.equals("1")) {
                tv_date_limit.setText("每日");
            } else if (cycle.equals("2")) {
                tv_date_limit.setText("每周");
            } else if (cycle.equals("3")) {
                tv_date_limit.setText("每月");
            }
        }

        // 设置图片
        List<GoodsImage> images = bean.getImages();
        if (images != null) {
            for (GoodsImage img : images) {
                addImageView(img.getImage_path());
            }
        }
        // TODO 设置分类
        String classifyStr = bean.getProduct_category();
        if (!TextUtils.isEmpty(classifyStr) && !classifyStr.equals("null")) {
            int classify = Integer.valueOf(classifyStr);
            for (int i = 0; i < categoryIds.length; i++) {
                if (categoryIds[i] == classify) {
                    tv_category.setText(categoryNames[i]);
                }
            }
        }
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
        FileUtils.deleteFile(mImagePath);
        mImagePath = "";
    }

    @Override
    public void showSuccessView(Goods bean) {
        if (isUpdate) {
            Intent intent = new Intent();
            intent.putExtra("bean", bean);
            setResult(Activity.RESULT_OK, intent);
            ToastUtils.getInstant().showToast("更新成功");
            finishDelayed(BaseConstant.DELAYED_TIME);
        } else {
            Intent intent = new Intent(ActionConstant.REFRESH_GOODS_LIST);
            intent.putExtra("type", TypeConstant.Goods.ON_SALE);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            ToastUtils.getInstant().showToast("上架成功");
            finishDelayed(BaseConstant.DELAYED_TIME);
        }
    }

    /**
     * 相机拍照
     */
    private void doTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(mImagePath);
        // 设置输出路径
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 手机拍照
        if (requestCode == BaseConstant.REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = ImageUtils.readBitmap(mImagePath, 480, 800);
                ImageUtils.saveBitmapToLocal(mImagePath, bmp);
                mDialogStr = getString(R.string.dlg_upload_image);
                mPresenter.uploadImage(mImagePath);
            }
        }
        // 相册获取
        else if (requestCode == BaseConstant.REQUEST_PICK_PHOTO) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                String imagePath = ImageUtils.getRealPathFromURI(mContext, uri);
                Bitmap bmp = ImageUtils.readBitmap(imagePath, 480, 800);
                mImagePath = mContext.getExternalCacheDir()
                        .getAbsolutePath()
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                ImageUtils.saveBitmapToLocal(mImagePath, bmp);
                mDialogStr = getString(R.string.dlg_upload_image);
                mPresenter.uploadImage(mImagePath);
            }
        }
    }
}
