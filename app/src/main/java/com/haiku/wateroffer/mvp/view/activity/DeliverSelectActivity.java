package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.MyItemClickListener;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IUserModel;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.view.adapter.DeliverSelectAdapter;
import com.haiku.wateroffer.mvp.view.divider.DividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择配送员界面
 * Created by hyming on 2016/7/18.
 */
@ContentView(R.layout.act_common_list)
public class DeliverSelectActivity extends BaseActivity implements IUserModel.DeliverSelectCallback, MyItemClickListener {

    private int uid;
    private List<Deliver> mDatas;
    private Map<String, Object> mTempParams;// 存储当前请求的参数
    private DeliverSelectAdapter mAdapter;

    private IUserModel mUserModel;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        if (!isTokenFail(params)) {
            mUserModel.getDeliverList(params, this);
        }

    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mDatas = new ArrayList<>();
        mAdapter = new DeliverSelectAdapter(mContext, mDatas);
        mAdapter.setListener(this);
        mUserModel = new UserModelImpl();
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.choose_deliver, true);
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

    @Override
    public void getDeliverListSuccess(List<Deliver> list) {
        mDatas.addAll(list);
        mRefreshLayout.loadingCompleted(true);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mUserModel.getDeliverList(params, this);
    }

    // 成功回调
    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mRefreshLayout.loadingCompleted(false);
        ToastUtils.getInstant().showToast(errorMsg);
    }

    @Override
    public void onTokenFail() {
        UserManager.cleanToken();
        ((IBaseModel) mUserModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mUserModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(int pos) {
        String deliver_id = mDatas.get(pos).getDiliveryman_id() + "";
        String deliver_name = mDatas.get(pos).getDiliveryman_name();
        Intent intent = new Intent();
        intent.putExtra("deliver_id", deliver_id);
        intent.putExtra("deliver_name", deliver_name);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
