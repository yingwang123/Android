package com.example.user.myapplication.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.services.HelloIntentService;
import com.example.user.myapplication.services.HelloService;

/**
 * Created by user on 2017/4/13.
 */
public class ServiceActivity extends Activity implements View.OnClickListener {
    HelloService  mService;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ((Button)findViewById(R.id.btn_service)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_intentService)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_bindService)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_bindServiceResult)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_service:
                Intent intent = new Intent(this, HelloService.class);
                startService(intent);
                break;
            case R.id.btn_intentService:
                Intent intent1 = new Intent(this, HelloIntentService.class);
                startService(intent1);
                break;
            case R.id.btn_bindService:
                Intent intent2 = new Intent(this, HelloService.class);
                bindService(intent2, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_bindServiceResult:
                if(mBound){
                    int num = mService.getRandomNumber();
                    Toast.makeText(this,"number:"+num,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HelloService.LocalBinder binder = (HelloService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }
}
