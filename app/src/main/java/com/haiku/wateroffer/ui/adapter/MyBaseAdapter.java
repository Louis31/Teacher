package com.haiku.wateroffer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hyming on 2016/7/14.
 */
public abstract class MyBaseAdapter extends Adapter<ViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;

    protected abstract int getDataItemCount();
    protected abstract ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindItemViewHolder(ViewHolder holder, int position);

    public MyBaseAdapter(Context cxt) {
        this.mContext = cxt;
        mInflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindItemViewHolder(holder, position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateItemViewHolder(parent, viewType);
    }

    // 绑定view id
    public <T extends View> T bind(View v, int viewId) {
        View view = v.findViewById(viewId);
        return (T) view;
    }
}
