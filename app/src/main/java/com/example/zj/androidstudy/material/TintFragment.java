package com.example.zj.androidstudy.material;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

public class TintFragment extends BaseFragment {
    private TextView mRectTextView;
    private TextView mCircleTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tint;
    }

    @Override
    protected void initViews(View view) {
        mRectTextView = view.findViewById(R.id.tv_rect);
        mCircleTextView = view.findViewById(R.id.tv_circle);

        // 获取OutLine
        ViewOutlineProvider providerRect = new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                // 修改outline形状
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
            }
        };
        ViewOutlineProvider providerCircle = new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        // 重新设置外形
        mRectTextView.setOutlineProvider(providerRect);
        mCircleTextView.setOutlineProvider(providerCircle);
    }

    @Override
    protected void initWorkers() {

    }
}
