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
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.GoodsListListener;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.ActionConstant;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.contract.GoodsListContract;
import com.haiku.wateroffer.mvp.model.impl.GoodsModelImpl;
import com.haiku.wateroffer.mvp.persenter.GoodsListPersenter;
import com.haiku.wateroffer.mvp.view.activity.GoodsEditActivity;
import com.haiku.wateroffer.mvp.view.adapter.GoodsListAdapter;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
import com.haiku.wateroffer.mvp.view.divider.BroadDividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表Fragment
 * Created by hyming on 2016/7/11.
 */
public class GoodsListFragment extends LazyFragment implements GoodsListContract.View, MyRefreshLayout.OnRefreshLayoutListener, GoodsListListener {
    private final String TAG = "GoodsListFragment";

    private Context mContext;
    private int uid;
    private int mItemPos;// 记录当前操作item的位置
    private int mType;// 标记当前列表数据的类型
    private List<Goods> mDatas;
    private ProgressDialog mDialog;
    private View rootView;
    private MyRefreshLayout mRefreshLayout;

    private GoodsListAdapter mAdapter;
    private GoodsListContract.Presenter mPresenter;

    public static GoodsListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        GoodsListFragment fragment = new GoodsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///获取索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt("type");
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.view_refresh_layout, container, false);
            initDatas();
            initViews();
            new GoodsListPersenter(new GoodsModelImpl(), this);
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
        intentFilter.addAction(ActionConstant.REFRESH_GOODS_LIST);
        broadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", -1);
            if (mType == type) {
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
        mAdapter = new GoodsListAdapter(mContext, mDatas, mType, this);
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
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        mPresenter.getListDatas(uid, mType, mRefreshLayout.getCurrentPage());
    }

    @Override
    public void setPresenter(GoodsListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
        mPresenter.getListDatas(uid, mType, mRefreshLayout.getCurrentPage());
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        mPresenter.getListDatas(uid, mType, mRefreshLayout.getCurrentPage());
    }

    // 显示列表界面
    @Override
    public void showListView(List<Goods> list) {
        mDatas.addAll(list);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.loadingCompleted(true);
    }


    // 刷新页面
    @Override
    public void refreshListView(int type) {
        /*if (type == TypeConstant.GoodsOpera.DELETE_GOODS) {
            // 删除成功
        } else if (type == TypeConstant.GoodsOpera.OFF_SHELF) {
            // 下架成功
            mDatas.remove(mItemPos);
            mAdapter.notifyItemRemoved(mItemPos);
        }*/

        mDatas.remove(mItemPos);
        mAdapter.notifyItemRemoved(mItemPos);
        // 发送广播
        Intent intent = new Intent(ActionConstant.REFRESH_GOODS_LIST);
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
    // 删除商品
    @Override
    public void onGoodsDeleteClick(final int pos) {
        new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_delete_goods))
                .setCancelable(false)
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  删除商品
                        mItemPos = pos;
                        mPresenter.deleteGoods(uid, mDatas.get(pos).getProduct_id());
                    }
                }).setNegativeButton("否", null).show();
    }

    // 上架商品
    @Override
    public void onGoodsUpShelfClick(int pos) {

    }

    // 下架商品
    @Override
    public void onGoodsOffShelfClick(int pos) {
        mItemPos = pos;
        mPresenter.offShelfGoods(uid, mDatas.get(pos).getProduct_id());
    }

    // 编辑商品
    @Override
    public void onGoodsEditClick(int pos) {
        Intent intent = new Intent(mContext, GoodsEditActivity.class);
        intent.putExtra("isUpdate", true);
        startActivity(intent);
    }
}
