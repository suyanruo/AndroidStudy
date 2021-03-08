package com.example.zj.androidstudy.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.fragment.MainTab01;
import com.example.zj.androidstudy.fragment.MainTab02;
import com.example.zj.androidstudy.fragment.MainTab03;
import com.example.zj.androidstudy.fragment.MainTab04;
import com.example.zj.androidstudy.fragment.MainTab05;
import com.example.zj.androidstudy.fragment.MainTab06;
import com.example.zj.androidstudy.fragment.MainTab07;
import com.example.zj.androidstudy.fragment.MainTab08;

public class ViewPagerActivity extends AppCompatActivity  {
  private static final String TAG = "ViewPagerActivity";
  private static final String FRAGMENT_TAG = "fragmentTag";

  ViewPager viewPager;
  FragmentPagerAdapter adapter;


  MainTab01 tab01 = new MainTab01();
  MainTab02 tab02 = new MainTab02();
  MainTab03 tab03 = new MainTab03();
  MainTab04 tab04 = new MainTab04();
  MainTab05 tab05 = new MainTab05();
  MainTab06 tab06 = new MainTab06();
  MainTab07 tab07 = new MainTab07();
  MainTab08 tab08 = new MainTab08();

  Fragment[] fragments = { tab01, tab02, tab03, tab04 };
  Fragment[] replaceFragments = {tab05, tab06, tab07, tab08 };
  boolean[] fragmentsUpdateFlag = { false, false, false, false };


  /**
   * 底部四个按钮
   */
  private Button tabBtnWeixin;
  private Button tabBtnFrd;
  private Button tabBtnAddress;
  private Button tabBtnSettings;


  Handler mainHandler = new Handler(Looper.getMainLooper()) {


    /*
     * （非 Javadoc）
     *
     * @see android.os.Handler#handleMessage(android.os.Message)
     */
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      int w = msg.what;
//      fragments[w] = replaceFragments[w];
//      fragmentsUpdateFlag[w] = true;
//      adapter.notifyDataSetChanged();

      changeFragment(fragments[0], replaceFragments[w]);
    }
  };

  private void changeFragment(Fragment rootFragment, Fragment newFragment) {
    Fragment oldFragment = rootFragment.getChildFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    FragmentTransaction transaction = rootFragment.getChildFragmentManager().beginTransaction();
    if (oldFragment == null) {
      transaction.add(R.id.frame_container, newFragment, FRAGMENT_TAG);
    } else {
      transaction.replace(R.id.frame_container, newFragment, FRAGMENT_TAG);
    }
    transaction.commitAllowingStateLoss();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager);
    viewPager = findViewById(R.id.viewpager);

    tabBtnWeixin = findViewById(R.id.btn1);
    tabBtnFrd = findViewById(R.id.btn2);
    tabBtnAddress = findViewById(R.id.btn3);
    tabBtnSettings = findViewById(R.id.btn4);

    tabBtnWeixin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewPagerActivity.this, "changeFragment1", Toast.LENGTH_SHORT).show();
        mainHandler.sendEmptyMessage(0);
      }
    });
    tabBtnFrd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewPagerActivity.this, "changeFragment2", Toast.LENGTH_SHORT).show();
        mainHandler.sendEmptyMessage(1);
      }
    });
    tabBtnAddress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewPagerActivity.this, "changeFragment3", Toast.LENGTH_SHORT).show();
        mainHandler.sendEmptyMessage(2);
      }
    });
    tabBtnSettings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ViewPagerActivity.this, "changeFragment4", Toast.LENGTH_SHORT).show();
        mainHandler.sendEmptyMessage(3);
      }
    });

    adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());


    viewPager.setAdapter(adapter);

    viewPager.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        Toast.makeText(ViewPagerActivity.this, ""+ position, Toast.LENGTH_SHORT).show();
      }


      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }


      @Override
      public void onPageScrollStateChanged(int arg0) {
      }
    });
  }

  class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fm;


    MyFragmentPagerAdapter(FragmentManager fm) {
      super(fm);
      this.fm = fm;
    }


    @Override
    public int getCount() {
      return fragments.length;
    }


    @Override
    public Fragment getItem(int position) {
      Fragment fragment = fragments[position % fragments.length];
      Log.i(TAG, "getItem:position=" + position + ",fragment:"
          + fragment.getClass().getName() + ",fragment.tag="
          + fragment.getTag());
      return fragments[position % fragments.length];
    }


    @Override
    public int getItemPosition(Object object) {
      return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      //得到缓存的fragment
      Fragment fragment = (Fragment) super.instantiateItem(container,
          position);
      //得到tag，这点很重要
      String fragmentTag = fragment.getTag();


      if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
      //如果这个fragment需要更新

        FragmentTransaction ft = fm.beginTransaction();
        //移除旧的fragment
        ft.remove(fragment);
        //换成新的fragment
        fragment = fragments[position % fragments.length];
        //添加新fragment时必须用前面获得的tag，这点很重要
        ft.add(container.getId(), fragment, fragmentTag);
        ft.attach(fragment);
        ft.commit();

        //复位更新标志
        fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
      }


      return fragment;
    }
  }

}
