package com.example.zj.androidstudy.view

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.view.dialogAnimator.BaseEffects
import com.example.zj.androidstudy.view.dialogAnimator.FadeOut
import com.example.zj.androidstudy.view.dialogAnimator.SlideBottom

/**
 * Created on 2021/6/18.
 *
 */
class CustomDialog(context: Context) : Dialog(context, R.style.DialogStyle) {
    private val mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_signin, null)

    init {
        setContentView(mRootView)
        setCanceledOnTouchOutside(true)
        initWindow()
    }

    private fun initWindow() {
        val window = window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun show() {
        super.show()
        val type: BaseEffects = SlideBottom()
        type.start(mRootView.rootView)
    }

    override fun dismiss() {
        FadeOut().apply {
            start(mRootView.rootView)
            getAnimatorSet().addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super@CustomDialog.dismiss()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
        }
    }
}