package com.haiku.wateroffer.common.listener;

/**
 * 配送列表相关事件
 * Created by hyming on 2016/7/20.
 */
public interface DeliverListListener {
    // item点击
    void onItemClick(int pos);

    // 暂停/继续操作
    void onContinuePauseClick(int pos);

    // 删除操作
    void onDeleteClick(int pos);
}
