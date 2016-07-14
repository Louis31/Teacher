package com.haiku.wateroffer.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.util.ui.ImageUtils;

import java.util.List;

/**
 * 订单列表Adapter
 * Created by hyming on 2016/7/11.
 */
public class OrderListAdapter extends MyBaseAdapter {
    // item相关字符串
    private String rmb;
    private String orderNumTip;
    private String goodsNumTip;

    private List<OrderItem> mDatas;
    private Fragment fragment;

    public OrderListAdapter(Context cxt, List<OrderItem> datas, Fragment fragment) {
        super(cxt);
        this.mDatas = datas;
        rmb = cxt.getString(R.string.rmb);
        orderNumTip = cxt.getString(R.string.order_number_tip);
        goodsNumTip = cxt.getString(R.string.order_goods_num_tip);
        this.fragment = fragment;
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        OrderItem bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;
        // 设置订单数据
        vh.tv_order_number.setText(orderNumTip + bean.getOrder_id());
        vh.tv_order_time.setText(bean.getOrder_time());
        vh.tv_order_amount.setText(rmb + bean.getOrder_amount());
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
        TextView tv_order_number;
        TextView tv_order_time;
        TextView tv_order_amount;
        LinearLayout llayout_goods;

        public ItemViewHolder(View v) {
            super(v);
            tv_order_number = bind(v, R.id.tv_order_number);
            tv_order_time = bind(v, R.id.tv_order_time);
            tv_order_amount = bind(v, R.id.tv_order_amount);
            llayout_goods = bind(v, R.id.llayout_goods);
        }
    }
}
