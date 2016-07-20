package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.OrderItem;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 配送列表的Contract
 * Created by hyming on 2016/7/20.
 */
public interface DeliverContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示列表界面
        void showListView(List<Deliver> list);

        // 更新列表界面
        void updateListView();

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getDeliverList(int uid);

        // 修改配送员状态
        void changeDeliverStatus(int diliveryman_id, String phone, int status);
    }
}
