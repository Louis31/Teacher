package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.DeliverOrderContract;
import com.haiku.wateroffer.mvp.model.impl.OrderModelImpl;
import com.haiku.wateroffer.mvp.persenter.DeliverOrderPersenter;
import com.haiku.wateroffer.mvp.view.adapter.DeliverOrderAdapter;
import com.haiku.wateroffer.mvp.view.divider.BroadDividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 配送员订单列表界面
 * Created by hyming on 2016/7/20.
 */
@ContentView(R.layout.act_common_list)
public class DeliverOrderActivity extends BaseActivity implements DeliverOrderContract.View {
    private List<OrderItem> mDatas;
    private DeliverOrderAdapter mAdapter;

    private DeliverOrderContract.Presenter mPresenter;

    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();

        new DeliverOrderPersenter(new OrderModelImpl(), this);
    }

    @Override
    public void setPresenter(@NonNull DeliverOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initDatas() {
        mDatas = new ArrayList<OrderItem>();
        mAdapter = new DeliverOrderAdapter(mContext, mDatas);
    }

    private void initViews() {
        String name = getIntent().getStringExtra("deliver_name");
        mTitlebar.initDatas(name + "配送列表", true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });

        mRefreshLayout.setPageSize(BaseConstant.PAGE_SIZE_DEFAULT);
        mRefreshLayout.addItemDecoration(new BroadDividerItem(mContext));
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
    }

    /**
     * 事件回调
     */
    @Override
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
    public void showListView(List<OrderItem> list) {
        mDatas.addAll(list);
        mRefreshLayout.loadingCompleted(true);
    }

    @Override
    public void refreshListView() {

    }

    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }
}
