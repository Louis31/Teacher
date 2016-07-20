package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.listener.DeliverListListener;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.DeliverContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.DeliverPresenter;
import com.haiku.wateroffer.mvp.view.adapter.DeliverListAdapter;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 配送列表Activity
 * Created by hyming on 2016/7/16.
 */
@ContentView(R.layout.act_deliver_list)
public class DeliverListActivity extends BaseActivity implements DeliverContract.View, DeliverListListener {
    private final int TYPE_DELIVER = 0;
    private final int TYPE_EDIT = 1;
    private int mType;

    private int mOperationType;

    private int colorRed;
    private int colorBlack;
    private int mCurrentPos;
    private boolean isClicking;


    private List<Deliver> mDatas;
    private DeliverListAdapter mListAdapter;// 配送员列表Adapter
    private DeliverListAdapter mEditAdapter;// 编辑列表Adapter

    private ProgressDialog mDialog;
    private DeliverContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(value = R.id.tv_tab_deliver)
    private TextView tv_tab_deliver;

    @ViewInject(R.id.tv_tab_edit)
    private TextView tv_tab_edit;

    @ViewInject(R.id.refresh_layout_edit)
    private MyRefreshLayout refresh_layout_edit;

    @ViewInject(R.id.refresh_layout_deliver)
    private MyRefreshLayout refresh_layout_deliver;

    @Event(R.id.tv_tab_deliver)
    private void tabDeliverClick(View v) {
        changeTabView(TYPE_DELIVER);
    }

    @Event(R.id.tv_tab_edit)
    private void tabEditClick(View v) {
        changeTabView(TYPE_EDIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        new DeliverPresenter(new UserModelImpl(), this);
    }

    @Override
    public void setPresenter(@NonNull DeliverContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initDatas() {
        mType = TYPE_DELIVER;
        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);

        mDatas = new ArrayList<>();
        mListAdapter = new DeliverListAdapter(mContext, mDatas, DeliverListAdapter.TYPE_LIST);
        mEditAdapter = new DeliverListAdapter(mContext, mDatas, DeliverListAdapter.TYPE_EDIT);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.delivery_list, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });

        refresh_layout_deliver.setPageSize(1000);
        refresh_layout_deliver.setAdapter(mListAdapter);
        refresh_layout_deliver.setLinearLayout();
        refresh_layout_deliver.setPullRefreshEnable(false);
        refresh_layout_deliver.setLoadMoreEnable(false);

        refresh_layout_edit.setPageSize(1000);
        refresh_layout_edit.setAdapter(mEditAdapter);
        refresh_layout_edit.setLinearLayout();
        refresh_layout_edit.setPullRefreshEnable(false);
        refresh_layout_edit.setLoadMoreEnable(false);
    }

    // 改变当前显示界面
    private void changeTabView(int type) {
        if (mType == type) {
            return;
        }
        // 配送员列表
        if (type == TYPE_DELIVER) {
            tv_tab_deliver.setTextColor(colorRed);
            tv_tab_edit.setTextColor(colorBlack);
            refresh_layout_deliver.setVisibility(View.VISIBLE);
            refresh_layout_edit.setVisibility(View.GONE);
        }
        // 编辑列表
        else {
            tv_tab_edit.setTextColor(colorRed);
            tv_tab_deliver.setTextColor(colorBlack);
            refresh_layout_edit.setVisibility(View.VISIBLE);
            refresh_layout_deliver.setVisibility(View.GONE);
        }
        mType = type;
    }

    // 显示/隐藏加载对话框
    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_updating));
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    // 显示列表界面
    @Override
    public void showListView(List<Deliver> list) {
        mDatas.addAll(list);
        mListAdapter.notifyDataSetChanged();
        mEditAdapter.notifyDataSetChanged();
        refresh_layout_deliver.loadingCompleted(true);
        refresh_layout_edit.loadingCompleted(true);
    }

    // 更新列表界面
    @Override
    public void updateListView() {
        if (mOperationType == TypeConstant.Deliver.DELETE) {
            mDatas.remove(mCurrentPos);
            mListAdapter.notifyItemRemoved(mCurrentPos);
            mEditAdapter.notifyItemRemoved(mCurrentPos);
        } else {
            mListAdapter.notifyItemChanged(mCurrentPos);
            mEditAdapter.notifyItemChanged(mCurrentPos);
        }
    }

    // 显示信息
    @Override
    public void showMessage(String msg) {
        refresh_layout_deliver.loadingCompleted(false);
        refresh_layout_edit.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    /**
     * 回调事件
     */
    @Override
    public void onItemClick(int pos) {
        // TODO 跳转到配送员订单页面

    }

    @Override
    public void onContinuePauseClick(int pos) {
        // 暂停/继续点击
        mCurrentPos = pos;
        Deliver bean = mDatas.get(pos);
        if (bean.getDiliveryman_status() == TypeConstant.Deliver.PAUSE) {
            mOperationType = TypeConstant.Deliver.CONTINUE;
        } else {
            mOperationType = TypeConstant.Deliver.PAUSE;
        }
        mPresenter.changeDeliverStatus(bean.getDiliveryman_id(), bean.getDiliveryman_phone(), mOperationType);
    }

    @Override
    public void onDeleteClick(int pos) {
        // 删除点击
        mCurrentPos = pos;
        mOperationType = TypeConstant.Deliver.DELETE;
        mPresenter.changeDeliverStatus(mDatas.get(pos).getDiliveryman_id(), mDatas.get(pos).getDiliveryman_phone(), mOperationType);
    }
}

