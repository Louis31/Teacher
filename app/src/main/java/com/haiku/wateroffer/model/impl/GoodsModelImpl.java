package com.haiku.wateroffer.model.impl;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.model.IGoodsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Goods Model实现类
 * Created by hyming on 2016/7/11.
 */
public class GoodsModelImpl implements IGoodsModel {

    // 获取商品列表
    @Override
    public void getGoodsList(@NonNull final GoodsListCallback callback) {


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = true;
                List<Goods> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Goods bean = new Goods();
                    list.add(bean);
                }

                if (isSuccess) {
                    callback.getListDataSuccess(list);
                } else {
                    callback.getListDataFail("");
                }
            }
        }, 2000);

    }
}
