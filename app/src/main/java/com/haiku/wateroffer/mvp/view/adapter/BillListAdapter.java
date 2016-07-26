package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Bill;

import java.util.List;

/**
 * 账单明细列表Adapter
 * Created by hyming on 2016/7/18.
 */
public class BillListAdapter extends MyBaseAdapter {

    private String rmb;
    private String orderNum;
    private String orderUpPay;
    private String orderOffPay;
    private List<Bill> mDatas;

    public BillListAdapter(Context cxt, List<Bill> datas) {
        super(cxt);
        this.mDatas = datas;
        rmb = cxt.getString(R.string.rmb);
        orderNum = cxt.getString(R.string.order_number_tip);
        orderUpPay = cxt.getString(R.string.order_up_pay);
        orderOffPay = cxt.getString(R.string.order_off_pay);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        Bill bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;
        vh.tv_order_number.setText(orderNum + bean.getSerial_number());
        vh.tv_order_deliver.setText(bean.getDiliverman());
        vh.tv_order_price.setText(rmb + bean.getOrder_amount());

        // 0，1为线上付款；2为线下付款
        if (bean.getWepay_method() == 2) {
            vh.tv_order_pay.setText(orderOffPay);
        } else {
            vh.tv_order_pay.setText(orderUpPay);
        }
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_bill, null));
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_order_number;
        TextView tv_order_deliver;
        TextView tv_order_price;
        TextView tv_order_pay;

        public ItemViewHolder(View v) {
            super(v);
            tv_order_number = bind(v, R.id.tv_order_number);
            tv_order_deliver = bind(v, R.id.tv_order_deliver);
            tv_order_price = bind(v, R.id.tv_order_price);
            tv_order_pay = bind(v, R.id.tv_order_pay);
        }
    }
}
