package com.example.zj.androidstudy.view.dialogAnimator

import android.animation.AnimatorSet
import android.view.View

/**
 * Created on 2021/6/18.
 *
 */
abstract class BaseEffects {
    companion object {
        var DURATION = 700
    }

    private var mDuration = DURATION.toLong()
    private var mAnimatorSet = AnimatorSet()

    abstract fun setupAnimation(view: View)

    fun start(view: View) {
        reset(view)
        setupAnimation(view)
        mAnimatorSet.start()
    }

    private fun reset(view: View) {
        view.pivotX = view.measuredWidth / 2.0f
        view.pivotY = view.measuredHeight / 2.0f
    }

    fun getAnimatorSet(): AnimatorSet {
        return mAnimatorSet
    }

    fun setDuration(duration: Long) {
        mDuration = duration
    }

    fun getDuration(): Long {
        return mDuration
    }
}