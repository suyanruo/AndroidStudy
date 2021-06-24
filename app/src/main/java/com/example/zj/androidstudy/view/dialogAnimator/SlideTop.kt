package com.example.zj.androidstudy.view.dialogAnimator

import android.animation.ObjectAnimator
import android.view.View

/**
 * Created on 2021/6/18.
 * 从上面滑入
 */
class SlideTop: BaseEffects() {
    override fun setupAnimation(view: View) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", -300f, 0f).setDuration(getDuration()),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(getDuration() * 3 / 2)
        )
    }
}