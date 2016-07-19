package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 我的账单Contract
 * Created by hyming on 2016/7/18.
 */
public interface MyBillContract {
    interface View extends BaseView<Presenter> {
        // 显示列表界面
        void showListView(List<Bill> list);

        // 显示查询结果
        void showSearchResult(Bill bean);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取账单列表
        void getListDatas(int uid);

        // 查询账单
        void searchBill(int uid, String from, String to, String diliverymanName);
    }
}
