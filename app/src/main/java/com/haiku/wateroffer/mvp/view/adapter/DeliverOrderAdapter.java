package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.listener.MyItemClickListener;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.constant.TypeConstant;

import java.util.List;

/**
 * 配送员订单列表Adapter
 * Created by hyming on 2016/7/25.
 */
public class DeliverOrderAdapter extends MyBaseAdapter {
    // item相关字符串
    private String rmb;
    private String orderNumTip;
    private String goodsNumTip;
    private String orderUpPay;
    private String orderOffPay;

    private List<OrderItem> mDatas;
    private MyItemClickListener mListener;

    public void setListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    public DeliverOrderAdapter(Context cxt, List<OrderItem> datas) {
        super(cxt);
        this.mDatas = datas;
        rmb = cxt.getString(R.string.rmb);
        orderNumTip = cxt.getString(R.string.order_number_tip);
        goodsNumTip = cxt.getString(R.string.order_goods_num_tip);
        orderUpPay = cxt.getString(R.string.order_up_pay);
        orderOffPay = cxt.getString(R.string.order_off_pay);

    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        OrderItem bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;
        // 设置订单数据
        vh.tv_order_number.setText(orderNumTip + bean.getSerial_number());
        vh.tv_order_time.setText(bean.getOrder_time());
        vh.tv_order_amount.setText(rmb + bean.getOrder_amount());
        // 0，1为线上付款；2为线下付款
        if (bean.getWepay_method() == 2) {
            vh.tv_wepay_method.setText(orderOffPay);
        } else {
            vh.tv_wepay_method.setText(orderUpPay);
        }
        vh.llayout_goods.removeAllViews();
        // 设置订单商品数据
        if (bean.getDetails() != null && bean.getDetails().size() > 0) {
            for (int i = 0; i < bean.getDetails().size(); i++) {
                Goods goods = bean.getDetails().get(i);
                View v = mInflater.inflate(R.layout.item_order_goods, null);

                ImageView iv_goods = bind(v, R.id.iv_goods);
                TextView tv_goods_name = bind(v, R.id.tv_goods_name);
                TextView tv_goods_price = bind(v, R.id.tv_goods_price);
                TextView tv_goods_count = bind(v, R.id.tv_goods_count);

                tv_goods_name.setText(goods.getProduct_name());
                tv_goods_price.setText(rmb + goods.getSell_price());
                tv_goods_count.setText(goodsNumTip + goods.getProduct_count());

                ImageUtils.showImage(mContext, goods.getProduct_image(), iv_goods);
                vh.llayout_goods.addView(v);
            }
        }
        // 设置状态底部按钮
        if (TypeConstant.Order.DELIVERING.equals(bean.getStatus())) {
            vh.tv_order_cancel.setVisibility(View.VISIBLE);
            vh.tv_order_finish.setVisibility(View.GONE);

        } else if (TypeConstant.Order.RECEIVED.equals(bean.getStatus())) {
            vh.tv_order_finish.setVisibility(View.VISIBLE);
            vh.tv_order_cancel.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_order, null), mListener);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }


    class ItemViewHolder extends ViewHolder {
        TextView tv_order_number;
        TextView tv_order_time;
        TextView tv_order_amount;
        TextView tv_wepay_method;
        LinearLayout llayout_goods;
        FrameLayout flayout_bottom;
        TextView tv_order_finish;
        TextView tv_order_cancel;

        public ItemViewHolder(View v, final MyItemClickListener listener) {
            super(v);
            tv_order_number = bind(v, R.id.tv_order_number);
            tv_order_time = bind(v, R.id.tv_order_time);
            tv_order_amount = bind(v, R.id.tv_order_amount);
            tv_wepay_method = bind(v, R.id.tv_wepay_method);
            llayout_goods = bind(v, R.id.llayout_goods);
            flayout_bottom = bind(v, R.id.flayout_bottom);
            tv_order_finish = bind(v, R.id.tv_order_finish);
            tv_order_cancel = bind(v, R.id.tv_order_cancel);
            flayout_bottom.setVisibility(View.VISIBLE);
            tv_order_cancel.setOnClickListener(new View.OnClickListener() {
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
