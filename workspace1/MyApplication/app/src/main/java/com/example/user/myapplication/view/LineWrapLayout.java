package com.example.user.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by user on 2017/5/24.
 */
public class LineWrapLayout extends ViewGroup {
    private int line_heght;
    public LineWrapLayout(Context context) {
        super(context);
    }

    public LineWrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int line_heght = 0;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int Height = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int xPos = getPaddingLeft();
        int yPos = getPaddingTop();
        for(int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            if(child.getVisibility() != GONE){
                LayoutParams layoutParams = child.getLayoutParams();
                Log.i("layoutParams.width",layoutParams.width+"    "+layoutParams.height);


                int wSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
                int hSpec = MeasureSpec.makeMeasureSpec(layoutParams.height,MeasureSpec.EXACTLY);
                child.measure(wSpec, hSpec);
                int childW = child.getMeasuredWidth();
                int childH = child.getMeasuredHeight();
                Log.i("childW",childW+"    "+childH);
                line_heght = Math.max(line_heght, childH);
                if(xPos+childW>width){
                    xPos = getPaddingLeft();
                    yPos = yPos+line_heght;
                }
                xPos= xPos+childW;
            }
        }
        this.line_heght = line_heght;
        //对高度没有限制
        if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.UNSPECIFIED){
            Height = yPos+line_heght;
        }else if (MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.AT_MOST){
            if(line_heght+yPos<Height){
                Height = yPos+line_heght;
            }
        }else{
            Height = yPos+line_heght;
        }


        setMeasuredDimension(width,Height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int count = getChildCount();
        int width = r-l;
        int xPos = getPaddingLeft();
        int yPos = getPaddingTop();
        for (int i = 0;i<count;i++){
            View chid = getChildAt(i);
            if(chid.getVisibility()!=GONE){
                int childw= chid.getMeasuredWidth();
                int childh = chid.getMeasuredHeight();
                if(xPos+childw>width){
                    xPos= getPaddingLeft();
                    yPos=yPos+line_heght;
                }
                chid.layout(xPos,yPos,xPos+childw,yPos+childh);
                xPos+=childw;
            }
        }
    }
}
