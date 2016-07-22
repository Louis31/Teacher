package com.haiku.wateroffer.mvp.view.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.OrderListListener;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.ActionConstant;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.contract.OrderListContract;
import com.haiku.wateroffer.mvp.model.impl.OrderModelImpl;
import com.haiku.wateroffer.mvp.persenter.OrderListPersenter;
import com.haiku.wateroffer.mvp.view.activity.OrderInfoActivity;
import com.haiku.wateroffer.mvp.view.adapter.OrderListAdapter;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
import com.haiku.wateroffer.mvp.view.divider.BroadDividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表Fragment
 * Created by hyming on 2016/7/11.
 */
public class OrderListFragment extends LazyFragment implements OrderListContract.View, MyRefreshLayout.OnRefreshLayoutListener, OrderListListener {
    private final String TAG = "OrderListFragment";
    private Context mContext;

    private int uid;
    private int mItemPos;// 记录当前操作item的位置
    private String mType;// 标记当前列表数据的类型
    private List<OrderItem> mDatas;

    private ProgressDialog mDialog;

    private View rootView;
    private MyRefreshLayout mRefreshLayout;
    private OrderListAdapter mAdapter;
    private OrderListContract.Presenter mPresenter;

    public static OrderListFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///获取索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString("type");
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.view_refresh_layout, container, false);
            initDatas();
            initViews();
            new OrderListPersenter(new OrderModelImpl(), this);
            isPrepared = true;
            lazyLoad();
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 注册广播接收者
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConstant.REFRESH_ORDER_LIST);
        broadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");
            if (mType.equals(type)) {
                LogUtils.showLogE(TAG, "onReceive Success");
                mRefreshLayout.refresh();// 刷新列表
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        broadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mDatas = new ArrayList<>();
        mAdapter = new OrderListAdapter(mContext, mDatas, mType, this);
        mAdapter.setListener(this);
    }

    private void initViews() {
        mRefreshLayout = (MyRefreshLayout) rootView.findViewById(R.id.myRefreshLayout);
        mRefreshLayout.setPageSize(BaseConstant.PAGE_SIZE_DEFAULT);
        mRefreshLayout.addItemDecoration(new BroadDividerItem(mContext));
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
        mRefreshLayout.setListener(this);
    }

    @Override
    public void setPresenter(OrderListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        mPresenter.getListDatas(uid, mType, BaseConstant.ORDER_SORT_SYNTHESIS, mRefreshLayout.getCurrentPage());
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
        mPresenter.getListDatas(uid, mType, BaseConstant.ORDER_SORT_SYNTHESIS, mRefreshLayout.getCurrentPage());
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        mPresenter.getListDatas(uid, mType, BaseConstant.ORDER_SORT_SYNTHESIS, mRefreshLayout.getCurrentPage());
    }

    // 显示列表界面
    @Override
    public void showListView(List<OrderItem> list) {
        mDatas.addAll(list);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.loadingCompleted(true);
    }

    // 刷新页面
    @Override
    public void refreshListView(int type) {
        /*if (type == TypeConstant.OrderOpera.CANCEL_DELIVER) {
            // 取消配送成功
            mDatas.remove(mItemPos);
            mAdapter.notifyItemRemoved(mItemPos);

        } else if (type == TypeConstant.OrderOpera.SEND_ORDER) {
            // 派单成功
            mDatas.remove(mItemPos);
            mAdapter.notifyItemRemoved(mItemPos);
        }*/
        mDatas.remove(mItemPos);
        mAdapter.notifyItemRemoved(mItemPos);
        // 发送广播
        Intent intent = new Intent(ActionConstant.REFRESH_ORDER_LIST);
        intent.putExtra("type", mType);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    // 显示/隐藏加载对话框
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

    /**
     * 事件回调
     */
    // 查看订单详情
    @Override
    public void onOrderDetailClick(int pos) {
        int order_id = mDatas.get(pos).getOrder_id();
        Intent intent = new Intent(mContext, OrderInfoActivity.class);
        intent.putExtra("order_id", order_id);
        startActivity(intent);
    }

    // 取消配送
    @Override
    public void onOrderCancelClick(final int pos) {
        new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_order_cancel))
                .setCancelable(false)
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemPos = pos;
                        mPresenter.cancelOrder(mDatas.get(pos).getOrder_id(), uid);  // 取消配送
                    }
                }).setNegativeButton("否", null).show();
    }

    // 派单
    @Override
    public void onOrderSendClick(int pos) {
        // 判断配送列表是否已经添加配送员
        mPresenter
        boolean isHas = true;
        if (isHas) {
            mItemPos = pos;
            mPresenter.sendOrder(mDatas.get(pos).getOrder_id(), uid);// 派送订单
        } else {
            new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_deliver_add))
                    .setCancelable(false)
                    .setPositiveButton("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 跳转到配送列表页面
                        }
                    }).setNegativeButton("否", null).show();
        }
    }
}
