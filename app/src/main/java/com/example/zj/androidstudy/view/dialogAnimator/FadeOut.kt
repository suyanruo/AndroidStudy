package com.example.zj.androidstudy.view.dialogAnimator

import android.animation.ObjectAnimator
import android.view.View

/**
 * Created on 2021/6/18.
 * 淡入
 */
class FadeOut: BaseEffects() {
    override fun setupAnimation(view: View) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(getDuration())
        )
    }
}