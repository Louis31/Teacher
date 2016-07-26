package com.haiku.wateroffer.mvp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haiku.wateroffer.R;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.ui.KeyBoardUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.ShopAddrContract;
import com.haiku.wateroffer.mvp.model.impl.UserModelImpl;
import com.haiku.wateroffer.mvp.persenter.ShopAddrPresenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 店铺地址Activity
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_shop_address)
public class ShopAddressActivity extends BaseActivity implements ShopAddrContract.View {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private boolean isUpdate;
    private ShopAddrContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.et_address_detail)
    private EditText et_address_detail;

    @ViewInject(R.id.et_address)
    private TextView et_address;

    @Event(R.id.iv_location)
    private void onLocationClick(View v) {
        startActivity(new Intent(mContext, AddressActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        initLocation();
        new ShopAddrPresenter(new UserModelImpl(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    // 开始定位
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        if (mLocationOption.isOnceLocationLatest()) {
            mLocationOption.setOnceLocationLatest(true);
        }
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void initDatas() {
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        String area = getIntent().getStringExtra("area");
        String area_detail = getIntent().getStringExtra("area_detail");
        et_address.setText(area);
        et_address_detail.setTag(area_detail);
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.shop_address, true);
        if (isUpdate) {
            mTitlebar.showRightTextView(getString(R.string.save));
            mTitlebar.setListener(new TitlebarListenerAdapter() {
                @Override
                public void onReturnIconClick() {
                    finish();
                }

                @Override
                public void onRightTextClick() {
                    String area = et_address.getText().toString();
                    String detail = et_address_detail.getText().toString().trim();

                    if (TextUtils.isEmpty(area) || TextUtils.isEmpty(detail)) {
                        ToastUtils.getInstant().showToast(R.string.msg_addr_invalid);
                    } else {

                    }
                }
            });
        } else {
            mTitlebar.setListener(new TitlebarListenerAdapter() {
                @Override
                public void onReturnIconClick() {
                    finish();
                }
            });
        }
    }

    @Override
    public void setPresenter(@NonNull ShopAddrContract.Presenter presenter) {
        this.mPresenter = presenter;
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

    @Override
    public void showSuccessView() {
        if (isUpdate) {
            // 更新成功,返回页面
            finish();
        } else {
            // TODO 添加店铺地址成功
        }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeybord();
        return super.onTouchEvent(event);
    }

    // 关闭keybord
    public void closeKeybord() {
        KeyBoardUtils.closeKeybord(et_address_detail, this);
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);//定位时间
                    et_address.setText(aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
}
