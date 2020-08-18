package com.example.zj.androidstudy.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.app.AsyncNotedAppOp;
import android.app.SyncNotedAppOp;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.zj.androidstudy.R;

import java.util.Arrays;

public class OpNoteActivity extends AppCompatActivity {
  private static final String TAG = "OpNoteActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_op_note);

    initOpsCallback();
  }

  private void initOpsCallback() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      AppOpsManager.OnOpNotedCallback appOpsCallback = new AppOpsManager.OnOpNotedCallback() {

        private void logPrivateDataAccess(String opCode, String trace) {
          Log.i(TAG, "Private data accessed. " +
              "Operation: $opCode\nStack Trace:\n$trace");
        }

        @Override
        public void onNoted(@NonNull SyncNotedAppOp syncNotedAppOp) {
          logPrivateDataAccess(syncNotedAppOp.getOp(),
              Arrays.toString(new Throwable().getStackTrace()));
        }

        @Override
        public void onSelfNoted(@NonNull SyncNotedAppOp syncNotedAppOp) {
          logPrivateDataAccess(syncNotedAppOp.getOp(),
              Arrays.toString(new Throwable().getStackTrace()));
        }

        @Override
        public void onAsyncNoted(@NonNull AsyncNotedAppOp asyncNotedAppOp) {
          logPrivateDataAccess(asyncNotedAppOp.getOp(),
              asyncNotedAppOp.getMessage());
        }
      };

      AppOpsManager appOpsManager = getSystemService(AppOpsManager.class);
      if (appOpsManager != null) {
        appOpsManager.setOnOpNotedCallback(getMainExecutor(), appOpsCallback);
      }
    }
  }
}
