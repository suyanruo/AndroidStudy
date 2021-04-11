package com.example.zj.androidstudy.fragment;

import androidx.navigation.Navigation;

import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

public class ChooseRecipientFragment extends BaseFragment {

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_choose_recipient;
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
