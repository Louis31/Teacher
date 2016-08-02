package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.MyItemClickListener;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.ActionConstant;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.DeliverOrderContract;
import com.haiku.wateroffer.mvp.model.impl.OrderModelImpl;
import com.haiku.wateroffer.mvp.persenter.DeliverOrderPersenter;
import com.haiku.wateroffer.mvp.view.adapter.DeliverOrderAdapter;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
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
public class DeliverOrderActivity extends BaseActivity implements DeliverOrderContract.View, MyItemClickListener {

    private int uid;
    private int mid;
    private int mItemPos;
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
        mPresenter.getListDatas(uid, mid);
    }

    @Override
    public void setPresenter(@NonNull DeliverOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initDatas() {
        uid = getIntent().getIntExtra("deliver_id", -1);
        mid = UserManager.getInstance().getUser().getUid();

        mDatas = new ArrayList<>();
        mAdapter = new DeliverOrderAdapter(mContext, mDatas);
        mAdapter.setListener(this);
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

        mRefreshLayout.setPageSize(1000);
        mRefreshLayout.addItemDecoration(new BroadDividerItem(mContext));
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
        mRefreshLayout.setLoadMoreEnable(false);
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
        mDatas.remove(mItemPos);
        mAdapter.notifyItemRemoved(mItemPos);

        // 更新待发货列表
        Intent intent = new Intent(ActionConstant.REFRESH_ORDER_LIST);
        intent.putExtra("type", TypeConstant.Order.PAYED);
        // 发送广播
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    @Override
    public void onItemClick(final int pos) {
        new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_order_cancel))
                .setCancelable(false)
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 取消配送
                        mItemPos = pos;
                        mPresenter.cancelOrder(mDatas.get(pos).getOrder_id(), mid, uid);
                    }
                }).setNegativeButton("否", null).show();
    }
}
