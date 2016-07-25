package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
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
import com.haiku.wateroffer.common.listener.OrderListListener;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.constant.TypeConstant;

import java.util.List;

/**
 * 订单列表Adapter
 * Created by hyming on 2016/7/11.
 */
public class OrderListAdapter extends MyBaseAdapter {
    private String mStatus;
    // item相关字符串
    private String rmb;
    private String orderNumTip;
    private String goodsNumTip;
    private String orderUpPay;
    private String orderOffPay;

    private List<OrderItem> mDatas;
    private Fragment fragment;

    private OrderListListener mListener;

    public void setListener(OrderListListener listener) {
        this.mListener = listener;
    }

    public OrderListAdapter(Context cxt, List<OrderItem> datas, String status, Fragment fragment) {
        super(cxt);
        this.mDatas = datas;
        this.mStatus = status;
        rmb = cxt.getString(R.string.rmb);
        orderNumTip = cxt.getString(R.string.order_number_tip);
        goodsNumTip = cxt.getString(R.string.order_goods_num_tip);
        orderUpPay = cxt.getString(R.string.order_up_pay);
        orderOffPay = cxt.getString(R.string.order_off_pay);
        this.fragment = fragment;
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

                ImageUtils.showImage(fragment, goods.getProduct_image(), iv_goods);
                vh.llayout_goods.addView(v);
            }
        }
        // 设置状态底部按钮
        showBottom(vh, bean);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_order, null), mListener);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }

    // 设置状态底部按钮
    private void showBottom(ItemViewHolder vh, OrderItem bean) {
        // 列表状态为所有订单
        if (mStatus.equals(TypeConstant.Order.ALL)) {
            // 订单状态为待付款
            if (bean.getStatus().equals(TypeConstant.Order.UNPAY)) {
                vh.flayout_bottom.setVisibility(View.GONE);
            }
            // 订单状态为待发货
            else if (bean.getStatus().equals(TypeConstant.Order.PAYED)) {
                vh.flayout_bottom.setVisibility(View.VISIBLE);
                vh.tv_order_send.setVisibility(View.VISIBLE);
                vh.tv_order_finish.setVisibility(View.GONE);
                vh.tv_order_cancel.setVisibility(View.GONE);
            }
            // 订单状态为配送中
            else if (bean.getStatus().equals(TypeConstant.Order.DELIVERING)) {
                vh.flayout_bottom.setVisibility(View.VISIBLE);
                vh.tv_order_send.setVisibility(View.GONE);
                vh.tv_order_finish.setVisibility(View.GONE);
                vh.tv_order_cancel.setVisibility(View.VISIBLE);
            }
            // 订单状态为已完成
            else if (bean.getStatus().equals(TypeConstant.Order.CLOSED)) {
                vh.flayout_bottom.setVisibility(View.VISIBLE);
                vh.tv_order_send.setVisibility(View.GONE);
                vh.tv_order_finish.setVisibility(View.VISIBLE);
                vh.tv_order_cancel.setVisibility(View.GONE);
            }
        }
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
        TextView tv_order_send;

        public ItemViewHolder(View v, final OrderListListener listener) {
            super(v);
            tv_order_number = bind(v, R.id.tv_order_number);
            tv_order_time = bind(v, R.id.tv_order_time);
            tv_order_amount = bind(v, R.id.tv_order_amount);
            tv_wepay_method = bind(v, R.id.tv_wepay_method);
            llayout_goods = bind(v, R.id.llayout_goods);

            llayout_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onOrderDetailClick(getPosition());
                    }
                }
            });
            // 列表状态为全部
            if (mStatus.equals(TypeConstant.Order.ALL)) {
                flayout_bottom = bind(v, R.id.flayout_bottom);
                tv_order_finish = bind(v, R.id.tv_order_finish);
                tv_order_cancel = bind(v, R.id.tv_order_cancel);
                tv_order_send = bind(v, R.id.tv_order_send);

                tv_order_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderSendClick(getPosition());
                        }
                    }
                });

                tv_order_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderCancelClick(getPosition());
                        }
                    }
                });
            }
            // 列表状态为待发货
            else if (mStatus.equals(TypeConstant.Order.PAYED)) {
                flayout_bottom = bind(v, R.id.flayout_bottom);
                tv_order_send = bind(v, R.id.tv_order_send);
                flayout_bottom.setVisibility(View.VISIBLE);
                tv_order_send.setVisibility(View.VISIBLE);

                tv_order_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderSendClick(getPosition());
                        }
                    }
                });
            }
            // 列表状态为配送中
            else if (mStatus.equals(TypeConstant.Order.DELIVERING)) {
                flayout_bottom = bind(v, R.id.flayout_bottom);
                tv_order_cancel = bind(v, R.id.tv_order_cancel);
                flayout_bottom.setVisibility(View.VISIBLE);
                tv_order_cancel.setVisibility(View.VISIBLE);

                tv_order_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onOrderCancelClick(getPosition());
                        }
                    }
                });
            }
            // 列表状态为已完成
            else if (mStatus.equals(TypeConstant.Order.CLOSED)) {
                flayout_bottom = bind(v, R.id.flayout_bottom);
                tv_order_finish = bind(v, R.id.tv_order_finish);
                flayout_bottom.setVisibility(View.VISIBLE);
                tv_order_finish.setVisibility(View.VISIBLE);
            }
        }
    }
}
