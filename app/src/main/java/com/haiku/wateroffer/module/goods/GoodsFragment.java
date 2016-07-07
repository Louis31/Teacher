package com.haiku.wateroffer.module.goods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.onekeydeliveryseller.R;
import com.haiku.wateroffer.module.base.LazyFragment;

/**
 * 商品管理Fragment
 * Created by hyming on 2016/7/6.
 */
public class GoodsFragment extends LazyFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, null);
    }

    @Override
    protected void lazyLoad() {

    }
}
