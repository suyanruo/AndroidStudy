package com.example.zj.androidstudy.scrollAndViewpager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerOneFragment extends Fragment {

  private RecyclerView recyclerView;
  private List<String> strDatas;
  private FragmentAdapter adapterFragment;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_viewpager_one, container, false);
    recyclerView = view.findViewById(R.id.recycler_fragment);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    strDatas = new ArrayList<>();
    adapterFragment = new FragmentAdapter(getContext(), strDatas);
    recyclerView.setAdapter(adapterFragment);
    WrappingGridLayoutManager gridLayoutManager = new WrappingGridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);//固定自身size不受adapter变化影响
    //消除滑动卡顿现象 ...
    recyclerView.setNestedScrollingEnabled(false);//限制recyclerview自身滑动特性,滑动全部靠scrollview完成
    initData();
  }

  private void initData() {
    for (int i = 0; i < 6; i++) {
      strDatas.add("item  " + i);
    }
    adapterFragment.notifyDataSetChanged();
  }

  public void loadMore() {
    List<String> newStrs = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      newStrs.add("新增  " + i);
    }
    int length = strDatas.size();
    strDatas.addAll(newStrs);
//        adapterFragment.notifyItemRangeChanged(length, newStrs.size());//这个方法刷新无效
    adapterFragment.notifyDataSetChanged();
  }

}
