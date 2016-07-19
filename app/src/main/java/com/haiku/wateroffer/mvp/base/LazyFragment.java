package com.haiku.wateroffer.mvp.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 懒加载的Fragment（只有用户可见了，才加载数据和布局）
 * Created by hyming on 2016/6/23.
 */
public abstract class LazyFragment extends Fragment {

    /*Fragment当前状态是否可见*/
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isFirstLoad = true;

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

    // 绑定view id
    public <T extends View> T findView(View v, int viewId) {
        View view = v.findViewById(viewId);
        return (T) view;
    }
}
