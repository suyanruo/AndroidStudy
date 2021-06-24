package com.example.zj.androidstudy.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * Created on 2021/6/21.
 *
 */
private const val MIN_ALPHA = 0f
private const val MIN_SCALE = 0.92f

class NGGuidePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pageWidth = width
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 1 -> {
                    if (position < 0) {
                        // 阻止消失页面的滑动
                        translationX = -pageWidth * position
                    } else {
                        // 直接设置出现的页面到底
                        translationX = pageWidth.toFloat()
                        // 阻止出现页面的滑动
                        translationX = -pageWidth * position
                    }

                    val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    val alphaFactor = MIN_ALPHA.coerceAtLeast(1 - abs(position) * 2)
                    alpha = alphaFactor
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}