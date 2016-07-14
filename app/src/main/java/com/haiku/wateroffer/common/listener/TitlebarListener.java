package com.haiku.wateroffer.common.listener;

/**
 * 标题栏事件监听监听
 * Created by hyming on 2016/7/14.
 */
public interface TitlebarListener {
    // 后退图标点击
    void onReturnIconClick();

    // 保存按钮
    void onSaveTextClick();

    // 添加图标点击
    void onAddIconClick();
}
