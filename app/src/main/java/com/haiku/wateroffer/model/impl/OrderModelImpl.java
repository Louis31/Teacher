package com.haiku.wateroffer.model.impl;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.model.IOrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Order Model实现类
 * Created by hyming on 2016/7/11.
 */
public class OrderModelImpl implements IOrderModel {

    // 获取订单列表
    @Override
    public void getOrderList(@NonNull final OrderListCallback callback) {


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = true;
                List<OrderItem> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    OrderItem bean = new OrderItem();
                    list.add(bean);
                }

                if (isSuccess) {
                    callback.getListDataSuccess(list);
                } else {
                    callback.getListDataFail("");
                }
            }
        },2000);

    }
}
