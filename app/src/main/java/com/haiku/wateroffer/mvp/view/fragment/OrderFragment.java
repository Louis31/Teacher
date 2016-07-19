package com.haiku.wateroffer.mvp.view.fragment;

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
import com.haiku.wateroffer.mvp.view.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理Fragment
 * Created by hyming on 2016/7/6.
 */
public class OrderFragment extends Fragment {
    private String[] tabTitles;
    private List<Fragment> fragments;
    private FragmentAdapter mAdapter;

    private View rootView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private OrderListFragment allFragment; // 全部订单
    private OrderListFragment pendingPaymentFragment; // 待付款订单
    private OrderListFragment pendingSendFragment; // 待发货订单
    private OrderListFragment sendingFragment; // 配送中订单
    private OrderListFragment finishFragment; // 已完成订单

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
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
        tabTitles = res.getStringArray(R.array.order_tab);

        fragments = new ArrayList<Fragment>();
        allFragment = OrderListFragment.newInstance(TypeConstant.Order.ALL);
        pendingPaymentFragment = OrderListFragment.newInstance(TypeConstant.Order.UNPAY);
        pendingSendFragment = OrderListFragment.newInstance(TypeConstant.Order.PAYED);
        sendingFragment = OrderListFragment.newInstance(TypeConstant.Order.DELIVERING);
        finishFragment = OrderListFragment.newInstance(TypeConstant.Order.CLOSED);

        fragments.add(allFragment);
        fragments.add(pendingPaymentFragment);
        fragments.add(pendingSendFragment);
        fragments.add(sendingFragment);
        fragments.add(finishFragment);
    }

    // 初始化界面
    private void initViews() {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        mAdapter = new FragmentAdapter(getChildFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(0);
    }

}
