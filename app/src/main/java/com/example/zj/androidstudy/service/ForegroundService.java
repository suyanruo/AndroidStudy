package com.example.zj.androidstudy.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.activity.MaterialDesignActivity;
import com.example.zj.androidstudy.tool.NotificationUtil;

public class ForegroundService extends Service {
    private static final String TAG = "ForegroundService";
    
    public ForegroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");


        createAndShowForegroundNotification(1);
    }

    private void createAndShowForegroundNotification(int notificationId) {
        Intent intent = new Intent(this, MaterialDesignActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        final NotificationCompat.Builder builder = NotificationUtil.getNotificationBuilder(this,
                "com.example.your_app.notification.CHANNEL_ID_FOREGROUND", // Channel id
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top
        builder
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText("text")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();

        startForeground(notificationId, notification);

//        if (notificationId != lastShownNotificationId) {
//            // Cancel previous notification
//            final NotificationManager nm = (NotificationManager) yourService.getSystemService(Activity.NOTIFICATION_SERVICE);
//            nm.cancel(lastShownNotificationId);
//        }
//        lastShownNotificationId = notificationId;
    }



    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {

    }
}
