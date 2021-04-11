package com.example.zj.androidstudy.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

/**
 * Created on 4/11/21.
 */

public class TabFragment extends BaseFragment {
  private static final String KEY_CONTENT = "key_content";
  private TextView tvContent;

  public static TabFragment getInstance(String content) {
    TabFragment fragment = new TabFragment();
    Bundle bundle = new Bundle();
    bundle.putString(KEY_CONTENT, content);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_tab;
  }

  @Override
  protected void initViews(View view) {
    tvContent = view.findViewById(R.id.tv_content);
    Bundle bundle = getArguments();
    tvContent.setText(bundle.getString(KEY_CONTENT, "Fragment Tab"));
  }

  @Override
  protected void initWorkers() {
  }
}
