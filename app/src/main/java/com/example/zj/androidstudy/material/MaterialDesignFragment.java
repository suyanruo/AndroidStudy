package com.example.zj.androidstudy.material;

import android.content.Intent;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.tool.FragmentUtil;

public class MaterialDesignFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_material_design;
    }

    @Override
    protected void initViews(View view) {
        view.findViewById(R.id.btn_go_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new ShowToastFragment());
            }
        });
        view.findViewById(R.id.btn_go_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new LoginFragment());
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
                FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new SlideViewFragment());
            }
        });
        view.findViewById(R.id.btn_go_tint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new TintFragment());
            }
        });
        view.findViewById(R.id.btn_go_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new RecyclerFragment());
            }
        });
    }

    @Override
    protected void initWorkers() {

    }
}
