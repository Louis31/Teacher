package com.haiku.wateroffer.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.common.OrderStatus;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ImageUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.OrderInfoContract;
import com.haiku.wateroffer.mvp.model.impl.OrderModelImpl;
import com.haiku.wateroffer.mvp.persenter.OrderInfoPersenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 订单详情Activity
 * Created by hyming on 2016/7/15.
 */
@ContentView(R.layout.act_order_info)
public class OrderInfoActivity extends BaseActivity implements OrderInfoContract.View {
    private final int TYPE_STATUS = 0;
    private final int TYPE_DETAIL = 1;
    private int mType;

    private int uid;
    private int order_id;
    private int colorRed;
    private int colorBlack;

    private String rmb;
    private String orderNumTip;
    private String goodsNumTip;

    private LayoutInflater mInflater;
    private OrderItem mOrderItem;

    private OrderInfoContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(value = R.id.tv_tab_status)
    private TextView tv_tab_status;

    @ViewInject(R.id.tv_tab_detail)
    private TextView tv_tab_detail;

    @ViewInject(R.id.llayout_order_detail)
    private View llayout_order_detail;

    @ViewInject(R.id.llayout_order_status)
    private View llayout_order_status;

    @ViewInject(R.id.flayout_loading)
    private View flayout_loading;

    // 订单详情底部状态
    @ViewInject(R.id.tv_order_finish)
    private TextView tv_order_finish;

    @ViewInject(R.id.tv_order_cancel)
    private TextView tv_order_cancel;

    @ViewInject(R.id.tv_order_send)
    private TextView tv_order_send;

    @Event(R.id.tv_tab_status)
    private void tabStatusClick(View v) {
        changeTabView(TYPE_STATUS);
    }

    @Event(R.id.tv_tab_detail)
    private void tabDetailClick(View v) {
        changeTabView(TYPE_DETAIL);
    }

    // TODO 订单详情底部状态点击事件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        new OrderInfoPersenter(new OrderModelImpl(), this);
        // 获取订单详情数据
        mPresenter.getOrderInfo(uid, order_id);
    }

    @Override
    public void setPresenter(@NonNull OrderInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initDatas() {
        mType = TYPE_STATUS;

        uid = UserManager.getInstance().getUser().getUid();
        order_id = getIntent().getIntExtra("order_id", -1);

        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);

        rmb = getString(R.string.rmb);
        orderNumTip = getString(R.string.order_number_tip);
        goodsNumTip = getString(R.string.order_goods_num_tip);

        mInflater = LayoutInflater.from(mContext);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.order_detail, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });
    }

    // 改变当前显示界面
    private void changeTabView(int type) {
        if (mType == type) {
            return;
        }
        // 订单状态
        if (type == TYPE_STATUS) {
            tv_tab_status.setTextColor(colorRed);
            tv_tab_detail.setTextColor(colorBlack);
            llayout_order_status.setVisibility(View.VISIBLE);
            llayout_order_detail.setVisibility(View.GONE);
        }
        // 订单详情
        else {
            tv_tab_detail.setTextColor(colorRed);
            tv_tab_status.setTextColor(colorBlack);
            llayout_order_detail.setVisibility(View.VISIBLE);
            llayout_order_status.setVisibility(View.GONE);
        }
        mType = type;
    }

    // 订单信息
    @Override
    public void setOrderInfo(OrderItem bean) {
        mOrderItem = bean;
        setStatusView();
        setInfoView();
        flayout_loading.setVisibility(View.GONE);
    }


    // 显示消息
    @Override
    public void showMessage(String msg) {
        flayout_loading.setVisibility(View.GONE);
        ToastUtils.getInstant().showToast(msg);
    }

    // 设置订单状态
    private void setStatusView() {
        // 订单有四种状态
        /**
         1.商家已接单：买家已付款或已经提交货到付款订单
         2.配送员已接单：配送员已经抢到单的状态
         3.订单完成：配送员已经完成配送，点击了确认送达按钮后的状态
         4.配送已取消：商家或者配送员点击了取消配送按钮，配送取消状态
         */
        String status = mOrderItem.getStatus();

        if (status.equals(TypeConstant.Order.PAYED) ||
                status.equals(TypeConstant.Order.DELIVERING)
                || status.equals(TypeConstant.Order.CLOSED)
                || status.equals(TypeConstant.Order.CANCELED)) {
            RelativeLayout rlayout_status_1 = findView(llayout_order_status, R.id.rlayout_status_1);
            ImageView iv_circle_1 = findView(llayout_order_status, R.id.iv_order_receive_circle);
            TextView tv_order_receive_time = findView(llayout_order_status, R.id.tv_order_receive_time);

            RelativeLayout rlayout_status_2 = findView(llayout_order_status, R.id.rlayout_status_2);
            ImageView iv_circle_2 = findView(llayout_order_status, R.id.iv_order_deliver_circle);
            TextView tv_order_deliver_name = findView(llayout_order_status, R.id.tv_order_deliver_name);
            TextView tv_order_deliver_phone = findView(llayout_order_status, R.id.tv_order_deliver_phone);
            TextView tv_order_deliver_time = findView(llayout_order_status, R.id.tv_order_deliver_time);

            RelativeLayout rlayout_status_3 = findView(llayout_order_status, R.id.rlayout_status_3);
            TextView tv_order_finish_time = findView(llayout_order_status, R.id.tv_order_finish_time);
            ImageView iv_circle_3 = findView(llayout_order_status, R.id.iv_order_finish_circle);

            RelativeLayout rlayout_status_4 = findView(llayout_order_status, R.id.rlayout_status_4);
            TextView tv_order_cancel_time = findView(llayout_order_status, R.id.tv_order_cancel_time);
            ImageView iv_circle_4 = findView(llayout_order_status, R.id.iv_order_cancel_circle);

            mOrderItem.setOrderStatus();
            int ordinal = mOrderItem.getOrderStatus().ordinal();
            if (ordinal >= OrderStatus.PAYED.ordinal()) {
                iv_circle_1.setImageResource(R.drawable.ic_circle_green);
                tv_order_receive_time.setText(mOrderItem.getOrder_time());
                rlayout_status_1.setVisibility(View.VISIBLE);
            }
            if (ordinal >= OrderStatus.DELIVERING.ordinal()) {
                iv_circle_1.setImageResource(R.drawable.ic_circle);
                iv_circle_2.setImageResource(R.drawable.ic_circle_green);

                tv_order_deliver_name.setText(getString(R.string.order_delivername_tip) + mOrderItem.getAddress_info().getUname());
                tv_order_deliver_phone.setText(getString(R.string.phone_tip) + mOrderItem.getAddress_info().getPhone());
                tv_order_deliver_time.setText(mOrderItem.getSend_time());

                rlayout_status_2.setVisibility(View.VISIBLE);
            }

            if (ordinal == OrderStatus.CLOSED.ordinal()) {
                iv_circle_1.setImageResource(R.drawable.ic_circle);
                iv_circle_2.setImageResource(R.drawable.ic_circle);
                iv_circle_3.setImageResource(R.drawable.ic_circle_green);
                tv_order_finish_time.setText(mOrderItem.getExptime());

                rlayout_status_3.setVisibility(View.VISIBLE);
            } else if (ordinal == OrderStatus.CANCELED.ordinal()) {
                iv_circle_1.setImageResource(R.drawable.ic_circle);
                iv_circle_2.setImageResource(R.drawable.ic_circle);
                iv_circle_4.setImageResource(R.drawable.ic_circle_green);
                tv_order_cancel_time.setText(mOrderItem.getExptime());

                rlayout_status_4.setVisibility(View.VISIBLE);
            }
        }
    }

    // 设置订单详情
    private void setInfoView() {
        TextView tv_order_number = findView(llayout_order_detail, R.id.tv_order_number);
        TextView tv_order_status = findView(llayout_order_detail, R.id.tv_order_status);
        TextView tv_order_time = findView(llayout_order_detail, R.id.tv_order_time);
        TextView tv_deliver_name = findView(llayout_order_detail, R.id.tv_deliver_name);
        TextView tv_deliver_phone = findView(llayout_order_detail, R.id.tv_deliver_phone);
        TextView tv_deliver_addr = findView(llayout_order_detail, R.id.tv_deliver_addr);
        TextView tv_deliver_time = findView(llayout_order_detail, R.id.tv_deliver_time);
        TextView tv_deliver_remark = findView(llayout_order_detail, R.id.tv_deliver_remark);
        LinearLayout llayout_goods = findView(llayout_order_detail, R.id.llayout_goods);
        FrameLayout flayout_bottom = findView(llayout_order_detail, R.id.flayout_bottom);

        // 设置订单状态
        setOrderStatus(tv_order_status);
        tv_order_number.setText(mOrderItem.getSerial_number());
        tv_order_time.setText(mOrderItem.getOrder_time());
        tv_deliver_name.setText(mOrderItem.getAddress_info().getUname());
        tv_deliver_phone.setText(mOrderItem.getAddress_info().getPhone());
        tv_deliver_addr.setText(mOrderItem.getAddress_info().getAddrs());
        tv_deliver_time.setText(mOrderItem.getExptime());
        tv_deliver_remark.setText(mOrderItem.getRemark());

        // 设置订单商品列表
        if (mOrderItem.getDetails() != null && mOrderItem.getDetails().size() > 0) {
            for (int i = 0; i < mOrderItem.getDetails().size(); i++) {
                Goods goods = mOrderItem.getDetails().get(i);
                View v = mInflater.inflate(R.layout.item_order_goods, null);

                ImageView iv_goods = findView(v, R.id.iv_goods);
                TextView tv_goods_name = findView(v, R.id.tv_goods_name);
                TextView tv_goods_price = findView(v, R.id.tv_goods_price);
                TextView tv_goods_count = findView(v, R.id.tv_goods_count);

                tv_goods_name.setText(goods.getProduct_name());
                tv_goods_price.setText(rmb + goods.getSell_price());
                tv_goods_count.setText(goodsNumTip + goods.getProduct_count());

                ImageUtils.showImage(OrderInfoActivity.this, goods.getProduct_image(), iv_goods);
                llayout_goods.addView(v);
            }
        }
        // 设置状态底部按钮
        showBottom(flayout_bottom);
    }

    // 设置订单底部按钮
    private void showBottom(View flayout_bottom) {
        String status = mOrderItem.getStatus();
        // 订单状态为待付款
        if (status.equals(OrderStatus.UNPAY.getStatus())) {
            flayout_bottom.setVisibility(View.GONE);
        }
        // 订单状态为待发货
        else if (status.equals(OrderStatus.PAYED.getStatus())) {
            flayout_bottom.setVisibility(View.VISIBLE);
            tv_order_send.setVisibility(View.VISIBLE);
            tv_order_finish.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.GONE);

            tv_order_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.sendOrder();
                }
            });
        }
        // 订单状态为配送中
        else if (status.equals(OrderStatus.DELIVERING.getStatus())) {
            flayout_bottom.setVisibility(View.VISIBLE);
            tv_order_send.setVisibility(View.GONE);
            tv_order_finish.setVisibility(View.GONE);
            tv_order_cancel.setVisibility(View.VISIBLE);

            tv_order_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.cancelOrder();
                }
            });
        }
        // 订单状态为已完成
        else if (status.equals(OrderStatus.CLOSED.getStatus())) {
            flayout_bottom.setVisibility(View.VISIBLE);
            tv_order_send.setVisibility(View.GONE);
            tv_order_finish.setVisibility(View.VISIBLE);
            tv_order_cancel.setVisibility(View.GONE);
        }
    }

    // 设置订单状态
    private void setOrderStatus(TextView tv) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getStatus().equals(mOrderItem.getStatus())) {
                tv.setText(status.getName());
            }
        }
    }
}
