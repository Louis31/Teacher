package com.haiku.wateroffer.module.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.onekeydeliveryseller.R;
import com.haiku.wateroffer.module.base.LazyFragment;

/**
 * 订单管理Fragment
 * Created by hyming on 2016/7/6.
 */
public class OrderFragment extends LazyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, null);
    }

    @Override
    protected void lazyLoad() {

    }
}
