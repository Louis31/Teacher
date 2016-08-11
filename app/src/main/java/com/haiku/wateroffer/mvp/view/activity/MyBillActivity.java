package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.data.StringUtils;
import com.haiku.wateroffer.common.util.ui.DialogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.MyBillContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.MyBillPresenter;
import com.haiku.wateroffer.mvp.view.adapter.BillListAdapter;
import com.haiku.wateroffer.mvp.view.divider.DividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的账单Activity
 * Created by hyming on 2016/7/15.
 */
@ContentView(R.layout.act_my_bill)
public class MyBillActivity extends BaseActivity implements MyBillContract.View {
    private final int TYPE_DETAIL = 0;
    private final int TYPE_OVERVIEW = 1;
    private int mType;

    private int uid;
    private int colorRed;
    private int colorBlack;

    private ProgressDialog mDialog;
    private List<Bill> mDatas;
    private BillListAdapter mAdapter;
    private MyBillContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.tv_tab_detail)
    private TextView tv_tab_detail;

    @ViewInject(R.id.tv_tab_overview)
    private TextView tv_tab_overview;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

    @ViewInject(R.id.llayout_bill_overview)
    private View llayout_bill_overview;

    @ViewInject(R.id.tv_start_time)
    private TextView tv_start_time;

    @ViewInject(R.id.tv_end_time)
    private TextView tv_end_time;

    @ViewInject(R.id.tv_deliver_name)
    private TextView tv_deliver_name;

    @Event(R.id.tv_tab_detail)
    private void tabDetailClick(View v) {
        changeTabView(TYPE_DETAIL);
    }

    @Event(R.id.tv_tab_overview)
    private void tabOverViewClick(View v) {
        changeTabView(TYPE_OVERVIEW);
    }

    // 选择开始时间
    @Event(R.id.llayout_start_time)
    private void startTimeClick(View v) {
        DialogUtils.showDateDialog(mContext, tv_start_time);
    }

    // 选择结束时间
    @Event(R.id.llayout_end_time)
    private void endTimeClick(View v) {
        DialogUtils.showDateDialog(mContext, tv_end_time);
    }

    // 选择配送员
    @Event(R.id.rlayout_deliver)
    private void deliverClick(View v) {
        // 跳转选择配送员界面
        startActivityForResult(new Intent(mContext, DeliverSelectActivity.class), 1);
    }

    // 开始查询
    @Event(R.id.btn_search)
    private void searchClick(View v) {
        //判断，若未选择配送员和时间段或者只选择了一个时间点，弹出提示: 请选择需要查询的时间段和配送员
        String time_none = getString(R.string.time_none);
        String start_time = tv_start_time.getText().toString();
        String end_time = tv_end_time.getText().toString();
        String deliver_name = tv_deliver_name.getText().toString();

        if (time_none.equals(start_time) || time_none.equals(end_time)
                || TextUtils.isEmpty(deliver_name)) {
            ToastUtils.getInstant().showToast(R.string.msg_bill_search_invalid);
        }  else if(end_time.compareTo(start_time)<0){
            ToastUtils.getInstant().showToast(R.string.msg_time_start_end_invalid);
        } else {
            // 查询数据
            showLoadingDialog(true);
            String deliver_id = (String) tv_deliver_name.getTag();
            mPresenter.searchBill(uid, start_time, end_time, deliver_id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        new MyBillPresenter(new UserModelImpl(), this);
        mPresenter.getListDatas(uid);
        // 查询当日的数据
        String currentDay = StringUtils.formatDate(new Date(), "yyyy-MM-dd");
        mPresenter.searchBill(uid, currentDay, currentDay, "");
    }

    @Override
    public void setPresenter(@NonNull MyBillContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mType = TYPE_DETAIL;
        colorRed = getResources().getColor(R.color.red);
        colorBlack = getResources().getColor(R.color.black);
        mDatas = new ArrayList<>();
        mAdapter = new BillListAdapter(mContext, mDatas);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.my_bill, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });

        mRefreshLayout.setPageSize(1000);
        mRefreshLayout.addItemDecoration(new DividerItem(mContext));
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
        mRefreshLayout.setLoadMoreEnable(false);
    }

    // 改变当前显示界面
    private void changeTabView(int type) {
        if (mType == type) {
            return;
        }
        // 账单明细
        if (type == TYPE_DETAIL) {
            tv_tab_detail.setTextColor(colorRed);
            tv_tab_overview.setTextColor(colorBlack);
            mRefreshLayout.setVisibility(View.VISIBLE);
            llayout_bill_overview.setVisibility(View.GONE);
        }
        // 账单概括
        else {
            tv_tab_overview.setTextColor(colorRed);
            tv_tab_detail.setTextColor(colorBlack);
            llayout_bill_overview.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        }
        mType = type;
    }

    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_searching));
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void showListView(List<Bill> list) {
        mDatas.addAll(list);
        mRefreshLayout.loadingCompleted(true);
    }

    @Override
    public void showSearchResult(Bill bean) {
        String rmb = getString(R.string.rmb);
        TextView tv_order_count = findView(llayout_bill_overview, R.id.tv_order_count);
        TextView tv_order_up_pay = findView(llayout_bill_overview, R.id.tv_order_up_pay);
        TextView tv_order_off_pay = findView(llayout_bill_overview, R.id.tv_order_off_pay);

        tv_order_count.setText(bean.getBillsNum());
        tv_order_up_pay.setText(rmb + bean.getOnLineAmount());
        tv_order_off_pay.setText(rmb + bean.getOffLineAmmount());
    }

    @Override
    public void showMessage(String msg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String deliver_id = data.getStringExtra("deliver_id");
            String deliver_name = data.getStringExtra("deliver_name");
            tv_deliver_name.setTag(deliver_id);
            tv_deliver_name.setText(deliver_name);
        }
    }
}
