package com.example.zj.androidstudy.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;

@SuppressLint("NewApi")
public class MainTab01 extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return (inflater.inflate(R.layout.fragment_main_tab01, container, false));

  }

}