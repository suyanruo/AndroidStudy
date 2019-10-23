package com.example.zj.androidstudy.firebase;

import android.os.Bundle;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.tool.SharedPreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class FirebaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_firebase);

    findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getTokenFromFirebase();
      }
    });
  }

  private String getToken() {
    String token = SharedPreferenceUtil.getString(this, Constants.SP_FIRE_BASE_CLOUD_MESSAGE_TOKEN, "");
    if (TextUtils.isEmpty(token)) {

    }
    return token;
  }

  private void getTokenFromFirebase() {
    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
          @Override
          public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
              Log.e("zj", "getInstanceId failed", task.getException());
              return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();
            refreshToken(token);
          }
        });
  }

  private void refreshToken(String token) {
    if (TextUtils.isEmpty(token)) {
      return;
    }
    String tokenCache = SharedPreferenceUtil.getString(this, Constants.SP_FIRE_BASE_CLOUD_MESSAGE_TOKEN, "");
    if (token.equals(tokenCache)) {
      return;
    }
    SharedPreferenceUtil.saveString(this, Constants.SP_FIRE_BASE_CLOUD_MESSAGE_TOKEN, token);
  }


}
