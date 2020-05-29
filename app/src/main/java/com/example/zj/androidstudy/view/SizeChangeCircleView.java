package com.example.zj.androidstudy.view;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Created on 2020/5/29.
 */
public class SizeChangeCircleView extends View {
  private Paint mPaint;
  int lineWidth = 10;
  float ratio = 50;
  int mColor = Color.BLUE;
  float rotate;


  public SizeChangeCircleView(Context context) {
    this(context, null);
  }

  public SizeChangeCircleView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SizeChangeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(mColor);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setStrokeWidth(lineWidth);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPaint.setColor(mColor);

    canvas.drawCircle(getWidth() / 2, getHeight() / 2, ratio, mPaint);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public void start() {
    // 旋转动画，通过改变rotate值实现
//    ValueAnimator rotateAni = ValueAnimator.ofFloat(0, 360);
//    //无限重复
//    rotateAni.setRepeatCount(Animation.INFINITE);
//    // 设置监听，赋值给rotate
//    rotateAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//      @Override
//      public void onAnimationUpdate(ValueAnimator animation) {
//        rotate = (float) animation.getAnimatedValue();
//        invalidate();
//      }
//    });

    //放大动画，通过改变ratio实现
    final ValueAnimator ratioAnimator = ValueAnimator.ofFloat(50f, 100f);
    ratioAnimator.setInterpolator(new LinearInterpolator());
    ratioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        ratio = (float) animation.getAnimatedValue();
        invalidate();
      }
    });
    ratioAnimator.setRepeatCount(Animation.INFINITE);
    ratioAnimator.setRepeatMode(ValueAnimator.REVERSE);

    //颜色变化动画
    ValueAnimator colorAni = ValueAnimator.ofArgb(Color.BLUE, Color.GREEN);
    colorAni.setInterpolator(new LinearInterpolator());
    colorAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mColor = (int) animation.getAnimatedValue();
      }
    });
    colorAni.setRepeatCount(Animation.INFINITE);
    // 设置重复的模式为原样恢复，即放大后再按原路缩小，这样才不会出现跳动
    colorAni.setRepeatMode(ValueAnimator.REVERSE);

    AnimatorSet set = new AnimatorSet();
    set.setDuration(1000);
    set.play(ratioAnimator).with(colorAni);
    set.start();
  }
}
