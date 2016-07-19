package com.haiku.wateroffer.mvp.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.common.listener.GoodsListListener;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.constant.TypeConstant;

import java.util.List;

/**
 * 商品列表Adapter
 * Created by hyming on 2016/7/12.
 */
public class GoodsListAdapter extends MyBaseAdapter {

    private int mType;
    private String rmb;
    private String goodsSaleTip;
    private String goodsStockTip;

    private List<Goods> mDatas;
    private Fragment fragment;

    private GoodsListListener mListener;

    private void setListener(GoodsListListener listener) {
        this.mListener = listener;
    }

    public GoodsListAdapter(Context cxt, List<Goods> datas, int type, Fragment fragment) {
        super(cxt);
        this.mDatas = datas;
        this.mType = type;
        rmb = cxt.getString(R.string.rmb);
        goodsSaleTip = cxt.getString(R.string.goods_sale_tip);
        goodsStockTip = cxt.getString(R.string.goods_stock_tip);
        this.fragment = fragment;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_goods, null), mListener);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        Goods bean = mDatas.get(position);
        ItemViewHolder vh = (ItemViewHolder) holder;

        vh.tv_goods_name.setText(bean.getProduct_name());
        vh.tv_goods_price.setText(rmb + bean.getSell_price());
        vh.tv_goods_sale.setText(goodsSaleTip);
        vh.tv_goods_stock.setText(goodsStockTip + bean.getProduct_instocks());
        ImageUtils.showImage(fragment, bean.getProduct_image(), vh.iv_goods);
    }

    @Override
    protected int getDataItemCount() {
        return mDatas.size();
    }

    class ItemViewHolder extends ViewHolder {
        TextView tv_goods_name;
        TextView tv_goods_price;
        TextView tv_goods_sale;
        TextView tv_goods_stock;
        ImageView iv_goods;
        TextView tv_delete;
        TextView tv_off_shelf;
        TextView tv_up_shelf;
        TextView tv_edit;

        public ItemViewHolder(View v, final GoodsListListener listener) {
            super(v);
            tv_goods_name = bind(v, R.id.tv_goods_name);
            tv_goods_price = bind(v, R.id.tv_goods_price);
            tv_goods_sale = bind(v, R.id.tv_goods_sale);
            tv_goods_stock = bind(v, R.id.tv_goods_stock);
            iv_goods = bind(v, R.id.iv_goods);

            tv_delete = bind(v, R.id.tv_delete);
            tv_off_shelf = bind(v, R.id.tv_off_shelf);
            tv_up_shelf = bind(v, R.id.tv_up_shelf);
            tv_edit = bind(v, R.id.tv_edit);

            if (mType == TypeConstant.Goods.SALE_NONE) {
                tv_off_shelf.setVisibility(View.GONE);
            } else {
                tv_up_shelf.setVisibility(View.GONE);
            }

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onGoodsDeleteClick(getPosition());
                    }
                }
            });

            tv_up_shelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onGoodsUpShelfClick(getPosition());
                    }
                }
            });

            tv_off_shelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onGoodsOffShelfClick(getPosition());
                    }
                }
            });

            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onGoodsEditClick(getPosition());
                    }
                }
            });
        }
    }
}
