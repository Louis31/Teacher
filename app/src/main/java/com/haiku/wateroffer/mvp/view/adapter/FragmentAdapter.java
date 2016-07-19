package com.haiku.wateroffer.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * 订单Fragment Adapter
 * Created by hyming on 2016/7/11.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;                              //tab名的列表
    private List<Fragment> fragments;                         //fragment列表

    public FragmentAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
