package com.example.zj.androidstudy.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;

public class ServiceActivity extends BaseActivity {
    private Button mBtnStartService;
    private Button mBtnStopService;
    private Button mBtnBindService;
    private Button mBtnUnbindService;
    private boolean isServiceStart;
    private boolean isServiceBound;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ForegroundService.Binder binder = (ForegroundService.Binder) service;
            ForegroundService foregroundService = binder.getForegroundService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 当与服务的连接意外中断时，例如服务崩溃或被终止时，Android 系统会调用该方法。当客户端取消绑定时，系统不会调用该方法。
            isServiceBound = false;
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

        checkAlertPermission();
    }

    private void startService() {
//        Intent startIntent = new Intent(Intent.ACTION_VIEW);
//        startIntent.setClassName("com.example.zj.androidstudy", "com.example.zj.androidstudy.service.ForegroundService");
        Intent startIntent = new Intent(this, ForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(startIntent);
        } else {
            startService(startIntent);
        }
        mBtnStopService.setEnabled(true);
        isServiceStart = true;
    }

    private void stopService() {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopService(stopIntent);
        mBtnStopService.setEnabled(false);
        isServiceStart = false;
    }

    private void bindService() {
//        Intent intent = new Intent();
//        intent.setAction("com.example.zj.androidstudy.service.ForegroundService");
//        intent.setPackage("com.example.zj.androidstudy");
        Intent intent = new Intent(this, ForegroundService.class);
        boolean bindSuccess = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mBtnUnbindService.setEnabled(true);
        isServiceBound = true;
    }

    private void unbindService() {
        unbindService(mServiceConnection);
        mBtnUnbindService.setEnabled(false);
        isServiceBound = false;
    }

    private void checkAlertPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "未被授予权限，相关功能不可用", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 如果您只需在 Activity 可见时与服务交互，应在 onStart() 期间进行绑定，在 onStop() 期间取消绑定
        if (isServiceStart) {
            stopService();
        }
        if (isServiceBound) {
            unbindService();
        }
    }
}
