package com.example.zj.androidstudy.view.weather

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.util.concurrent.ConcurrentHashMap

/**
 * 天气小太阳
 * ref: https://www.jianshu.com/p/2c9dc35f3aad
 */
class WeatherView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val mPaint: Paint = Paint()
    private val mRectCenterArc: RectF = RectF()
    private val mRectOutSideArc: RectF = RectF()
    private val mRectFSunFlower: RectF = RectF()
    private val mRectFCloudShadow: RectF = RectF()
    private val mRectFSunShadow: RectF = RectF()
    private var minRingCenterWidth = 0f
    private var ringWidth = 0f
    private val ringColor: Int = Color.parseColor("#ffcf45")
    private var centerArcAngle = 0f
    private var centerArcEndAngle: Float = 0f
    private var outSideArcAngle = 0f
    private var outSideArcStartAngle: Float = 0f
    private var isDrawArcLine = false
    private var isDrawSun = false
    private var isDrawRing = false
    private var isDrawCloud = false
    private var isDrawCloudShadow = false
    private var isDrawSunShadow = false
    private val sunWidth = 0f
    private val finalSunWidth = 0f
    private val maxSunFlowerWidth = 0f
    private val sunRotateAngle = 0f
    private val cloudShadowAlpha = 0
    private val mPath: Path? = null
    private val mCloudShadowPath: Path? = null

    //云朵的组成部分信息
//    private val mCircleInfoTopOne: CircleInfo? = null //云朵的组成部分信息
//    private val mCircleInfoTopTwo: CircleInfo? = null //云朵的组成部分信息
//    private val mCircleInfoBottomOne: CircleInfo? = null //云朵的组成部分信息
//    private val mCircleInfoBottomTwo: CircleInfo? = null //云朵的组成部分信息
//    private val mCircleInfoBottomThree: CircleInfo? = null

    //太阳花朵，白云的Shade
    private val mCloudLinearGradient: LinearGradient? = null //太阳花朵，白云的Shade
    private val mFlowerLinearGradient: LinearGradient? = null //太阳花朵，白云的Shade
    private val mFlowerRotateLinearGradient: LinearGradient? = null
    private val cloudShadowColor: Int = android.graphics.Color.parseColor("#bc9a31")
    private val sunShadowWidth = 0f
    private var sunShadowHeight: kotlin.Float = 0f
    private val sunShadowColor: Int = Color.parseColor("#bac3c3")

    //存储所有动画的ValueAnimator方便管理
    private val animMap: ConcurrentHashMap<String, ValueAnimator> = ConcurrentHashMap()

    //动画是否开始
    private var isStart = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, (width * 1.4f).toInt())
    }

    fun startAnim() {
        resetAnim()
        startInvalidateAnim()
    }

    private fun startInvalidateAnim() {
        isStart = true
        var valueAnimator: ValueAnimator? = animMap[ANIM_CONTROL_INVALIDATE]
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f)
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = 300
            valueAnimator.repeatCount = ValueAnimator.INFINITE
            valueAnimator.addUpdateListener(AnimatorUpdateListener {
                invalidate()
            })
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    startRing()
                }
            })
            animMap[ANIM_CONTROL_INVALIDATE] = valueAnimator
        }
        startValueAnimator(valueAnimator!!)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawZoomRing(canvas)
        drawArcLine(canvas)
    }

    private fun drawZoomRing(canvas: Canvas) {
        mPaint.shader = null
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL
        mPaint.color = ringColor
        canvas.drawCircle((measuredWidth / 2).toFloat(), (measuredHeight / 2).toFloat(), ringWidth / 2, mPaint) //外圆大圆
        mPaint.color = Color.WHITE
        canvas.drawCircle((measuredWidth / 2).toFloat(), (measuredHeight / 2).toFloat(), minRingCenterWidth / 2, mPaint) //内圆小圆
    }

    private fun drawArcLine(canvas: Canvas) {
        mPaint.color = ringColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = (measuredWidth / 40).toFloat()
        canvas.drawArc(mRectCenterArc, centerArcEndAngle - centerArcAngle, centerArcAngle, false, mPaint) //画内弧
        mPaint.strokeWidth = (measuredWidth / 25).toFloat()
        canvas.drawArc(mRectOutSideArc, outSideArcStartAngle, outSideArcAngle, false, mPaint) //画外弧
    }

    private fun startRing() {
        isDrawRing = true
        var zoomAnim = animMap[ANIM_RING_ZOOM]
        if (zoomAnim == null) {
            zoomAnim = ValueAnimator.ofFloat().setDuration(500)
            zoomAnim.addUpdateListener(AnimatorUpdateListener {
                ringWidth = it.animatedValue as Float
            })
            zoomAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    startArcLine()
                }
            })
            animMap[ANIM_RING_ZOOM] = zoomAnim
        }
        zoomAnim?.setFloatValues(ringWidth, measuredWidth * 0.8f)
        startValueAnimator(zoomAnim!!)

        var circleZoom = animMap[ANIM_RING_CIRCLE_ZOOM]
        if (circleZoom == null) {
            circleZoom = ValueAnimator.ofFloat().setDuration(300)
            circleZoom.addUpdateListener(AnimatorUpdateListener {
                minRingCenterWidth = it.animatedValue as Float
            })
            circleZoom.startDelay = 300
            circleZoom.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isDrawRing = false
                }
            })
            animMap[ANIM_RING_CIRCLE_ZOOM] = circleZoom
        }
        circleZoom?.setFloatValues(minRingCenterWidth, measuredWidth * 0.8f)
        startValueAnimator(circleZoom!!)
    }

    private fun startArcLine() {
        isDrawArcLine = true
        // 内部圆弧长度控制
        var centerArcLineAngleAnim = animMap[ANIM_ARC_LINE_CENTER_ANGLE]
        if (centerArcLineAngleAnim == null) {
            centerArcLineAngleAnim = ValueAnimator.ofFloat().setDuration(500)
            centerArcLineAngleAnim.addUpdateListener(AnimatorUpdateListener {
                centerArcAngle = it.animatedValue as Float
            })
            animMap[ANIM_ARC_LINE_CENTER_ANGLE] = centerArcLineAngleAnim
        }
        centerArcLineAngleAnim!!.setFloatValues(centerArcAngle, 180f, 0f)
        startValueAnimator(centerArcLineAngleAnim)

        // 内部圆弧移动控制
        var centerArcLineMoveAnim = animMap[ANIM_ARC_LINE_CENTER_MOVE]
        if (centerArcLineMoveAnim == null) {
            centerArcLineMoveAnim = ValueAnimator.ofFloat().setDuration(400)
            centerArcLineMoveAnim.addUpdateListener(AnimatorUpdateListener { animation ->
                centerArcEndAngle = animation.animatedValue as Float
            })
            animMap[ANIM_ARC_LINE_CENTER_MOVE] = centerArcLineMoveAnim
        }
        centerArcLineMoveAnim!!.setFloatValues(centerArcEndAngle, 630f)
        startValueAnimator(centerArcLineMoveAnim)
        // 外部圆弧长度控制
        var outSizeArcLineAngleAnim = animMap[ANIM_ARC_LINE_OUTSIZE_ANGLE]
        if (outSizeArcLineAngleAnim == null) {
            outSizeArcLineAngleAnim = ValueAnimator.ofFloat().setDuration(400)
            outSizeArcLineAngleAnim.addUpdateListener(AnimatorUpdateListener { animation ->
                outSideArcAngle = animation.animatedValue as Float })
            animMap[ANIM_ARC_LINE_OUTSIZE_ANGLE] = outSizeArcLineAngleAnim
        }
        outSizeArcLineAngleAnim!!.setFloatValues(outSideArcAngle, 180f, 0f)
        startValueAnimator(outSizeArcLineAngleAnim)
        // 外部圆弧移动控制
        var outSizeArcLineMoveAnim = animMap[ANIM_ARC_LINE_OUTSIZE_MOVE]
        if (outSizeArcLineMoveAnim == null) {
            outSizeArcLineMoveAnim = ValueAnimator.ofFloat().setDuration(300)
            outSizeArcLineMoveAnim.addUpdateListener(AnimatorUpdateListener { animation ->
                outSideArcStartAngle = animation.animatedValue as Float })
            animMap[ANIM_ARC_LINE_OUTSIZE_MOVE] = outSizeArcLineMoveAnim
        }
        outSizeArcLineMoveAnim!!.setFloatValues(outSideArcStartAngle, -90f)
        startValueAnimator(outSizeArcLineMoveAnim)
//        postDelayed({
//            startSun()
//        }, 250)
    }

    private fun startValueAnimator(valueAnimator: ValueAnimator) {
        if (isStart) {
            valueAnimator.start()
        }
    }

    private fun resetAnim() {
        stopAnimAndRemoveCallbacks()
        //是否画相应的内容
        isDrawArcLine = false
        isDrawSun = false
        isDrawRing = false
        isDrawCloud = false
        isDrawCloudShadow = false
        isDrawSunShadow = false
        //圆环部分
        minRingCenterWidth = 10f
        ringWidth = 3 * minRingCenterWidth
        //圆弧部分
        centerArcAngle = 2f
        centerArcEndAngle = 270 + centerArcAngle
        outSideArcStartAngle = 180f
        outSideArcAngle = 90f

        mRectCenterArc.set((measuredWidth / 2 - measuredWidth / 6).toFloat(), (measuredHeight / 2 - measuredWidth / 6).toFloat()
                , (measuredWidth / 2 + measuredWidth / 6).toFloat(), (measuredHeight / 2 + measuredWidth / 6).toFloat())
        mRectOutSideArc.set((measuredWidth / 2 - measuredWidth / 4).toFloat(), (measuredHeight / 2 - measuredWidth / 4).toFloat()
                , (measuredWidth / 2 + measuredWidth / 4).toFloat(), (measuredHeight / 2 + measuredWidth / 4).toFloat())

        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimAndRemoveCallbacks()
    }

    private fun stopAnimAndRemoveCallbacks() {
        isStart = false
        for ((_, value) in animMap) {
            value.end()
        }
        this.handler?.removeCallbacksAndMessages(null)
    }

    companion object {
        const val TAG = "WeatherView"
        const val ANIM_CONTROL_INVALIDATE = "anim_control_invalidate"
        const val ANIM_RING_ZOOM = "anim_ring_zoom"
        const val ANIM_RING_CIRCLE_ZOOM = "anim_ring_circle_zoom"
        const val ANIM_ARC_LINE_CENTER_ANGLE = "anim_arc_line_center_angle"
        const val ANIM_ARC_LINE_CENTER_MOVE = "anim_arc_line_center_move"
        const val ANIM_ARC_LINE_OUTSIZE_ANGLE = "anim_arc_line_outsize_angle"
        const val ANIM_ARC_LINE_OUTSIZE_MOVE = "anim_arc_line_outsize_move"
    }
}