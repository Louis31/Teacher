package com.haiku.wateroffer.module.base;

import android.support.v4.app.Fragment;

/**
 * 懒加载的Fragment（只有用户可见了，才加载数据和布局）
 * Created by hyming on 2016/6/23.
 */
public abstract class LazyFragment extends Fragment {

    /*Fragment当前状态是否可见*/
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }


    /**
     * 可见
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    private void onInVisible() {

    }

    /**
     * 延迟加载
     */
    protected abstract void lazyLoad();
}
