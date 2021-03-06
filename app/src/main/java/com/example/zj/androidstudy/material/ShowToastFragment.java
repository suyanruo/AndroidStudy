package com.example.zj.androidstudy.material;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

public class ShowToastFragment extends BaseFragment {
    // SnackBar配合CoordinatorLayout使用可实现右滑删除效果
    private CoordinatorLayout mRlFragment;
    private Button mBtnShowToast;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show_toast;
    }

    @Override
    protected void initViews(View view) {
        mRlFragment = view.findViewById(R.id.rl_toast_fragment);
        mBtnShowToast = view.findViewById(R.id.btn_show_toast);

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
