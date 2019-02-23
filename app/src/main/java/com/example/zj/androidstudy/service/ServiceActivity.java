package com.example.zj.androidstudy.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;

public class ServiceActivity extends BaseActivity {
    private Button mBindServcieButton;
    private Button mUnbindServiceButton;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mBindServcieButton = findViewById(R.id.btn_bind_service);
        mUnbindServiceButton = findViewById(R.id.btn_unbind_service);

        mBindServcieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        mUnbindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }

    private void bindService() {
//        Intent intent = new Intent();
//        intent.setAction("com.example.zj.androidstudy.service.ForegroundService");
//        intent.setPackage("com.example.zj.androidstudy");
        Intent intent = new Intent(this, ForegroundService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(mServiceConnection);
    }
}
