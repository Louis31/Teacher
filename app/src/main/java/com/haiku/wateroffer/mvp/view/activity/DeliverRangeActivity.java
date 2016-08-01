package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.model.IBaseModel;
import com.haiku.wateroffer.mvp.model.IShopModel;
import com.haiku.wateroffer.mvp.model.impl.ShopModelImpl;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 配送范围Activity
 * Created by hyming on 2016/7/27.
 */
@ContentView(R.layout.act_deliver_range)
public class DeliverRangeActivity extends BaseActivity implements IRequestCallback {
    private AlertDialog mRangeDialog;
    private ProgressDialog mDialog;
    private IShopModel mShopModel;

    private AMap aMap;
    private MarkerOptions markerOption;
    private int mChoiceWhich;
    private int zoomLevel;
    private String mRadius;
    private Circle mCircle;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.tv_range)
    private TextView tv_range;


    @ViewInject(R.id.map)
    private MapView mMapView;

    @Event(R.id.rlayout_range)
    private void rangeClick(View v) {
        if (mRangeDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("配送范围");
            final ChoiceOnClickListener choiceListener =
                    new ChoiceOnClickListener();
            builder.setSingleChoiceItems(R.array.deliver_range, mChoiceWhich, choiceListener);

            DialogInterface.OnClickListener btnListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            int choiceWhich = choiceListener.getWhich();
                            mChoiceWhich = choiceWhich;
                            mRadius =
                                    getResources().getStringArray(R.array.deliver_range)[choiceWhich];
                            tv_range.setText(mRadius);
                            mCircle.setRadius(Double.valueOf(mRadius) * 1000);
                            aMap.invalidate();
                        }
                    };
            builder.setPositiveButton("确定", btnListener);
            mRangeDialog = builder.create();
        }
        mRangeDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        initLocation();
        mShopModel = new ShopModelImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    private void initView() {
        mTitlebar.initDatas(R.string.delivery_range, true);
        mTitlebar.showRightTextView(getString(R.string.save));
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }

            @Override
            public void onRightTextClick() {
                showLoadingDialog(true);
                int uid = UserManager.getInstance().getUser().getUid();
                String range = tv_range.getText().toString();
                Map<String, Object> params = new HashMap<>();
                params.put("id", uid);
                params.put("range", range);
                if (UserManager.isTokenEmpty()) {
                    ((IBaseModel) mShopModel).getAccessToken(params, DeliverRangeActivity.this);
                } else {
                    mShopModel.changeShopRange(params, DeliverRangeActivity.this);
                }
            }
        });
        mRadius = getIntent().getStringExtra("range");
        tv_range.setText(mRadius);

        String[] ranges = getResources().getStringArray(R.array.deliver_range);
        for (int i = 0; i < ranges.length; i++) {
            if (ranges[i].equals(mRadius)) {
                mChoiceWhich = i;
            }
        }
    }

    private void initLocation() {
        zoomLevel = 20;
        double lat = getIntent().getDoubleExtra("latitude", 0);
        double lon = getIntent().getDoubleExtra("longitude", 0);
        LatLng latLng = new LatLng(lat, lon);

        aMap = mMapView.getMap();
        markerOption = new MarkerOptions();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        markerOption.position(latLng);
        aMap.addMarker(markerOption);

        // 绘制圆形
        mCircle = aMap.addCircle(new CircleOptions().center(latLng)
                .radius(Double.valueOf(mRadius) * 1000).strokeColor(getResources().getColor(R.color.map_stroke)).fillColor(getResources().getColor(R.color.map_radius))
                .strokeWidth(3));
    }

    private class ChoiceOnClickListener implements DialogInterface.OnClickListener {
        private int which = 0;

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            this.which = which;
        }

        public int getWhich() {
            return which;
        }
    }

    // 显示/隐藏加载对话框
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

    @Override
    public void getTokenSuccess(Map<String, Object> params) {
        mShopModel.changeShopRange(params, DeliverRangeActivity.this);
    }

    @Override
    public void onSuccess() {
        showLoadingDialog(false);
        Intent intent = new Intent();
        intent.putExtra("range", mRadius);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        showLoadingDialog(false);
        ToastUtils.getInstant().showToast(errorMsg);
        // token 失效
        if (errorCode == BaseConstant.TOKEN_INVALID) {
            UserManager.cleanToken();
        }
    }
}
