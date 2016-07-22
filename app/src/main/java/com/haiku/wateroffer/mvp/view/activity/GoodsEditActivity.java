package com.haiku.wateroffer.mvp.view.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 添加/编辑商品Activity
 * Created by hyming on 2016/7/17.
 */
// TODO 上传商品最多6张
@ContentView(R.layout.act_goods_edit)
public class GoodsEditActivity extends BaseActivity {

    private boolean isUpdate;// 当前是否为编辑界面
    private List<String> images;

    private AlertDialog mChoseDialog;

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

    // 每天点击
    @Event(R.id.rlayout_person)
    private void personClick(View v) {
        if (mChoseDialog == null) {
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
                            tv_date_limit.setText(hobbyStr);
                        }
                    };
            builder.setPositiveButton("确定", btnListener);
            mChoseDialog = builder.create();
        }
        mChoseDialog.show();
    }

    // 限购点击
    @Event(R.id.rlayout_limit)
    private void limitClick(View v) {
        final IOSAlertDialog dialog = new IOSAlertDialog(mContext).builder();
        dialog.setTitle(getString(R.string.dlg_limit_title)).setMsg(getString(R.string.dlg_limit_tip))
                .setInput(InputType.TYPE_CLASS_NUMBER)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 获取到输入数据
                                String value = dialog.getInputValue();
                                tv_limit_count.setText(value + "次");
                            }
                        })
                .setNegativeButton(getString(R.string.cancel), null);
        dialog.show();
    }

    // 超额点击
    @Event(R.id.rlayout_overrange)
    private void overrangeClick(View v) {
        final IOSAlertDialog dialog = new IOSAlertDialog(mContext).builder();
        dialog.setTitle(getString(R.string.dlg_overrange_title)).setMsg(getString(R.string.dlg_overrange_tip))
                .setInput(InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 获取到输入数据
                                String value = dialog.getInputValue();
                                tv_overrange.setText(value + "元");
                            }
                        })
                .setNegativeButton(getString(R.string.cancel), null);
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
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
                super.onRightTextClick();
            }
        });
    }

    private void checkValidate() {
        // 判断3，若必填项商品名称、商品图片、商品描述、分类任意一项或几项没填，弹出提示：填写完整信息后方可上架，2S后消失
        String price = et_goods_price.getText().toString().trim();
        String stock = et_goods_stock.getText().toString().trim();
        String name = et_goods_name.getText().toString().trim();
        String describe = et_goods_describe.getText().toString().trim();

        if (price.equals("0")) {
            ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
        } else if (stock.equals("0")) {
            ToastUtils.getInstant().showToast(R.string.msg_stock_invalid);
        } else if (TextUtils.isEmpty(price) || TextUtils.isEmpty(stock) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(describe) || images.size() == 0) {
            ToastUtils.getInstant().showToast(R.string.msg_goods_invalid);
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
}
