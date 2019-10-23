package com.example.zj.androidstudy.material;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.view.CustomSlideView;

public class SlideViewFragment extends BaseFragment {
    private CustomSlideView mCustomSlideView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(inflater.inflate(R.layout.fragment_slide_view, container, false));
    }

    @Override
    protected void initViews(View view) {
        mCustomSlideView = view.findViewById(R.id.custom_slide_view);
        // 设置view平移
        // 方式三：使用补间动画  setAnimation方法
//        mCustomSlideView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate));
        // 方法四：使用属性动画
//        ObjectAnimator.ofFloat(mCustomSlideView, "translationX", 0, 300).setDuration(1000).start();
//        mCustomSlideView.smoothScroll(-400, 0);
    }

    @Override
    protected void initWorkers() {
        startAnimatorFromXml(mCustomSlideView);
    }

    private void playObjectAnimator(View target) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationX", 0, 1000, 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(target, "scaleX", 1f, 2f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(target, "rotationX", 0, 90, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(animator).with(animator2).after(animator3);
        animatorSet.start();
    }

    /**
     * 使用PropertyValuesHolder进行组合动画
     * @param target
     */
    private void playProperty(View target) {
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, holder, holder2);
        animator.setDuration(1000).start();
    }

    private void startAnimatorFromXml(View target) {
        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.scale);
        animator.setTarget(target);
        animator.start();
    }
}
