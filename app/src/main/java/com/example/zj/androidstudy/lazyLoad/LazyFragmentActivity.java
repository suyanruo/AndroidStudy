package com.example.zj.androidstudy.lazyLoad;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.fragment.TabFragment;
import com.example.zj.androidstudy.tool.FragmentUtil;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created on 4/7/21.
 */

public class LazyFragmentActivity extends AppCompatActivity {
  private TabFragment mainTab01 = TabFragment.getInstance("Fragment Tab1");
  private TabFragment mainTab02 = TabFragment.getInstance("Fragment Tab2");
  private TabFragment mainTab03 = TabFragment.getInstance("Fragment Tab3");
  private TabFragment mainTab04 = TabFragment.getInstance("Fragment Tab4");
  int i = 0;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_fragment);

    initViews();
//    initWorkers();
  }


  protected void initViews() {
    final FragmentHandler handler = new FragmentHandler(this);
    findViewById(R.id.btn_change_visibility).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (i < 4) {
          Message message = Message.obtain();
          message.arg1 = i;
          handler.sendMessage(message);
        } else {
          if (i % 2 == 0) {
            FragmentUtil.showFragment(LazyFragmentActivity.this, mainTab03);
          } else {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(mainTab03);
            transaction.commit();
          }
        }
        i++;
      }
    });
  }

  protected void initWorkers() {
    FragmentHandler handler = new FragmentHandler(this);
    for (int i = 0; i < 4; i++) {
      Message message = Message.obtain();
      message.arg1 = i;
      handler.sendMessageDelayed(message, 2000 * i);
    }
  }

  private static class FragmentHandler extends Handler {

    private WeakReference<LazyFragmentActivity> activityWeakReference;

    public FragmentHandler(LazyFragmentActivity activity) {
      activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      super.handleMessage(msg);
      int arg1 = msg.arg1;
      LazyFragmentActivity activity = activityWeakReference.get();
      if (arg1 < 4) {
        switch (arg1) {
          case 0:
            FragmentUtil.enterNewFragment(activity, R.id.root_content, activity.mainTab01);
            break;
          case 1:
            FragmentUtil.enterNewFragment(activity, R.id.root_content, activity.mainTab02);
            break;
          case 2:
//            FragmentUtil.enterNewFragment(activityWeakReference.get(), R.id.root_content, activity.mainTab03);
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.root_content, activity.mainTab03);
            transaction.hide(activity.mainTab03);
            transaction.commit();
            break;
          case 3:
            FragmentUtil.enterNewFragment(activity, R.id.root_content, activity.mainTab04);
            break;
          default:
            break;
        }
      } else {
        switch (arg1 % 4) {
          case 0:
            FragmentUtil.showFragment(activity, activity.mainTab01);
            break;
          case 1:
            FragmentUtil.showFragment(activity, activity.mainTab02);
            break;
          case 2:
            FragmentUtil.showFragment(activity, activity.mainTab03);
            break;
          case 3:
            FragmentUtil.showFragment(activity, activity.mainTab04);
            break;
          default:
            break;
        }
      }
    }
  }
}
