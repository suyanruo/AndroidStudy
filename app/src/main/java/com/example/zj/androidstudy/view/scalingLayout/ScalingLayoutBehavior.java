package com.example.zj.androidstudy.view.scalingLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Created by mertsimsek on 30/09/2017.
 */

public class ScalingLayoutBehavior extends CoordinatorLayout.Behavior<ScalingLayout> {

    private final float toolbarHeightInPixel;

    public ScalingLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        toolbarHeightInPixel = context.getResources().getDimensionPixelSize(R.dimen.dp_56);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ScalingLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ScalingLayout child, View dependency) {
        int totalScrollRange = ((AppBarLayout) dependency).getTotalScrollRange();
        child.setProgress((-dependency.getY()) / totalScrollRange);
        if (totalScrollRange + dependency.getY() > (float) child.getMeasuredHeight() / 2) {
            child.setTranslationY(totalScrollRange + dependency.getY() + toolbarHeightInPixel - (float) child.getMeasuredHeight() / 2);
        } else {
            child.setTranslationY(toolbarHeightInPixel);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
