package com.haiku.wateroffer.mvp.view.activity;

import android.os.Bundle;

import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Contribution;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.view.adapter.ContributionAdapter;
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
 * 商家贡献值界面
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_contribution)
public class ContributionActivity extends BaseActivity implements IShopModel.IContributionCallback {

    private int uid;
    private Map<String, Object> mTempParams;// 存储当前请求的参数

    private List<Contribution> mDatas;
    private ContributionAdapter mAdapter;

    private IShopModel mShopModel;

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
        params.put("id", uid);
        if (!isTokenFail(params)) {
            mShopModel.getContribution(params, this);
        }
    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        mDatas = new ArrayList<>();
        mAdapter = new ContributionAdapter(mContext, mDatas);
        mShopModel = new ShopModelImpl();
    }


    private void initViews() {
        mTitlebar.initDatas(R.string.contribution, true);
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
    public void getContributionSuccess(List<Contribution> list) {
        mDatas.addAll(list);
        mRefreshLayout.loadingCompleted(true);
    }

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mShopModel.getContribution(params, this);
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
        ((IBaseModel) mShopModel).getAccessToken(mTempParams, this);
    }

    // 判断是否为token失效
    private boolean isTokenFail(Map<String, Object> params) {
        if (UserManager.isTokenEmpty()) {
            mTempParams = params;
            ((IBaseModel) mShopModel).getAccessToken(params, this);
            return true;
        }
        return false;
    }
}
