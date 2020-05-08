package com.example.zj.androidstudy;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.multidex.MultiDex;

public class StudyApplication extends Application {
    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        initBaiduMap();

        initFireBase();
    }

    private void initBaiduMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initFireBase() {
        HandlerThread handlerThread = new HandlerThread("FirebaseMessaging");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                FirebaseApp.initializeApp(StudyApplication.this);
                FirebaseMessaging.getInstance().subscribeToTopic(Constants.FIRE_BASE_CLOUD_MESSAGE_TOPIC);
            }
        };
        handler.sendEmptyMessage(0);
    }

    public static Context getContext() {
        return mContext;
    }
}
