package com.example.zj.androidstudy.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;

public class ServiceActivity extends BaseActivity {
    private Button mBtnStartService;
    private Button mBtnStopService;
    private Button mBtnBindService;
    private Button mBtnUnbindService;

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

        mBtnStartService = findViewById(R.id.btn_start_service);
        mBtnStopService = findViewById(R.id.btn_stop_service);
        mBtnBindService = findViewById(R.id.btn_bind_service);
        mBtnUnbindService = findViewById(R.id.btn_unbind_service);

        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        mBtnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
        mBtnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
         mBtnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }

    private void startService() {
        Intent startIntent = new Intent(this, ForegroundService.class);
        startService(startIntent);
        mBtnStopService.setEnabled(true);
    }

    private void stopService() {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopService(stopIntent);
        mBtnStopService.setEnabled(false);
    }

    private void bindService() {
//        Intent intent = new Intent();
//        intent.setAction("com.example.zj.androidstudy.service.ForegroundService");
//        intent.setPackage("com.example.zj.androidstudy");
        Intent intent = new Intent(this, ForegroundService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mBtnUnbindService.setEnabled(true);
    }

    private void unbindService() {
        unbindService(mServiceConnection);
        mBtnUnbindService.setEnabled(false);
    }
}
