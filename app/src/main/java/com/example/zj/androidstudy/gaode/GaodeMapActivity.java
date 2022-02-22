package com.example.zj.androidstudy.gaode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TextOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.CommonUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class GaodeMapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMapClickListener, AMap.OnMarkerDragListener {
    private MapView mMapView = null;
    private AMap aMap;
    private UiSettings uiSettings;
    //定位服务
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    //地理编码
    private GeocodeSearch geocodeSearch;
    //回显位置信息的TextView
    private TextView mTvLocationCoordinate;
    private TextView mTvLocationInfo;
    private TextView mTvAreaSize;
    //当前地图上的marker
    private Marker marker;
    private List<Marker> mMarkerList = new ArrayList<>();
    private List<LatLng> mlatLngList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode_map);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        mTvLocationCoordinate = findViewById(R.id.tv_location_coordinate);
        mTvLocationInfo = findViewById(R.id.tv_location_info);
        mTvAreaSize = findViewById(R.id.tv_area_size);
        findViewById(R.id.btn_delete_marker).setOnClickListener(v -> {
            int len = mMarkerList.size();
            if (len > 0) {
                mMarkerList.get(len - 1).remove();
                mlatLngList.remove(mlatLngList.size() - 1);
                refreshChosenPoint();
            }
        });
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        initMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private void initMap() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            uiSettings = aMap.getUiSettings();
            setMapAttribute();
        }

    }

    private void setMapAttribute() {
        //设置默认缩放级别
        aMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //隐藏的右下角缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //显示右上角定位按钮
        uiSettings.setMyLocationButtonEnabled(false);
        //设置定位监听
        aMap.setLocationSource(this);
        //可触发定位并显示当前位置
        aMap.setMyLocationEnabled(true);
        //定位一次，且将视角移动到地图中心点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //隐藏定位点外圈圆的颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        //设置地理编码查询
        try {
            geocodeSearch = new GeocodeSearch(this);
            geocodeSearch.setOnGeocodeSearchListener(this);
        } catch (AMapException e) {
            e.printStackTrace();
        }
        //设置地图点击事件
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerDragListener(this);
    }

    private void refreshChosenPoint() {
        aMap.clear();
        mMarkerList.clear();

        // 声明 多边形参数对象
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        LatLng currentLat, nextLat;
        for (int i = 0; i < mlatLngList.size(); i++) {
            currentLat = mlatLngList.get(i);
            if (i < mlatLngList.size() - 1) {
                nextLat = mlatLngList.get(i + 1);
            } else {
                if (mlatLngList.size() > 2) {
                    nextLat = mlatLngList.get(0);
                } else {
                    nextLat = null;
                }
            }
            polygonOptions.add(currentLat);
            MarkerOptions markerOptions = new MarkerOptions().position(currentLat).draggable(true).anchor(0.5f, 0.5f);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bn_reroute_blue));
            mMarkerList.add(aMap.addMarker(markerOptions));
            if (currentLat != null && nextLat != null) {
                int dis = (int) CommonUtil.getDistance(currentLat.longitude, currentLat.latitude,
                        nextLat.longitude, nextLat.latitude);
                LatLng textLat = new LatLng((currentLat.latitude + nextLat.latitude) / 2,
                        (currentLat.longitude + nextLat.longitude) / 2);
                TextOptions textOptions = new TextOptions().text(dis + "米").position(textLat).visible(true);
                aMap.addText(textOptions);
            }
        }
        polygonOptions.strokeWidth(5) // 多边形的边框
                .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
                .fillColor(Color.argb(100, 119, 136, 153));   // 多边形的填充色
        aMap.addPolygon(polygonOptions);

        float areaSize = AMapUtils.calculateArea(mlatLngList);
        mTvAreaSize.setText(MessageFormat.format("区域面积:{0}", areaSize));
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (locationClient == null) {
            //初始化定位
            try {
                locationClient = new AMapLocationClient(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //初始化定位参数
            locationClientOption = new AMapLocationClientOption();
            //设置定位回调监听
            locationClient.setLocationListener(this);
            //高精度定位模式
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //单定位模式
            locationClientOption.setOnceLocation(true);
            //设置定位参数
            locationClient.setLocationOption(locationClientOption);
            //启动定位
            locationClient.startLocation();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示定位圆点
                onLocationChangedListener.onLocationChanged(aMapLocation);
                mTvLocationCoordinate.setText("当前纬度:" + aMapLocation.getLatitude() + "当前经度" + aMapLocation.getLongitude());
                //根据当前经纬度查询地址
                LatLonPoint latLonPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            } else {
                Log.e("YyyyQ", "定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo());
                Toast.makeText(getApplication(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    /**
     * 根据坐标转换地址信息
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            mTvLocationInfo.setText("当前位置信息:" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
        } else {
            Toast.makeText(getApplication(), "获取当前位置信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 地址转坐标
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 地图点击事件
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bn_reroute_blue));
        markerOptions.position(latLng).anchor(0.5f,0.5f);
        marker = aMap.addMarker(markerOptions);
        //根据点击地图的点位获取详细信息
        mTvLocationCoordinate.setText("当前纬度:" + latLng.latitude + "当前经度" + latLng.longitude);
        //根据当前经纬度查询地址
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        mlatLngList.add(new LatLng(latLng.latitude, latLng.longitude));
        refreshChosenPoint();
    }

    private int mCurrentDragMarkerIndex;

    @Override
    public void onMarkerDragStart(Marker marker) {
        for (Marker m : mMarkerList) {
            if (marker == m) {
                mCurrentDragMarkerIndex = mMarkerList.indexOf(m);
                mlatLngList.set(mCurrentDragMarkerIndex, marker.getPosition());
            }
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        mlatLngList.set(mCurrentDragMarkerIndex, marker.getPosition());
        refreshChosenPoint();
//        aMap.clear();
//        PolygonOptions polygonOptions = new PolygonOptions();
//        for (int i = 0; i < mlatLngList.size(); i++) {
//            polygonOptions.add(mlatLngList.get(i));
//        }
//        polygonOptions.strokeWidth(5) // 多边形的边框
//                .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
//                .fillColor(Color.argb(100, 119, 136, 153));   // 多边形的填充色
//        aMap.addPolygon(polygonOptions);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }
}