package com.haiku.wateroffer.mvp.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 配送范围Activity
 * Created by hyming on 2016/7/27.
 */
@ContentView(R.layout.act_deliver_range)
public class DeliverRangeActivity extends BaseActivity implements IRequestCallback {
    private AlertDialog mRangeDialog;
    private ProgressDialog mDialog;
    private IShopModel mShopModel;

    private int mChoiceWhich;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.tv_range)
    private TextView tv_range;

    @Event(R.id.rlayout_range)
    private void rangeClick(View v) {
        if (mRangeDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("配送范围");
            final ChoiceOnClickListener choiceListener =
                    new ChoiceOnClickListener();
            builder.setSingleChoiceItems(R.array.deliver_range, UserManager.getInstance().getRange(), choiceListener);

            DialogInterface.OnClickListener btnListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            int choiceWhich = choiceListener.getWhich();
                            mChoiceWhich = choiceWhich;
                            String range =
                                    getResources().getStringArray(R.array.deliver_range)[choiceWhich];
                            tv_range.setText(range);
                        }
                    };
            builder.setPositiveButton("确定", btnListener);
            mRangeDialog = builder.create();
        }
        mRangeDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mShopModel = new ShopModelImpl();
    }

    private void initView() {
        mTitlebar.initDatas(R.string.delivery_range, true);
        mTitlebar.showRightTextView(getString(R.string.save));
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                showLoadingDialog(true);
                int uid = UserManager.getInstance().getUser().getUid();
                String range = tv_range.getText().toString();
                Map<String, Object> params = new HashMap<>();
                params.put("id", uid);
                params.put("range", range);
                if (UserManager.isTokenEmpty()) {
                    ((IBaseModel) mShopModel).getAccessToken(params, DeliverRangeActivity.this);
                } else {
                    mShopModel.changeShopRange(params, DeliverRangeActivity.this);
                }
            }
        });

        tv_range.setText(getResources().getStringArray(R.array.deliver_range)[UserManager.getInstance().getRange()]);
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

    // 显示/隐藏加载对话框
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_submiting));
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mShopModel.changeShopRange(params, DeliverRangeActivity.this);
    }

    @Override
    public void onSuccess() {
        UserManager.getInstance().setRange(mChoiceWhich);
        showLoadingDialog(false);
        finish();
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        showLoadingDialog(false);
        ToastUtils.getInstant().showToast(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
