package com.haiku.wateroffer.module.goods;

import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.module.base.BasePresenter;
import com.haiku.wateroffer.module.base.BaseView;

import java.util.List;

/**
 * 商品列表模块的Contract类
 * Created by hyming on 2016/7/12.
 */
public class GoodsListContract {
    interface View extends BaseView<Presenter> {
        // 显示列表界面
        void showListView(List<Goods> list);

        // 显示信息
        void showMessage(String msg);

    }

    interface Presenter extends BasePresenter {
        // 获取列表数据
        void getListDatas();
    }
}
