package com.example.zj.androidstudy.lazyLoad;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.fragment.MainTab01;
import com.example.zj.androidstudy.fragment.MainTab02;
import com.example.zj.androidstudy.fragment.MainTab03;
import com.example.zj.androidstudy.fragment.MainTab04;
import com.example.zj.androidstudy.tool.FragmentUtil;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created on 4/7/21.
 */

public class LazyFragmentActivity extends AppCompatActivity {


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_fragment);

    initWorkers();
  }

  protected void initWorkers() {
    FragmentHandler handler = new FragmentHandler(this);
    for (int i = 0; i < 40; i++) {
      Message message = Message.obtain();
      message.arg1 = i;
      handler.sendMessageDelayed(message, 2000 * i);
    }
  }

  private static class FragmentHandler extends Handler {
    MainTab01 mainTab01 = new MainTab01();
    MainTab02 mainTab02 = new MainTab02();
    MainTab03 mainTab03 = new MainTab03();
    MainTab04 mainTab04 = new MainTab04();

    private WeakReference<LazyFragmentActivity> activityWeakReference;

    public FragmentHandler(LazyFragmentActivity activity) {
      activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      int arg1 = msg.arg1;
      if (arg1 < 4) {
        switch (arg1) {
          case 0:
            FragmentUtil.enterNewFragment(activityWeakReference.get(), R.id.root_content, mainTab01);
            break;
          case 1:
            FragmentUtil.enterNewFragment(activityWeakReference.get(), R.id.root_content, mainTab02);
            break;
          case 2:
            FragmentUtil.enterNewFragment(activityWeakReference.get(), R.id.root_content, mainTab03);
            break;
          case 3:
            FragmentUtil.enterNewFragment(activityWeakReference.get(), R.id.root_content, mainTab04);
            break;
          default:
            break;
        }
      } else {
        switch (arg1 % 4) {
          case 0:
            FragmentUtil.showFragment(activityWeakReference.get(), mainTab01);
            break;
          case 1:
            FragmentUtil.showFragment(activityWeakReference.get(), mainTab02);
            break;
          case 2:
            FragmentUtil.showFragment(activityWeakReference.get(), mainTab03);
            break;
          case 3:
            FragmentUtil.showFragment(activityWeakReference.get(), mainTab04);
            break;
          default:
            break;
        }
      }
    }
  }
}
