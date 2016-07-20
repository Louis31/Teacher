package com.haiku.wateroffer.mvp.persenter;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.mvp.contract.DeliverContract;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 配送列表的Presenter
 * Created by hyming on 2016/7/20.
 */
public class DeliverPresenter implements DeliverContract.Presenter {
    private final int REQUEST_LIST = 1;
    private final int REQUEST_CHANGE = 2;
    private int requesType;

    @NonNull
    private final IUserModel mUserModel;
    @NonNull
    private final DeliverContract.View mView;

    public DeliverPresenter(@NonNull IUserModel userModel, @NonNull DeliverContract.View view) {
        this.mUserModel = userModel;
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * Presenter
     */
    @Override
    public void getDeliverList(int uid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);

        if (UserManager.isTokenEmpty()) {
            requesType = REQUEST_LIST;
            // 获取token
            ((IBaseModel) mUserModel).getAccessToken(params, this);
        } else {
            mUserModel.getDeliverList(params, this);
        }
    }

    @Override
    public void changeDeliverStatus(int diliveryman_id, String phone, int status) {

    }
    /**
     * Callback
     */
}
