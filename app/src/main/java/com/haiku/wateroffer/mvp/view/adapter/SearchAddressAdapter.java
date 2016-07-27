package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.MyItemClickListener;

import java.util.List;

/**
 * 搜索地址Adapter
 * Created by hyming on 2016/7/26.
 */
public class SearchAddressAdapter extends MyBaseAdapter {

    private List<PoiItem> mDatas;
    private MyItemClickListener mListener;

    public void setListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    public SearchAddressAdapter(Context cxt, List<PoiItem> datas) {
        super(cxt);
        this.mDatas = datas;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        PoiItem bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;

        vh.tv_address_title.setText(bean.getTitle());
        vh.tv_address_content.setText(bean.getSnippet());
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_address, null), mListener);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_address_title;
        TextView tv_address_content;

        public ItemViewHolder(View v, final MyItemClickListener listener) {
            super(v);
            tv_address_title = bind(v, R.id.tv_address_title);
            tv_address_content = bind(v, R.id.tv_address_content);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getPosition());
                    }
                }
            });
        }
    }
}
