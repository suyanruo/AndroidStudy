package com.example.zj.androidstudy.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 * Created on 3/19/21.
 */

public class BackJobService extends JobIntentService {
  private static final String TAG = "BackJobService";
  public static final int JOB_ID = 1000;

  public static void enqueueWork(Context context, Intent work) {
    enqueueWork(context, BackJobService.class, JOB_ID, work);
  }

  @Override
  protected void onHandleWork(@NonNull Intent intent) {
    // 工作线程
    Log.e(TAG, "onHandleWork");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy");
  }
}
