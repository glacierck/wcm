package com.frame.commonframe.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.frame.commonframe.R;

public class FlikerProgressBar extends View {
    private int DEFAULT_HEIGHT_DP = 35;

    private int borderWidth;

    private float maxProgress = 100f;

    private Paint textPaint;

    private Paint bgPaint;

    private Paint pgPaint;

    private String progressText = "";

    private Rect textRect;

    private RectF bgRectf;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    /**
     * 当前进度
     */
    private float progress;

    /**
     * 进度文本、边框、进度条颜色
     */
    private int progressColor;

    private int textSize;

    private int radius;

    BitmapShader bitmapShader;

    public FlikerProgressBar(Context context) {
        this(context, null, 0);

    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FlikerProgressBar);
        try {
            textSize = (int) ta.getDimension(R.styleable.FlikerProgressBar_textSize, 12);
            radius = (int) ta.getDimension(R.styleable.FlikerProgressBar_radius, 0);
            borderWidth = (int) ta.getDimension(R.styleable.FlikerProgressBar_borderWidth, 2);
            borderBackColor = ta.getColor(R.styleable.FlikerProgressBar_borderBackColor, Color.parseColor("#484848"));//边框的默认颜色
            progressColor = ta.getColor(R.styleable.FlikerProgressBar_progressColor, Color.parseColor("#40c4ff"));
            progressTextColor = ta.getColor(R.styleable.FlikerProgressBar_progressTextColor, Color.parseColor("#000000"));

        } finally {
            ta.recycle();
        }
    }


    private void initPaint(){
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
    }



    private void init() {
        textRect = new Rect();
        int width = getMeasuredWidth() - borderWidth;
        int height = getMeasuredHeight() - borderWidth;
        width = width<=0?1:width;
        height = height<=0?1:height;
        bgRectf = new RectF(borderWidth, borderWidth,width, height);
        initPgBimap( width,height);
    }

    private void initPgBimap(int width ,int height) {
        pgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int height = 0;
//        int width = 0;
//        switch (heightSpecMode) {
//            case MeasureSpec.AT_MOST:
//                height = dp2px(DEFAULT_HEIGHT_DP);
//                break;
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.UNSPECIFIED:
//                height = heightSpecSize;
//                break;
//        }
//        switch (widthSpecMode) {
//            case MeasureSpec.AT_MOST:
//                width = dp2px(DEFAULT_HEIGHT_DP);
//                break;
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.UNSPECIFIED:
//                width = widthSpecSize;
//                break;
//        }
//        setMeasuredDimension(width, height);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);


        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的宽
            width = dp2px(DEFAULT_HEIGHT_DP) * 2;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的高
            height = dp2px(DEFAULT_HEIGHT_DP) * 2;
        }
        //如果不是自定义设置进度条高度，就直接把高度当作进度条高度
        //如果有图片，就为图片预留空间
        //传入处理后的宽高
        setMeasuredDimension(width, height);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景
        drawBackGround(canvas);

        //进度
        drawProgress(canvas);

        //进度text
        drawProgressText(canvas);

        //变色处理
//        drawColorProgressText(canvas);
    }

    private int borderBackColor;

    public void setBorderBackColor(int borderBackColor) {
        this.borderBackColor = borderBackColor;
    }

    /**
     * 边框
     *
     * @param canvas
     */
    private void drawBackGround(Canvas canvas) {
//        bgPaint.setColor(progressColor);
        bgPaint.setColor(borderBackColor);
        //left、top、right、bottom不要贴着控件边，否则border只有一半绘制在控件内,导致圆角处线条显粗
        canvas.drawRoundRect(bgRectf, radius, radius, bgPaint);
    }

    /**
     * 进度
     */
    private void drawProgress(Canvas canvas) {
        pgPaint.setColor(progressColor);

        float right = (progress / maxProgress) * (getMeasuredWidth() - borderWidth - 1);
        pgCanvas.save(Canvas.CLIP_SAVE_FLAG);
        pgCanvas.clipRect(borderWidth, borderWidth + 1, right, getMeasuredHeight() - borderWidth - 1);
        pgCanvas.drawColor(progressColor);
        pgCanvas.restore();

        //控制显示区域
        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        pgPaint.setShader(bitmapShader);
        canvas.drawRoundRect(bgRectf, radius, radius, pgPaint);
    }


    private int progressTextColor;

    public void setProgressTextColor(int progressTextColor) {
        this.progressTextColor = progressTextColor;
    }


    /**
     * 进度提示文本
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {

        textPaint.setColor(progressTextColor);
        textPaint.getTextBounds(getProgressText(), 0, getProgressText().length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        canvas.drawText(getProgressText(), xCoordinate, yCoordinate, textPaint);
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    /**
     * 变色处理
     *
     * @param canvas
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (getMeasuredWidth() - tWidth) / 2;
        float yCoordinate = (getMeasuredHeight() + tHeight) / 2;
        float progressWidth = (progress / maxProgress) * getMeasuredWidth();
        if (progressWidth > xCoordinate) {
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, getMeasuredHeight());
            canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
            canvas.restore();
        }
    }

    public void setProgress(float progress) {
        if (progress < maxProgress) {
            this.progress = progress;
        } else {
            this.progress = maxProgress;
        }
        invalidate();
    }


    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }


    public void setProgressColor(int color) {
        progressColor = color;
    }

    public float getProgress() {
        return progress;
    }


    private String getProgressText() {
        return progressText;
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
