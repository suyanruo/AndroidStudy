package com.example.zj.androidstudy.scrollAndViewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019-12-19.
 */
public class ViewPagerTwoFragment extends Fragment {

  private RecyclerView recyclerView;
  private List<String> strDatas;
  private FragmentAdapter adapterFragment;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_viewpager_two, container, false);
    recyclerView = view.findViewById(R.id.recycler_fragment);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    strDatas = new ArrayList<>();
    adapterFragment = new FragmentAdapter(getContext(), strDatas);
    recyclerView.setAdapter(adapterFragment);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(gridLayoutManager);
    initData();
  }

  private void initData() {
    for (int i = 0; i < 10; i++) {
      strDatas.add("item  " + i);
    }
    adapterFragment.notifyDataSetChanged();
  }
}
