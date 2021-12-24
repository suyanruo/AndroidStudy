package com.example.zj.androidstudy.material;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.FragmentUtil;

public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        FragmentUtil.setRootFragment(MaterialDesignActivity.this, R.id.root_material, new MaterialDesignFragment());
    }

    @Override
    public void onBackPressed() {
        // TODO: 2021/12/22 实现退出fragment功能
//        if (FragmentUtil.hasFragment(this)) {
//            FragmentUtil.exitLastFragment(this);
//        } else {
            super.onBackPressed();
//        }
    }
}
