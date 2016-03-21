package com.example.dell.chihuobao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class LocationActivity extends Activity {
    User user;
    HashMap hashMap;
    BitmapDescriptor bdA;
    BitmapDescriptor bdGround;
    private Marker mMarkerA;
    private LatLng currentPt;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    MapView mMapView;
    BaiduMap mBaiduMap;


    Button requestLocButton;
    Button save;
    boolean isFirstLoc = true; // 是否首次定位

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        user=MyApplication.getUser();
        hashMap = user.getUser();
        requestLocButton = (Button) findViewById(R.id.button1);
        save = (Button) findViewById(R.id.save);
        bdA = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
        bdGround = BitmapDescriptorFactory
                .fromResource(R.drawable.ground_overlay);
        mCurrentMode = LocationMode.NORMAL;
        requestLocButton.setText("普通");
        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hashMap.put("axisX",currentPt.longitude);
                hashMap.put("axisY",currentPt.latitude);
                BaseLog.d("axisX", currentPt.longitude + "");
                BaseLog.d("axisY",currentPt.latitude+"");
                updateUser(hashMap);

            }
        });

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        if(hashMap.get("axisX")!=null && (hashMap.get("axisY")!=null)) {
            currentPt=new LatLng(Double.parseDouble(hashMap.get("axisY").toString()), Double.parseDouble(hashMap.get("axisX").toString()));
            initOverlay();
        }
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();





    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                mBaiduMap.setOnMapClickListener(listener);

            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }

        OnMapClickListener listener=new OnMapClickListener(){

            @Override
            public void onMapClick(LatLng latLng) {
                currentPt = latLng;
                updateMapState();
                clearOverlay(null);
                initOverlay();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        };
    }
    /**
     * 更新地图状态显示面板
     */
    private void updateMapState() {

        /*String state = "";
        if (currentPt == null) {
            state = "点击、长按、双击地图以获取经纬度和地图状态";
        } else {
            state = String.format(",当前经度： %f 当前纬度：%f",
                    currentPt.longitude, currentPt.latitude);
        }
        state += "\n";
        MapStatus ms = mBaiduMap.getMapStatus();
        state += String.format(
                "zoom=%.1f rotate=%d overlook=%d",
                ms.zoom, (int) ms.rotate, (int) ms.overlook);
        Toast.makeText(LocationActivity.this, state, Toast.LENGTH_SHORT).show();*/
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(currentPt);
        mBaiduMap.animateMapStatus(update);
      /*  update = MapStatusUpdateFactory.zoomTo(16f);
        mBaiduMap.animateMapStatus(update);*/


    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        // 回收 bitmap 资源
        bdA.recycle();
        bdGround.recycle();
    }
    public void initOverlay() {
        // add marker overlay
        LatLng llA = currentPt;


        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(true);
        ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));


        /*ArrayList<BitmapDescriptor> giflist = new ArrayList<>();
        giflist.add(bdA);*/



    /*    // add ground overlay
        LatLng southwest = new LatLng(39.92235, 116.380338);
        LatLng northeast = new LatLng(39.947246, 116.414977);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);*/

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(
                        LocationActivity.this,
                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
                                + marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
        mMarkerA = null;

    }
    public void updateUser(final HashMap hashMap){
        RequestParams params = new RequestParams(UserModifyActivity.URL+UserModifyActivity.UPDATE_USER);
        params.addBodyParameter("id",(int)(Double.parseDouble(hashMap.get("id").toString())),null);
        params.addBodyParameter("username",hashMap.get("username").toString(),null);
        params.addBodyParameter("password", hashMap.get("password").toString(), null);
        params.addBodyParameter("phone", hashMap.get("phone"), null);
        params.addBodyParameter("address", hashMap.get("address"), null);
      /*  params.addBodyParameter("shopphoto", hashMap.get("shopphoto"), null);
        params.addBodyParameter("shopphotodetail",hashMap.get("shopphotodetail"), null);*/
        params.addBodyParameter("registertime", hashMap.get("registertime"), null);
        params.addBodyParameter("updatetime", hashMap.get("updatetime").toString(), null);
        params.addBodyParameter("shoptype", (int)(Double.parseDouble(hashMap.get("shoptype").toString())), null);
        params.addBodyParameter("minconsume", Double.parseDouble(hashMap.get("minconsume").toString()), null);
        params.addBodyParameter("sendexpense", Double.parseDouble(hashMap.get("sendexpense").toString()), null);
        params.addBodyParameter("email", hashMap.get("email").toString(), null);
        params.addBodyParameter("qrcode", hashMap.get("qrcode"), null);
        params.addBodyParameter("shopmessage", hashMap.get("shopmessage"), null);
        params.addBodyParameter("telephone", hashMap.get("telephone"), null);
        params.addBodyParameter("identify", hashMap.get("identify"), null);
        params.addBodyParameter("license", hashMap.get("license"), null);
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("businessstarttime", hashMap.get("businessstarttime").toString(), null);
        params.addBodyParameter("businessendtime", hashMap.get("businessendtime").toString(), null);
        params.addBodyParameter("isServing", (int)(Double.parseDouble(hashMap.get("isServing").toString())), null);
        params.addBodyParameter("axisX", hashMap.get("axisX"), null);
        params.addBodyParameter("axisY", hashMap.get("axisY"), null);
        if(hashMap.get("rank")!=null) {
            params.addBodyParameter("rank", (int) (Double.parseDouble(hashMap.get("rank").toString())), null);
        }else{
            params.addBodyParameter("rank", "0", null);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);
                user.setUser(hashMap);
                MyApplication.getInstance().setUser(user);
                Toast.makeText(x.app(), "map更新成功！" + result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "map更新失败", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
