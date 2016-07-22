package com.haiku.wateroffer.mvp.contract;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.mvp.base.BasePresenter;
import com.haiku.wateroffer.mvp.base.BaseView;

import java.util.List;

/**
 * 商品列表模块的Contract类
 * Created by hyming on 2016/7/12.
 */
public interface GoodsListContract {
    interface View extends BaseView<Presenter> {
        // 显示/隐藏加载对话框
        void showLoadingDialog(boolean isShow);

        // 显示列表界面
        void showListView(List<Goods> list);

        // 更新列表界面
        void refreshListView(int type);

        // 显示信息
        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getListDatas(int uid, int status, int pageno);

        // 删除商品
        void deleteGoods(int uid, int product_id);

        // 下架商品
        void offShelfGoods(int uid, int product_id);
    }
}
