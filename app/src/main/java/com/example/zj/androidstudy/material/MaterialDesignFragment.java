package com.example.zj.androidstudy.material;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.tool.FragmentUtil;
import com.example.zj.androidstudy.tool.ScreenUtil;

public class MaterialDesignFragment extends BaseFragment {
    private Button mBtnToast;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_material_design;
    }

    @Override
    protected void initViews(View view) {
        mBtnToast = view.findViewById(R.id.btn_go_toast);
        mBtnToast.setOnClickListener(v ->
            FragmentUtil.enterNewFragment(getActivity(), R.id.root_material, new ShowToastFragment()));
        animateView(mBtnToast);
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

    private void animateView(Button button) {
        ObjectAnimator z = ObjectAnimator.ofFloat(button, "translationZ", 0f, 30f, 0f, 30f, 0f).setDuration(2000);
        float size = ScreenUtil.px2dip(button.getTextSize());
        ObjectAnimator text = ObjectAnimator.ofFloat(button, "textSize", size, size * 2, size, size * 2, size);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000).playTogether(z, text);
        set.start();
    }

    @Override
    protected void initWorkers() {

    }
}
