package com.haiku.wateroffer.mvp.view.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.haiku.wateroffer.R;

/**
 * 自定义列表刷新页面
 * Created by hyming on 2016/7/11.
 */
public class MyRefreshLayout extends FrameLayout {

    private Context mContext;

    private int mCurrentPage;// 当前页
    private int mStartPage = 0;// 默认开始页数
    private int mPageSize = 10;// 每页数量
    private int lastVisibleItemPosition;

    private boolean isCanLoadMore;
    private boolean isLoading;

    private LinearLayoutManager mLayoutManager;
    private Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnRefreshLayoutListener mListener;

    public void setListener(OnRefreshLayoutListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshLayoutListener {
        // 刷新当前页面
        void onRefresh();

        // 加载更多数据
        void onLoadMore();
    }

    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        mCurrentPage = mStartPage;
        isCanLoadMore = true;
        isLoading = true;

        View view = LayoutInflater.from(context).inflate(R.layout.refresh_layout, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 第一次显示加载
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (isLoading)
                    mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new MyRefreshListener());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new MyScrollListener());

        this.addView(view);
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setItemAnimator(ItemAnimator animator) {
        if (animator != null) {
            mRecyclerView.setItemAnimator(animator);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setStartPage(int page) {
        this.mStartPage = page;
        mCurrentPage = mStartPage;
    }

    public void setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public void setLinearLayout() {
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setGridLayout(int spanCount) {
        mLayoutManager = new GridLayoutManager(mContext, spanCount);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    // 刷新数据完成
    public void refreshCompleted(boolean isSuccess) {
        if (isSuccess) {
            mCurrentPage = mStartPage;
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    // 加载数据完成
    public void loadingCompleted(boolean isSuccess) {
        if (isSuccess) {
            mCurrentPage++;
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    // 是否可以下拉刷新
    public void setPullRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    // 是否可以加载更多
    public void setLoadMoreEnable(boolean enable) {
        isCanLoadMore = enable;
    }


    private class MyScrollListener extends OnScrollListener {
        public MyScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (isCanLoadMore && !isLoading && newState == RecyclerView.SCROLL_STATE_IDLE
                    && mAdapter.getItemCount() >= mPageSize && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                isLoading = true;
                mSwipeRefreshLayout.setRefreshing(true);
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isCanLoadMore && !isLoading) {
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        }
    }

    private class MyRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            if (!isLoading) {
                isLoading = true;
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        }
    }
}
