package com.example.zj.androidstudy.firebase;

import android.util.Log;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.tool.SharedPreferenceUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created on 2019-10-23.
 */
public class MessageReceiverService extends FirebaseMessagingService {
  /**
   * title key
   */
  public static final String TITLE_KEY = "title";
  /**
   * message key
   */
  public static final String MESSAGE_KEY = "content";

  @Override
  public void onNewToken(@NonNull String s) {
    super.onNewToken(s);
    Log.e("zj", "token: " + s);
    SharedPreferenceUtil.saveString(this, Constants.SP_FIRE_BASE_CLOUD_MESSAGE_TOKEN, s);
  }

  @Override
  public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    Map<String, String> data = remoteMessage.getData();
    String title = data.get(TITLE_KEY);
    String message = data.get(MESSAGE_KEY);
    Log.e("zj", title + message);

  }
}
