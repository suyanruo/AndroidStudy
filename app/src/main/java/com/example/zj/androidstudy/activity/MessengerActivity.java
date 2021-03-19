package com.example.zj.androidstudy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.service.MessengerService;

public class MessengerActivity extends AppCompatActivity {
  private static final String TAG = "MessengerActivity";

  private Messenger mService;
  private Messenger mGetReplyMessenger = new Messenger(new ClientMesHandler(Looper.getMainLooper()));

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      Message message = Message.obtain(null, Constants.MSG_FROM_CLIENT);
      Bundle data = new Bundle();
      data.putString("msg", "Hello, this is client");
      message.setData(data);
      // 设置接收消息的Messenger对象
      message.replyTo = mGetReplyMessenger;
      try {
        mService.send(message);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messenger);

    bindService();
  }

  private void bindService() {
    Intent intent = new Intent(this, MessengerService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  protected void onDestroy() {
    unbindService(mConnection);
    super.onDestroy();
  }

  private static class ClientMesHandler extends Handler {
    public ClientMesHandler(@NonNull Looper looper) {
      super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      switch (msg.what) {
        case Constants.MSG_FROM_SERVICE:
          Log.e(TAG, "Receive Message from service: " + msg.getData().getString("reply"));
          break;
        default:
          super.handleMessage(msg);
      }
    }
  }
}
