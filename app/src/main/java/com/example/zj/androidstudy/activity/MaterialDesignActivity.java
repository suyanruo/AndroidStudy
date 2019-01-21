package com.example.zj.androidstudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.fragment.ShowToastFragment;
import com.example.zj.androidstudy.tool.FragmentUtil;

public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        FragmentUtil.setRootFragment(MaterialDesignActivity.this, R.id.root_material, new ShowToastFragment());
    }
}
