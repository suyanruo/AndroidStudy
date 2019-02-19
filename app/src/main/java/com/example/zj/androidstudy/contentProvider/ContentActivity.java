package com.example.zj.androidstudy.contentProvider;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;
import com.example.zj.androidstudy.tool.FragmentUtil;

public class ContentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        FragmentUtil.setRootFragment(this, R.id.root_content, new ContentProviderFragment());
    }
}
