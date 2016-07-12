package com.haiku.wateroffer.module.order;

import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.module.base.BasePresenter;
import com.haiku.wateroffer.module.base.BaseView;

import java.util.List;

/**
 * 订单列表模块的Contract类
 * Created by hyming on 2016/7/11.
 */
public class OrderListContract {
    interface View extends BaseView<Presenter> {
        // 显示列表界面
        void showListView(List<OrderItem> list);

        // 显示信息
        void showMessage(String msg);

    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getListDatas();
    }
}
