package com.example.zj.androidstudy.autoFillCode;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.event.SmsCodeEvent;
import com.example.zj.androidstudy.tool.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.MessageFormat;

public class AutoFillSmsCodeActivity extends AppCompatActivity {
  EditText mEtReceiverPhone;
  TextView mTvSmsCode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auto_fill_sms_code);
    EventBus.getDefault().register(this);

    mEtReceiverPhone = findViewById(R.id.et_receiver_phone);
    mTvSmsCode = findViewById(R.id.tv_message_code);

    findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendMessage();
      }
    });
  }

  private void sendMessage() {
    if (ContextCompat.checkSelfPermission(AutoFillSmsCodeActivity.this, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(AutoFillSmsCodeActivity.this, new String[] { Manifest.permission.READ_PHONE_STATE }, 2001);
      return;
    }
    /*
     * 当send按钮被点击时，会先调用SmsManager的getDefault()方法
     * 获取到SmsManager的实例，然后再调用它的sendTextMessage()方法就可以
     * 去发送短信了。sendTextMessage()方法接收5个参数，其中第一个参数用于指定
     * 接收人的手机号码，第三个参数用于指定短信的内容，其他的几个参数我们暂时用不到，
     * 直接传入null就可以了。
     * */
    SmsManager smsManager = AppUtil.getSmsManager(this, mEtReceiverPhone.getText().toString());
    smsManager.sendTextMessage(mEtReceiverPhone.getText().toString(),
        null, MessageFormat.format("{0} {1}", createMessageToken(), "141212"),
        null, null);
  }

  private String createMessageToken() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      PendingIntent intent = PendingIntent.getForegroundService(this, 0,
          new Intent(this, AutoFillSmsService.class), 0);
      return AppUtil.getSmsManager(this, mEtReceiverPhone.getText().toString()).createAppSpecificSmsToken(intent);
    }
    return "";
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 2001) {
      if (permissions[0].equals(Manifest.permission.READ_PHONE_STATE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        sendMessage();
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void onReceiveSmsCode(final SmsCodeEvent event) {
    if (event != null && !TextUtils.isEmpty(event.smsCode)) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mTvSmsCode.setText(event.smsCode);
        }
      });
    }
  }
}
