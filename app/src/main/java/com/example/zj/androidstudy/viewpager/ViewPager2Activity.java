package com.example.zj.androidstudy.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.Fragment2Adapter;

import java.util.Arrays;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {
  private ViewPager2 viewPager2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager2);

    viewPager2 = findViewById(R.id.vp2);
    List<Integer> dataList = Arrays.asList(0, 1, 2, 3);
    Fragment2Adapter adapter = new Fragment2Adapter(this, dataList);
    viewPager2.setAdapter(adapter);
    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
  }
}