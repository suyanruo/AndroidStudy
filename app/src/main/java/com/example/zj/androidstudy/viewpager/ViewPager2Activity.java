package com.example.zj.androidstudy.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.ViewPager2FragmentAdapter;
import com.example.zj.androidstudy.adapter.ViewPager2RecyclerViewAdapter;
import com.example.zj.androidstudy.fragment.TabFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {
  private static final String TAG = "ViewPager2Activity";

  private final int activeColor = Color.parseColor("#ff678f");
  private final int normalColor = Color.parseColor("#666666");
  private final int activeSize = 20;
  private final int normalSize = 14;
  private final String[] tabs = new String[]{"首页", "关注", "推荐", "最新"};

  private ViewPager2 mVp2Recyclerview;
  private ViewPager2 mVp2Fragment;
  private TabLayout mTabLayout;
  private TabLayoutMediator mTabLayoutMediator;
  private Button mBtnFakeDrag;
  private SparseArray<Fragment> fragmentArray = new SparseArray<>();
  private ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
      int tabCount = mTabLayout.getTabCount();
      for (int i = 0; i < tabCount; i++) {
        TabLayout.Tab tab = mTabLayout.getTabAt(i);
        TextView textView = (TextView) tab.getCustomView();
        if (tab.getPosition() == position) {
          textView.setTextSize(activeSize);
          textView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
          textView.setTextSize(normalSize);
          textView.setTypeface(Typeface.DEFAULT);
        }
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
      super.onPageScrollStateChanged(state);
    }
  };
  private TabLayoutMediator.TabConfigurationStrategy mStrategy = new TabLayoutMediator.TabConfigurationStrategy() {
    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
      // 这里可以自定义TabView
      TextView tabView = new TextView(ViewPager2Activity.this);
      int[][] states = new int[2][];
      states[0] = new int[]{android.R.attr.state_selected};
      states[1] = new int[]{};
      int[] colors = new int[]{activeColor, normalColor};
      ColorStateList colorStateList = new ColorStateList(states, colors);
      tabView.setText(tabs[position]);
      tabView.setTextSize(normalSize);
      tabView.setTextColor(colorStateList);
      tab.setCustomView(tabView);

      // 也可以直接设置tab，不过这种方式没有提供设置字体颜色大小的方法
//      tab.setText(tabs[position]);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager2);

    mVp2Recyclerview = findViewById(R.id.vp2_recyclerview);
    mVp2Fragment = findViewById(R.id.vp2_fragment);
    mTabLayout = findViewById(R.id.tab_layout);
    mBtnFakeDrag = findViewById(R.id.btn_fake_drag);

    initViewPagerView();
    initViewPagerFragment();
    initButton();
  }

  private void initViewPagerView() {
    List<Integer> dataList = Arrays.asList(0, 1, 2, 3);
    ViewPager2RecyclerViewAdapter adapter = new ViewPager2RecyclerViewAdapter(this, dataList);
    mVp2Recyclerview.setAdapter(adapter);
    // 设置横向或竖向滑动
//    mVp2Recyclerview.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
    // 设置禁用用户手动滑动
    mVp2Recyclerview.setUserInputEnabled(true);
    // 设置页面动画
    CompositePageTransformer transformer = new CompositePageTransformer();
    // 设置页间距
    transformer.addTransformer(new MarginPageTransformer(getResources().getDimensionPixelSize(R.dimen.dp_10)));
    // 设置划入动画
    transformer.addTransformer(new ScaleInTransformer());
    mVp2Recyclerview.setPageTransformer(transformer);
    // 一屏多页效果
    mVp2Recyclerview.setOffscreenPageLimit(1);
    RecyclerView recyclerView = (RecyclerView) mVp2Recyclerview.getChildAt(0);
    int padding = getResources().getDimensionPixelOffset(R.dimen.dp_20);
    recyclerView.setPadding(padding, 0, padding, 0);
    recyclerView.setClipToPadding(false);
  }

  private void initViewPagerFragment() {
    ViewPager2FragmentAdapter adapter = new ViewPager2FragmentAdapter(this, initFragmentArray());
    mVp2Fragment.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
    mVp2Fragment.setAdapter(adapter);
    // 页面滑动事件监听
    mVp2Fragment.registerOnPageChangeCallback(mPageChangeCallback);
    mTabLayoutMediator = new TabLayoutMediator(mTabLayout, mVp2Fragment, mStrategy);
    // 要执行这一句才是真正将两者绑定起来
    mTabLayoutMediator.attach();
  }

  private SparseArray<Fragment> initFragmentArray() {
    for (int i = 0; i < tabs.length; i++) {
      fragmentArray.put(i, TabFragment.getInstance(tabs[i]));
    }
    return fragmentArray;
  }

  private void initButton() {
    mBtnFakeDrag.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 模拟拖拽
        mVp2Recyclerview.beginFakeDrag();
        // fakeDragBy返回一个boolean值，表示是否正在滑动。当参数值为正数时表示向前一个页面滑动，当值为负数时表示向下一个页面滑动
        if (mVp2Recyclerview.fakeDragBy(-10f)) {
          mVp2Recyclerview.endFakeDrag();
        }
      }
    });
  }

  @Override
  protected void onDestroy() {
    mTabLayoutMediator.detach();
    mVp2Fragment.unregisterOnPageChangeCallback(mPageChangeCallback);
    super.onDestroy();
  }
}