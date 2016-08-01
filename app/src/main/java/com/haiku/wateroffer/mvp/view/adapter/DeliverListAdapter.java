package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.listener.DeliverListListener;
import com.haiku.wateroffer.constant.TypeConstant;

import java.util.List;

/**
 * 配送员列表Adapter
 * Created by hyming on 2016/7/20.
 */
public class DeliverListAdapter extends MyBaseAdapter {
    public final static int TYPE_LIST = 1;
    public final static int TYPE_EDIT = 2;

    private int mType;
    private String pauseStr;
    private String continueStr;

    private List<Deliver> mDatas;
    private DeliverListListener mListener;

    public void setListener(DeliverListListener listener) {
        this.mListener = listener;
    }

    public DeliverListAdapter(Context cxt, List<Deliver> datas, int type) {
        super(cxt);
        this.mDatas = datas;
        this.mType = type;
        pauseStr = cxt.getString(R.string.pause);
        continueStr = cxt.getString(R.string.go_on);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        Deliver bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;

        vh.tv_deliver_name.setText(bean.getDiliveryman_name());
        vh.tv_deliver_phone.setText(bean.getDiliveryman_phone());

        // 编辑列表
        if (mType == TYPE_EDIT) {
            if (TypeConstant.Deliver.PAUSE == bean.getDiliveryman_status()) {
                // 暂停状态，可以点击继续
                vh.tv_continue_pause.setText(continueStr);
            } else if (bean.getDiliveryman_status().equals(TypeConstant.Deliver.NORMAL)) {
                // 继续状态，可以点击暂停
                vh.tv_continue_pause.setText(pauseStr);
            }
        }
        // 配送列表
        else if (mType == TYPE_LIST) {
            if (TypeConstant.Deliver.PAUSE == bean.getDiliveryman_status()) {
                // 暂停状态
                vh.tv_paused.setVisibility(View.VISIBLE);
                vh.tv_added.setVisibility(View.GONE);
            } else if (bean.getDiliveryman_status().equals(TypeConstant.Deliver.NORMAL)) {
                // 继续状态
                vh.tv_added.setVisibility(View.VISIBLE);
                vh.tv_paused.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_deliver_list, parent, false), mListener);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_deliver_name;
        TextView tv_deliver_phone;
        TextView tv_added;
        TextView tv_paused;
        View llayout_operation;
        TextView tv_continue_pause;
        TextView tv_delete;

        public ItemViewHolder(View v, final DeliverListListener listener) {
            super(v);
            tv_deliver_name = bind(v, R.id.tv_deliver_name);
            tv_deliver_phone = bind(v, R.id.tv_deliver_phone);
            tv_added = bind(v, R.id.tv_added);
            tv_paused = bind(v, R.id.tv_paused);
            llayout_operation = bind(v, R.id.llayout_operation);
            tv_continue_pause = bind(v, R.id.tv_continue_pause);
            tv_delete = bind(v, R.id.tv_delete);

            // 配送员列表
            if (mType == TYPE_LIST) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(getPosition());
                        }
                    }
                });
            }
            // 编辑列表
            else if (mType == TYPE_EDIT) {
                llayout_operation.setVisibility(View.VISIBLE);
                tv_continue_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onContinuePauseClick(getPosition());
                        }
                    }
                });

                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onDeleteClick(getPosition());
                        }
                    }
                });
            }
        }
    }
}
