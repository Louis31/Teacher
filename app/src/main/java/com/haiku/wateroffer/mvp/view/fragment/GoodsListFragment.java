package com.haiku.wateroffer.mvp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.GoodsListListener;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.model.impl.GoodsModelImpl;
import com.haiku.wateroffer.mvp.base.LazyFragment;
import com.haiku.wateroffer.mvp.contract.GoodsListContract;
import com.haiku.wateroffer.mvp.persenter.GoodsListPersenter;
import com.haiku.wateroffer.mvp.view.adapter.GoodsListAdapter;
import com.haiku.wateroffer.mvp.view.divider.BroadDividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表Fragment
 * Created by hyming on 2016/7/11.
 */
public class GoodsListFragment extends LazyFragment implements GoodsListContract.View, MyRefreshLayout.OnRefreshLayoutListener, GoodsListListener {
    private Context mContext;

    private int uid;
    private int mType;// 标记当前列表数据的类型
    private List<Goods> mDatas;

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

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mDatas = new ArrayList<>();
        mAdapter = new GoodsListAdapter(mContext, mDatas, mType, this);
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


    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    // 删除商品
    @Override
    public void onGoodsDeleteClick(int pos) {

    }

    // 上架商品
    @Override
    public void onGoodsUpShelfClick(int pos) {

    }

    // 下架商品
    @Override
    public void onGoodsOffShelfClick(int pos) {

    }

    // 编辑商品
    @Override
    public void onGoodsEditClick(int pos) {

    }
}
