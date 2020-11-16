package com.example.zj.androidstudy.download;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.R;

public class DownloadActivity extends AppCompatActivity {
  private TextView tvDownload;
  private boolean isBindService;

  private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
      DownloadService downloadService = binder.getService();
      downloadService.setOnProgressListener(new DownloadService.OnProgressListener() {
        @Override
        public void onProgress(float fraction) {
          tvDownload.setText(String.valueOf(fraction * 100));
          // 判断是否真的下载完成进行安装了，以及是否注册绑定过服务
          if (fraction == DownloadService.UNBIND_SERVICE && isBindService) {
            unbindService(connection);
            isBindService = false;
          }
        }
      });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download);

    tvDownload = findViewById(R.id.tv_download_progress);
  }

  @Override
  protected void onResume() {
    super.onResume();
    bindService("https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk");
  }

  private void bindService(String apkUrl) {
    Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
    intent.putExtra(Constants.INTENT_KEY_DOWNLOAD_URL, apkUrl);
    isBindService = bindService(intent, connection, Context.BIND_AUTO_CREATE);
  }
}
