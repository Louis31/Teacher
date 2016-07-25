package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.DeliverListListener;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.data.ValidatorUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.TypeConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.DeliverContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.DeliverPresenter;
import com.haiku.wateroffer.mvp.view.adapter.DeliverListAdapter;
import com.haiku.wateroffer.mvp.view.dialog.AddDeliverDialog;
import com.haiku.wateroffer.mvp.view.dialog.IOSAlertDialog;
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

    private AddDeliverDialog mAddDeliverDialog;

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

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

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
        mPresenter.getDeliverList(UserManager.getInstance().getUser().getUid());
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

        mListAdapter.setListener(this);
        mEditAdapter.setListener(this);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.delivery_list, true);
        mTitlebar.initAddIcon();
        mTitlebar.showAddIcon(true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onAddIconClick() {
                // 显示添加配送员界面
                showAddDeliverView();
            }
        });

        mRefreshLayout.setPageSize(1000);
        mRefreshLayout.setAdapter(mListAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
        mRefreshLayout.setLoadMoreEnable(false);
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
            mRefreshLayout.setAdapter(mListAdapter);
        }
        // 编辑列表
        else {
            tv_tab_edit.setTextColor(colorRed);
            tv_tab_deliver.setTextColor(colorBlack);
            mRefreshLayout.setAdapter(mEditAdapter);
        }
        mType = type;
    }

    // 显示/隐藏加载对话框
    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_submiting));
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
        for (Deliver bean : mDatas) {
            if (bean.getDiliveryman_status() == TypeConstant.Deliver.DELETE) {
                mDatas.remove(bean);
            }
        }
        mListAdapter.notifyDataSetChanged();
        mEditAdapter.notifyDataSetChanged();
        mRefreshLayout.loadingCompleted(true);
    }

    // 更新列表界面
    @Override
    public void updateListView(Deliver bean) {
        if (mAddDeliverDialog != null && mAddDeliverDialog.isShowing()) {
            mAddDeliverDialog.dismiss();
        }
        if (bean != null) {
            mDatas.add(bean);
            mListAdapter.notifyItemInserted(mDatas.size() - 1);
            mEditAdapter.notifyItemInserted(mDatas.size() - 1);
        } else {
            if (mOperationType == TypeConstant.Deliver.DELETE) {
                mDatas.remove(mCurrentPos);
                mListAdapter.notifyItemRemoved(mCurrentPos);
                mEditAdapter.notifyItemRemoved(mCurrentPos);
            } else {
                mListAdapter.notifyItemChanged(mCurrentPos);
                mEditAdapter.notifyItemChanged(mCurrentPos);
            }
        }
    }

    // 显示信息
    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    private void showAddDeliverView() {
        mAddDeliverDialog = new AddDeliverDialog(mContext).builder();
        mAddDeliverDialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mAddDeliverDialog.getInputText();
                if (!ValidatorUtils.isMobile(phone)) {
                    ToastUtils.getInstant().showToast(R.string.msg_phone_invalid);
                } else {
                    // 添加配送员
                    mPresenter.addDeliver(UserManager.getInstance().getUser().getUid(), phone);
                }
            }
        }).show();
    }

    /**
     * 回调事件
     */
    @Override
    public void onItemClick(int pos) {
        // TODO 跳转到配送员订单页面
        Intent intent = new Intent(mContext, DeliverOrderActivity.class);
        intent.putExtra("deliver_id", mDatas.get(pos).getDiliveryman_id());
        intent.putExtra("deliver_name", mDatas.get(pos).getDiliveryman_name());
        startActivity(intent);
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
    public void onDeleteClick(final int pos) {
        new IOSAlertDialog(mContext).builder().setMsg(getString(R.string.dlg_deliver_delete))
                .setCancelable(false)
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 删除点击
                        mCurrentPos = pos;
                        mOperationType = TypeConstant.Deliver.DELETE;
                        mPresenter.changeDeliverStatus(mDatas.get(pos).getDiliveryman_id(), mDatas.get(pos).getDiliveryman_phone(), mOperationType);
                    }
                }).setNegativeButton("否", null).show();
    }
}

