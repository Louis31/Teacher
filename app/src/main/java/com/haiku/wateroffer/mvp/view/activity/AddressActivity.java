package com.haiku.wateroffer.mvp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.GeoPoint;
import com.haiku.wateroffer.common.listener.MyItemClickListener;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.view.adapter.SearchAddressAdapter;
import com.haiku.wateroffer.mvp.view.divider.DividerItem;
import com.haiku.wateroffer.mvp.view.widget.MyRefreshLayout;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址列表
 * Created by hyming on 2016/7/21.
 */
@ContentView(R.layout.act_address)
public class AddressActivity extends BaseActivity implements AMap.OnMapClickListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener
        , PoiSearch.OnPoiSearchListener, MyRefreshLayout.OnRefreshLayoutListener {
    private int zoomLevel;
    private AMap aMap;
    private GeoPoint mPoiItem;
    Boolean isInit = false;
    private MarkerOptions markerOption;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    private List<PoiItem> mPoiItemList;
    private SearchAddressAdapter mAdapter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.map)
    private MapView mMapView;

    @ViewInject(R.id.myRefreshLayout)
    private MyRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        initSearch();
        initLocation();

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

    private void initDatas() {
        zoomLevel = 20;
        if (getIntent().getExtras().get("point") != null) {
            mPoiItem = (GeoPoint) getIntent().getExtras().get("point");
        }
        mPoiItemList = new ArrayList<PoiItem>();
        mAdapter = new SearchAddressAdapter(mContext, mPoiItemList);
        mAdapter.setListener(new MyItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                PoiItem bean = mPoiItemList.get(pos);
                String addr = bean.getProvinceName() + bean.getCityName() + bean.getAdName() + bean.getSnippet() + bean.getTitle();
                Intent intent = new Intent();
                intent.putExtra("address", addr);
                LatLonPoint lp = bean.getLatLonPoint();
                intent.putExtra("latitude", lp.getLatitude());
                intent.putExtra("longitude", lp.getLongitude());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.current_address_list, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });

        mRefreshLayout.setPageSize(10);
        mRefreshLayout.addItemDecoration(new DividerItem(mContext));
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setLinearLayout();
        mRefreshLayout.setPullRefreshEnable(false);
        mRefreshLayout.setLoadMoreEnable(false);
    }

    private void initLocation() {
        aMap = mMapView.getMap();
        markerOption = new MarkerOptions();
        if (mPoiItem != null) {
            LatLng latLng = new LatLng(mPoiItem.getLat(), mPoiItem.getLon());
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
            markerOption.position(latLng);
            aMap.addMarker(markerOption);
        }
        aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
    }

    private void initSearch() {
        if (mPoiItem == null) {
            return;
        }
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(mPoiItem.getAddress(), "", mPoiItem.getCity());
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 实现回调方法
     */
    //对单击地图事件回调
    @Override
    public void onMapClick(LatLng latLng) {
        markerOption.position(latLng);
        mPoiItem.setLat(latLng.latitude);
        mPoiItem.setLon(latLng.longitude);
        aMap.clear();
        aMap.addMarker(markerOption);

        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    //对正在移动地图事件回调
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    //对移动地图结束事件回调
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (isInit) {
            markerOption.position(cameraPosition.target);
            mPoiItem.setLat(cameraPosition.target.latitude);
            mPoiItem.setLon(cameraPosition.target.longitude);
            aMap.clear();
            aMap.addMarker(markerOption);

            GeocodeSearch geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
            //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude), 200, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        }
        isInit = true;
    }

    //逆地理编码回调接口
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        //解析result获取逆地理编码结果
        String address = regeocodeResult.getRegeocodeAddress().getFormatAddress() + regeocodeResult.getRegeocodeAddress().getBuilding();
        mPoiItem.setAddress(address);
        mRefreshLayout.refresh();
        initSearch();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    mPoiItemList.clear();
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    mPoiItemList.addAll(poiItems);
                    mRefreshLayout.loadingCompleted(true);
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onRefresh() {
        initSearch();
    }

    @Override
    public void onLoadMore() {

    }
}
