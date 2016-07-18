package com.haiku.wateroffer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.listener.MyItemClickListener;

import java.util.List;

/**
 * 选择配送员列表Adapter
 * Created by hyming on 2016/7/18.
 */
public class DeliverSelectAdapter extends MyBaseAdapter {

    private List<Deliver> mDatas;
    private MyItemClickListener mListener;

    public void setListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    public DeliverSelectAdapter(Context cxt, List<Deliver> datas) {
        super(cxt);
        this.mDatas = datas;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        Deliver bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;

        vh.tv_deliver_name.setText(bean.getDiliveryman_name());
        vh.tv_deliver_phone.setText(bean.getDiliveryman_phone());
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_deliver_list, null), mListener);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_deliver_name;
        TextView tv_deliver_phone;

        public ItemViewHolder(View v, final MyItemClickListener listener) {
            super(v);
            tv_deliver_name = bind(v, R.id.tv_deliver_name);
            tv_deliver_phone = bind(v, R.id.tv_deliver_phone);
            tv_deliver_phone.setGravity(Gravity.RIGHT);
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
