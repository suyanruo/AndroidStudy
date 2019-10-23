package com.example.zj.androidstudy.autoFillCode;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import androidx.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.example.zj.androidstudy.event.SmsCodeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoFillSmsService extends IntentService {
  private static final String TAG = "AutoFillSmsService";

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public AutoFillSmsService(String name) {
    super(name);
  }

  public AutoFillSmsService() {
    super("");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      String CHANNEL_ID = "my_channel_02";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
          "Auto Fill Sms Notification", importance);

      ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

      new Notification.Builder(this, CHANNEL_ID);
      Notification notification = new Notification.Builder(this, CHANNEL_ID)
          .setContentTitle("")
          .setContentText("").build();

      startForeground(1, notification);
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    handleSms(intent);
  }

  private void handleSms(Intent intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      for (SmsMessage pdu : Telephony.Sms.Intents.
          getMessagesFromIntent(intent)) {
        String message = pdu.getDisplayMessageBody();
        Log.e(TAG, message);
        if (!TextUtils.isEmpty(message)) {
          Pattern pattern = Pattern.compile("\\d{6}");
          Matcher matcher = pattern.matcher(message);
          if (matcher.find()) {
            EventBus.getDefault().postSticky(new SmsCodeEvent(matcher.group(0)));
          }
        }
      }
    }
  }
}
