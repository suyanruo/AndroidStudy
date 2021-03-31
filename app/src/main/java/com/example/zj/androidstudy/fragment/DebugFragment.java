package com.example.zj.androidstudy.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;

/**
 * Debug API
 */
public class DebugFragment extends Fragment {


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    Debug.startMethodTracing("AndroidStudy");
    return inflater.inflate(R.layout.fragment_debug, container, false);
  }

  @Override
  public void onDestroyView() {
    Debug.stopMethodTracing();
    super.onDestroyView();
  }
}