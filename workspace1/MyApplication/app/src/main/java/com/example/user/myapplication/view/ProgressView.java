package com.example.user.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.user.myapplication.R;

import static android.graphics.Color.*;


/**
 * Created by user on 2017/5/22.
 */
public class ProgressView extends View {
    private Paint paint,paint2,paint3;
    private int widht =0;
    private int rate ;
    private String str="0";
    private int i;
    private int textColor;
    private float textSize;
    private float rectHeight;
    private int beforeColor,endColor;
    private RectF beforeRect,endRect;
    private int currentProgress;
    public ProgressView(Context context) {
       this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        textColor = attributes.getColor(R.styleable.ProgressView_textColor, Color.RED);
        textSize = attributes.getFloat(R.styleable.ProgressView_textSize, spToPx(5));
        rectHeight = attributes.getFloat(R.styleable.ProgressView_rectHeight, dpToPx(2));
        beforeColor = attributes.getColor(R.styleable.ProgressView_beforColor, Color.RED);
        endColor = attributes.getColor(R.styleable.ProgressView_endColor, Color.GRAY);
        currentProgress = attributes.getInteger(R.styleable.ProgressView_currentpProgress,0);
        attributes.recycle();
        invaliPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
    }


    private int measure(int measureSpec,boolean isWidth){
        int result ;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth?getPaddingRight()+getPaddingLeft():getPaddingBottom()+getPaddingTop();
        //如果是指定大小
        if(mode==MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = isWidth?getSuggestedMinimumWidth():getSuggestedMinimumHeight();
            result+=padding;
            if(mode==MeasureSpec.AT_MOST){
                if(isWidth)
                    result = Math.max(result,size);
                else
                    result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) Math.max(rectHeight,textSize);
    }
    @Override
    protected int getSuggestedMinimumWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        widht = getMeasuredWidth();

        drawWidthBar(canvas, currentProgress);
        //drawWidthBar(canvas);

    }

   public void setProgress(int i){
       this.currentProgress=i;
       invalidate();
   }


    private void drawWidthBar(Canvas canvas, int i){
        beforeRect.set(getPaddingLeft(), (float) (getHeight() / 2.0 - dpToPx(rectHeight) / 2.0)
                , (getWidth() - getPaddingLeft() - getPaddingRight()) / 100 * currentProgress
                , (float) (getHeight() / 2.0f + dpToPx(rectHeight) / 2.0));
        float textWidth = paint2.measureText(currentProgress+"%");
        float textDrawHeight = (float) (getHeight()/2.0-(paint2.descent()+paint2.ascent())/2.0);
        canvas.drawText(currentProgress + "%"
                , (getWidth() - getPaddingLeft() - getPaddingRight()) / 100 * currentProgress
                , textDrawHeight, paint2);
        endRect.set((getWidth() - getPaddingLeft() - getPaddingRight()) / 100 * currentProgress + textWidth
                , (float) (getHeight() / 2.0 - dpToPx(rectHeight) / 2.0)
                , getWidth() - getPaddingRight()
                ,(float) (getHeight()/2.0+dpToPx(rectHeight)/2.0));

        canvas.drawRect(beforeRect, paint);
        canvas.drawRect(endRect, paint3);
    }
    private void invaliPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(beforeColor);
        paint2.setColor(textColor);
        paint2.setTextSize(spToPx(textSize));
        paint3.setColor(endColor);
        beforeRect = new RectF();
        endRect = new RectF();
    }
    private float spToPx(float sp){
        float rate = getResources().getDisplayMetrics().scaledDensity;
        return rate*sp;
    }
    private float dpToPx(float dp){
        float rate = getResources().getDisplayMetrics().density;
        return rate*dp;
    }

}
