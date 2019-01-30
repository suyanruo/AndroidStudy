package com.example.zj.androidstudy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.activity.CoordinatorActivity;
import com.example.zj.androidstudy.activity.CoordinatorActivity2;
import com.example.zj.androidstudy.activity.TabLayoutActivity;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.tool.FragmentUtil;

public class ShowToastFragment extends BaseFragment {
    // SnackBar配合CoordinatorLayout使用可实现右滑删除效果
    private CoordinatorLayout mRlFragment;
    private Button mBtnShowToast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(inflater.inflate(R.layout.fragment_show_toast, container, false));
    }


    @Override
    protected void initViews(View view) {
        mRlFragment = view.findViewById(R.id.rl_material_fragment);
        mBtnShowToast = view.findViewById(R.id.btn_show_toast);
        view.findViewById(R.id.btn_go_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment((AppCompatActivity) getActivity(), R.id.root_material, ShowToastFragment.this, new LoginFragment());
            }
        });
        view.findViewById(R.id.btn_tablayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabLayoutActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_coordinatorlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CoordinatorActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_coordinatorlayout2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CoordinatorActivity2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_slide_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment((AppCompatActivity) getActivity(), R.id.root_material, ShowToastFragment.this, new SlideViewFragment());
            }
        });
        mBtnShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBar();
            }
        });
    }

    @Override
    protected void initWorkers() {
    }


    public void showSnackBar() {
        Snackbar.make(mRlFragment, "标题", Snackbar.LENGTH_LONG)
                .setAction("点击事件", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "click event", Toast.LENGTH_SHORT).show();
                    }
                }).setDuration(Snackbar.LENGTH_LONG).show();
    }
}
