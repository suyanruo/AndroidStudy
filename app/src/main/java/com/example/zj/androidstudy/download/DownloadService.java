package com.example.zj.androidstudy.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.tool.AppUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class DownloadService extends Service {
  public static final int HANDLE_DOWNLOAD = 0x001;
  public static final float UNBIND_SERVICE = 2.0F;

  private DownloadBinder binder;
  private DownloadManager downloadManager;
  private DownloadChangeObserver downloadObserver;
  private BroadcastReceiver downLoadBroadcast;
  private ScheduledExecutorService scheduledExecutorService;

  //下载任务ID
  private long downloadId;
  private String downloadUrl;
  public static OnProgressListener onProgressListener;

  public Handler downLoadHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case HANDLE_DOWNLOAD:
          if (onProgressListener != null) {
            if (msg.arg1 >= 0 && msg.arg2 > 0) {
              onProgressListener.onProgress(msg.arg1 / msg.arg2);
            }
          }
          break;
        default:
          break;
      }
    }
  };

  private Runnable progressRunnable = new Runnable() {
    @Override
    public void run() {
      updateProgress();
    }
  };

  @Override
  public void onCreate() {
    super.onCreate();
    binder = new DownloadBinder();
  }

  @Override
  public IBinder onBind(Intent intent) {
    downloadUrl = intent.getStringExtra(Constants.INTENT_KEY_DOWNLOAD_URL);
    downloadFile();
    return binder;
  }

  private void downloadFile() {
    downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    downloadObserver = new DownloadChangeObserver();

    registerContentObserver();

    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
    /**设置用于下载时的网络状态*/
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
    /**设置通知栏是否可见*/
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
    /**设置漫游状态下是否可以下载*/
    request.setAllowedOverRoaming(false);
    /**如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true.*/
    request.setVisibleInDownloadsUi(true);
    /**设置文件保存路径*/
    request.setDestinationInExternalFilesDir(getApplicationContext(), "phoenix", "phoenix.apk");
    /**将下载请求放入队列， return下载任务的ID*/
    downloadId = downloadManager.enqueue(request);

    registerBroadcast();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unregisterBroadcast();
    unregisterContentObserver();
  }

  /**
   * 发送Handler消息更新进度和状态
   */
  private void updateProgress() {
    int[] bytesAndStatus = getBytesAndStatus(downloadId);
    downLoadHandler.sendMessage(downLoadHandler.obtainMessage(HANDLE_DOWNLOAD, bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2]));
  }

  /**
   * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
   */
  private int[] getBytesAndStatus(long downloadId) {
    int[] bytesAndStatus = new int[] { -1, -1, 0};
    DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
    try (Cursor cursor = downloadManager.query(query)) {
      if (cursor != null && cursor.moveToFirst()) {
        // 已经下载文件大小
        bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
        // 下载文件的总大小
        bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
        // 下载状态
        bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
      }
    }
    return bytesAndStatus;
  }

  /**
   * 注册service 广播 1.任务完成时 2.进行中的任务被点击
   */
  private void registerBroadcast() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
    intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
    registerReceiver(downLoadBroadcast = new DownloadBroadcast(), intentFilter);
  }

  /**
   * 注销广播
   */
  private void unregisterBroadcast() {
    if (downLoadBroadcast != null) {
      unregisterReceiver(downLoadBroadcast);
      downLoadBroadcast = null;
    }
  }

  /**
   * 注册ContentObserver
   */
  private void registerContentObserver() {
    if (downloadObserver != null) {
      getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), false, downloadObserver);
    }
  }

  /**
   * 注销ContentObserver
   */
  private void unregisterContentObserver() {
    if (downloadObserver != null) {
      getContentResolver().unregisterContentObserver(downloadObserver);
    }
  }

  /**
   * 接受下载完成广播
   */
  private class DownloadBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
      switch (intent.getAction()) {
        case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
          if (downloadId == downId && downId != -1 && downloadManager != null) {
            Uri downIdUri = downloadManager.getUriForDownloadedFile(downloadId);
            close();
            if (downIdUri != null) {
              AppUtil.installApk(getApplicationContext(), downIdUri);
            }
            if (onProgressListener != null) {
              onProgressListener.onProgress(UNBIND_SERVICE);
            }
          }
          break;
        default:
          break;
      }
    }
  }

  /**
   * 监听下载进度
   */
  private class DownloadChangeObserver extends ContentObserver {

    /**
     * Creates a content observer.
     */
    public DownloadChangeObserver() {
      super(downLoadHandler);
      scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * 当所监听的Uri发生改变时，就会回调此方法
     * @param selfChange 此值意义不大, 一般情况下该回调值false
     */
    @Override
    public void onChange(boolean selfChange) {
      scheduledExecutorService.scheduleAtFixedRate(progressRunnable, 0, 2, TimeUnit.SECONDS);
    }
  }

  /**
   * 关闭定时器，线程等操作
   */
  private void close() {
    if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
      scheduledExecutorService.shutdown();
    }

    if (downLoadHandler != null) {
      downLoadHandler.removeCallbacksAndMessages(null);
    }
  }

  public class DownloadBinder extends Binder {
    /**
     * 返回当前服务的实例
     */
    public DownloadService getService() {
      return DownloadService.this;
    }
  }

  public interface OnProgressListener {
    /**
     * 下载进度
     * @param fraction 已下载/总大小
     */
    void onProgress(float fraction);
  }

  /**
   * 对外开发的方法
   * @param onProgressListener
   */
  public void setOnProgressListener(OnProgressListener onProgressListener) {
    DownloadService.onProgressListener = onProgressListener;
  }
}
