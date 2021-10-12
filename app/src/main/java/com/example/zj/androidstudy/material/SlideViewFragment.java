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
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.view.CustomSlideView;

public class SlideViewFragment extends BaseFragment implements View.OnClickListener {
    private CustomSlideView mCustomSlideView;
    private Button mBtnScroller;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_slide_view;
    }

    @Override
    protected void initViews(View view) {
        mCustomSlideView = view.findViewById(R.id.custom_slide_view);
        // 设置view平移
        mBtnScroller = view.findViewById(R.id.btn_scroller);
        mBtnScroller.setOnClickListener(this);
        view.findViewById(R.id.btn_view_animation).setOnClickListener(this);
        view.findViewById(R.id.btn_object_animation_one).setOnClickListener(this);
        view.findViewById(R.id.btn_object_animation_two).setOnClickListener(this);
        view.findViewById(R.id.btn_object_animation_three).setOnClickListener(this);
        view.findViewById(R.id.btn_object_animation_four).setOnClickListener(this);
    }

    @Override
    protected void initWorkers() {
    }

    /**
     * 借助AnimatorSet，根据属性设置属性动画
     */
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
     */
    private void playProperty(View target) {
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("translationX", 0f, 1000f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, holder, holder2, holder3);
        animator.setDuration(1000).start();
    }

    /**
     * 根据xml文件设置属性动画
     */
    private void startAnimatorFromXml(View target) {
        Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.scale);
        animator.setTarget(target);
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroller:
                // 方式一：使用Scroller.这种平移不能改变view位置，所以移动后位置不响应点击事件
                mCustomSlideView.smoothScroll(-400, 0);
                break;
            case R.id.btn_view_animation:
                // 方式二：使用补间动画setAnimation方法.这种平移不能改变view位置，所以移动后位置不响应点击事件
                mCustomSlideView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate));
                break;
            case R.id.btn_object_animation_one:
                // 方法三：使用属性动画.可以处理点击事件
                ObjectAnimator.ofFloat(mCustomSlideView, "translationX", 0, 300).setDuration(1000).start();
                break;
            case R.id.btn_object_animation_two:
                playObjectAnimator(mCustomSlideView);
                break;
            case R.id.btn_object_animation_three:
                startAnimatorFromXml(mCustomSlideView);
                break;
            case R.id.btn_object_animation_four:
                playProperty(mCustomSlideView);
                break;
            default:
                break;
        }
    }
}
