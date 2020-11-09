package com.example.zj.androidstudy.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

public class ChooseRecipientFragment extends BaseFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return init(inflater.inflate(R.layout.fragment_choose_recipient, container, false));
  }

  @Override
  protected void initViews(View view) {
    view.findViewById(R.id.btn_go_home).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_global_homeFragment);
      }
    });
  }

  @Override
  protected void initWorkers() {
  }
}
