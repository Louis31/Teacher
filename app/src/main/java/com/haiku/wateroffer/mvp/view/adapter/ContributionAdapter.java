package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Contribution;

import java.util.List;

/**
 * 贡献值Adapter
 * Created by hyming on 2016/7/18.
 */
public class ContributionAdapter extends MyBaseAdapter {

    private List<Contribution> mDatas;

    public ContributionAdapter(Context cxt, List<Contribution> datas) {
        super(cxt);
        this.mDatas = datas;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        Contribution bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;
        vh.tv_order_id.setText(bean.getOrderid());
        vh.tv_contribute.setText("+"+bean.getContribution());

    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_contribute, null));
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_order_id;
        TextView tv_contribute;

        public ItemViewHolder(View v) {
            super(v);
            tv_order_id = bind(v, R.id.tv_order_id);
            tv_contribute = bind(v, R.id.tv_contribute);
        }
    }
}
