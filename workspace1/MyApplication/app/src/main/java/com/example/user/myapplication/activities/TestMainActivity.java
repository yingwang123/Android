package com.example.user.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.myapplication.R;
import com.example.user.myapplication.view.ProgressView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by user on 2017/4/13.
 */
public class TestMainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_stack)
    Button btn_stack;
    @Bind(R.id.btn_servicetest)
    Button btn_servicetest;
    @Bind(R.id.btn_animation)
    Button btn_animation;
    Intent intent = new Intent();
    @Bind(R.id.pv_1)
    ProgressView pv_1;
    private int i=0;
    Timer timer =new Timer();
    @Override
    protected int initLayout() {
        return R.layout.activity_testmain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(i<100)
                            pv_1.setProgress(i++);
                    }
                });

            }
        },1000,100);
    }
    @OnClick({R.id.btn_stack
    ,R.id.btn_servicetest,
            R.id.btn_animation})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stack:
                intent.setClass(this,ActivityA.class);
                break;
            case R.id.btn_servicetest:
                intent.setClass(this,ServiceActivity.class);
                break;
            case R.id.btn_animation:
                intent.setClass(this,AnimationActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
