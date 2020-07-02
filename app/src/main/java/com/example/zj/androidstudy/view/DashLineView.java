package com.example.zj.androidstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.zj.androidstudy.R;

public class DashLineView extends View {

  private Context context;
  private Paint paint = new Paint();
  private int orientation = 0;
  private int lineColor;
  private int lineSpace;
  private int lineWidth;
  private int blankSpace;
  private Path path = new Path();
  private RectF rectF;
  private Paint circlePaint = new Paint();
  private Path circlePath = new Path();
  /* 圆角的半径，依次为左上角xy半径，右上角，右下角，左下角 */
  private final float[] mRadius = new float[8];

  public DashLineView(Context context) {
    this(context, null);
  }

  public DashLineView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DashLineView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    initAttrs(attrs);
    init();
  }

  private void initAttrs(AttributeSet attrs) {
    if (attrs == null) {
      return;
    }
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashLineView);
    orientation = typedArray.getInteger(R.styleable.DashLineView_orientation, 0);
    lineColor = typedArray.getInteger(R.styleable.DashLineView_line_color, Color.WHITE);
    lineSpace = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_line_space, dp2px(5));
    lineWidth = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_dash_line_width, dp2px(2));
    blankSpace = typedArray.getDimensionPixelOffset(R.styleable.DashLineView_blank_space, dp2px(5));
  }

  private void init() {
    paint.setColor(lineColor);
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.STROKE);
    //绘制长度为lineSpace的实线后再绘制长度为blankSpace的空白区域，0位间隔
    paint.setPathEffect(new DashPathEffect(new float[]{lineSpace, blankSpace}, 0));

    circlePaint.setColor(lineColor);
    circlePaint.setAntiAlias(true);
    circlePaint.setStyle(Paint.Style.FILL);
  }

  public void setCornerRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
    mRadius[0] = mRadius[1] = leftTop;
    mRadius[2] = mRadius[3] = rightTop;
    mRadius[4] = mRadius[5] = rightBottom;
    mRadius[6] = mRadius[7] = leftBottom;
    invalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //设置画笔宽度
    paint.setStrokeWidth(lineWidth);
    if (orientation == 0) { //水平
      rectF = new RectF(getMeasuredWidth() - getMeasuredHeight() / 2, 0, getMeasuredWidth(), getMeasuredHeight());
      setCornerRadius(getMeasuredHeight() / 2, 0, 0, getMeasuredHeight() / 2);
    } else {
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (orientation == 0) { //水平
      path.moveTo(0, getMeasuredHeight() / 2);
      path.lineTo(getWidth() - getMeasuredHeight() / 2, getMeasuredHeight() / 2);
      circlePath.addRoundRect(rectF, mRadius, Path.Direction.CW);
    } else { //垂直
      path.moveTo(0, 0);
      path.lineTo(0, getHeight());
    }
    canvas.drawPath(circlePath, circlePaint);
    canvas.drawPath(path, paint);
  }

  /**
   * dp转px
   */
  public int dp2px(float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
  }
}
