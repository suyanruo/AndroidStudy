package com.example.zj.androidstudy.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.zj.androidstudy.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadActivity extends AppCompatActivity {
  private static final String TAG = "ThreadActivity";
  private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_thread);

    executorService.execute(new Runnable() {
      @Override
      public void run() {
        Log.e(TAG, "first thread start");
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              Thread.sleep(10000);
              Log.e(TAG, "first thread stop");
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }).start();
        // 异步操作不会阻塞当前线程池的流程，可以继续后面排队的线程执行！
        Log.e(TAG, "first thread execute finish in executor");
      }
    });
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        Log.e(TAG, "second thread start");
      }
    });
  }
}
