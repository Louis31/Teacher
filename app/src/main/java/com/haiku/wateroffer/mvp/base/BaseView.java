package com.haiku.wateroffer.mvp.base;

import android.support.annotation.NonNull;

/**
 * View基类接口
 * Created by hyming on 2016/7/5.
 */
public interface BaseView<T> {
    void setPresenter(@NonNull T presenter);
}
