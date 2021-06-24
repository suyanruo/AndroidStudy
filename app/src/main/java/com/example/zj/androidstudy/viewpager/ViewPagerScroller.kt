package com.example.zj.androidstudy.viewpager

import android.content.Context
import android.widget.Scroller

/**
 * Created on 2021/6/21.
 *
 */
class ViewPagerScroller(context: Context) : Scroller(context) {
    companion object {
        const val DEFAULT_DURATION = 500
    }

    private var mDuration = DEFAULT_DURATION

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setDuration(duration: Int) {
        mDuration = duration
    }
}