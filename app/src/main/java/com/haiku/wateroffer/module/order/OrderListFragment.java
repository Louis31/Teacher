package com.haiku.wateroffer.module.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.OrderListListener;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.model.impl.OrderModelImpl;
import com.haiku.wateroffer.module.base.LazyFragment;
import com.haiku.wateroffer.ui.adapter.OrderListAdapter;
import com.haiku.wateroffer.ui.dialog.AlertDialog;
import com.haiku.wateroffer.ui.divider.BroadDividerItem;
import com.haiku.wateroffer.ui.widget.MyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表Fragment
 * Created by hyming on 2016/7/11.
 */
public class OrderListFragment extends LazyFragment implements OrderListContract.View, MyRefreshLayout.OnRefreshLayoutListener, OrderListListener {

    private Context mContext;

    private int uid;
    private String mType;// 标记当前列表数据的类型
    private List<OrderItem> mDatas;

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
            rootView = inflater.inflate(R.layout.fragment_order_list, container, false);
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

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mDatas = new ArrayList<>();
        mAdapter = new OrderListAdapter(mContext, mDatas, mType, this);
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

    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    /**
     * 事件回调
     */
    // 查看订单详情
    @Override
    public void onOrderDetailClick(int pos) {
        startActivity(new Intent(mContext, OrderInfoActivity.class));
    }

    // 取消配送
    @Override
    public void onOrderCancelClick(int pos) {
        new AlertDialog(mContext).builder().setMsg(getString(R.string.dlg_order_cancel))
                .setCancelable(false)
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 取消配送
                        mPresenter.cancelOrder();
                    }
                }).setNegativeButton("否", null).show();
    }

    // 派单
    @Override
    public void onOrderSendClick(int pos) {
        // 判断配送列表是否已经添加配送员
        boolean isHas = false;
        if (isHas) {
            // 派送订单
            mPresenter.sendOrder();
        } else {
            new AlertDialog(mContext).builder().setMsg(getString(R.string.dlg_deliver_add))
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
