package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.OrderItem;

import java.util.List;

/**
 * 配送员订单列表Adapter
 * Created by hyming on 2016/7/25.
 */
public class DeliverOrderAdapter extends MyBaseAdapter {
    private List<OrderItem> mDatas;

    public DeliverOrderAdapter(Context cxt, List<OrderItem> datas) {
        super(cxt);
        this.mDatas = datas;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        OrderItem bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;

    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_order, null));
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {

        public ItemViewHolder(View v) {
            super(v);
        }
    }
}
