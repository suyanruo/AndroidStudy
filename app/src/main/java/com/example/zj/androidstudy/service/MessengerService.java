package com.example.zj.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.zj.androidstudy.Constants;

import androidx.annotation.NonNull;

public class MessengerService extends Service {
  private static final String TAG = "MessengerService";

  private static class ServiceMesHandler extends Handler {
    public ServiceMesHandler(@NonNull Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      switch (msg.what) {
        case Constants.MSG_FROM_CLIENT:
          Log.e(TAG, "Receive data from client: " + msg.getData().getString("msg"));
          Messenger client = msg.replyTo;
          Message message = Message.obtain(null, Constants.MSG_FROM_SERVICE);
          Bundle data = new Bundle();
          data.putString("reply", "Received your message. Contact you later.");
          message.setData(data);
          try {
            client.send(message);
          } catch (RemoteException e) {
            e.printStackTrace();
          }
          break;
        default:
          super.handleMessage(msg);
      }
    }
  }

  private Messenger mMessenger = new Messenger(new ServiceMesHandler(Looper.getMainLooper()));

  @Override
  public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
  }
}
