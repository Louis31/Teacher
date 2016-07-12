package com.haiku.wateroffer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;

import java.util.List;

/**
 * 订单列表Adapter
 * Created by hyming on 2016/7/11.
 */
public class OrderListAdapter extends Adapter<ViewHolder> {

    private Context mContext;
    private List<OrderItem> mDatas;

    public OrderListAdapter(Context cxt, List<OrderItem> datas) {
        this.mContext = cxt;
        this.mDatas = datas;
    }

    public void setDatas(List<OrderItem> datas) {
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ItemViewHolder extends ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
