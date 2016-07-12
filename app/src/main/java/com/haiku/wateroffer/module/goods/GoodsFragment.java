package com.haiku.wateroffer.module.goods;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.ui.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理Fragment
 * Created by hyming on 2016/7/6.
 */
public class GoodsFragment extends Fragment {
    private String[] tabTitles;
    private List<Fragment> fragments;
    private FragmentAdapter mAdapter;

    private View rootView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private GoodsListFragment onSaleFragment; // 出售中
    private GoodsListFragment saleNoneFragment;// 已售罄
    private GoodsListFragment offShelfFragment;// 已下架

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_goods, container, false);
            initDatas();
            initViews();
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    // 初始化数据
    private void initDatas() {
        Resources res = getResources();
        tabTitles = res.getStringArray(R.array.goods_tab);

        fragments = new ArrayList<Fragment>();
        onSaleFragment = GoodsListFragment.newInstance(TypeConstant.Goods.ON_SALE);
        saleNoneFragment = GoodsListFragment.newInstance(TypeConstant.Goods.SALE_NONE);
        offShelfFragment = GoodsListFragment.newInstance(TypeConstant.Goods.OFF_SHELF);

        fragments.add(onSaleFragment);
        fragments.add(saleNoneFragment);
        fragments.add(offShelfFragment);
    }

    // 初始化界面
    private void initViews() {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        mAdapter = new FragmentAdapter(getChildFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
