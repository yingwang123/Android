package com.example.user.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.user.myapplication.R;

/**
 * Created by user on 2017/4/13.
 */
public class TestMainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testmain);
        findViewById(R.id.btn_stack).setOnClickListener(this);
        findViewById(R.id.btn_servicetest).setOnClickListener(this);
    }

    Intent intent = new Intent();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stack:
                intent.setClass(this,ActivityA.class);
                break;
            case R.id.btn_servicetest:
                intent.setClass(this,ServiceActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
