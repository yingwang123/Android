package com.example.user.myapplication.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.user.myapplication.R;

import java.util.WeakHashMap;

/**
 * Created by user on 2017/4/26.
 */
public class AnimationActivity extends Activity implements View.OnClickListener {
    private Button button1,button2 ,button3,button4;
    private ImageView iv_object;
    private int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animatorlayout);
        bindViews();
        DisplayMetrics displayMetrics =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    private void bindViews() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        iv_object = (ImageView) findViewById(R.id.iv_object);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        iv_object.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                lineAnimator();
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.iv_object:
                break;
        }
    }
    private void moveView(View view, int rawX, int rawY) {
        int left = rawX - view.getWidth() / 2;
        int top = rawY - view.getHeight();
        int width = left + view.getWidth();
        int height = top + view.getHeight();
        view.layout(left, top, width, height);
    }
    private void lineAnimator(){
        ValueAnimator valueAnimator =ValueAnimator.ofInt(height, 0, height / 4, height / 2, height / 4 * 3, height,height / 2);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = width / 2;
                int y = Integer.valueOf(animation.getAnimatedValue().toString());
                moveView(iv_object, x, y);
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();

    }


}
