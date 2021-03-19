package com.example.zj.androidstudy.service;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.material.MaterialDesignActivity;
import com.example.zj.androidstudy.tool.NotificationUtil;

public class ForegroundService extends Service {
  private static final String TAG = "ForegroundService";
  private Handler mHandler;

  public ForegroundService() {
  }

  @Override
  public void onCreate() {
      super.onCreate();
      Log.e(TAG, "onCreate");

      createAndShowForegroundNotification(1);

      mHandler = new Handler(Looper.getMainLooper());
      mHandler.post(new Runnable() {
          @Override
          public void run() {
              createDialog();
          }
      });
  }

    private void createAndShowForegroundNotification(int notificationId) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Intent intent = new Intent(this, MaterialDesignActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        final NotificationCompat.Builder builder = NotificationUtil.getNotificationBuilder(this,
            "com.example.your_app.notification.CHANNEL_ID_FOREGROUND", // Channel id
            NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top
        builder
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Foreground Service")
            .setContentText("Now we have started a foreground service")
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        Notification notification = builder.build();

        startForeground(notificationId, notification);

//        if (notificationId != lastShownNotificationId) {
//            // Cancel previous notification
//            final NotificationManager nm = (NotificationManager) yourService.getSystemService(Activity.NOTIFICATION_SERVICE);
//            nm.cancel(lastShownNotificationId);
//        }
//        lastShownNotificationId = notificationId;
      }
    }

    private void createDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(), R.style.AlertDialogCustom);
            builder.setTitle("Service Dialog");
            Dialog dialog = builder.create();
            //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY，否则弹不出
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
            } else {
                dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
            }
            dialog.show();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    public class Binder extends android.os.Binder {
      public ForegroundService getForegroundService() {
        return ForegroundService.this;
      }
    }
}
