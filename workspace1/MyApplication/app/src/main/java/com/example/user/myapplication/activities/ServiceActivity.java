package com.example.user.myapplication.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.services.HelloIntentService;
import com.example.user.myapplication.services.HelloService;
import com.example.user.myapplication.services.MessengerService;

/**
 * Created by user on 2017/4/13.
 */
public class ServiceActivity extends Activity implements View.OnClickListener {
    HelloService  mService;
    Messenger mServicemessenger;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ((Button)findViewById(R.id.btn_service)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_intentService)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_bindService)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_bindServiceResult)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_bindServiceM)).setOnClickListener(this);
    }

    //Context.BIND_AUTO_CREATE 创建尚未激活的服务
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_service:
                Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
                try {
                    mServicemessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
            case R.id.btn_bindServiceM:
                Intent intent3 = new Intent(this, MessengerService.class);
                bindService(intent3,mCommectionMessenger,Context.BIND_AUTO_CREATE);
                break;

        }
    }
    private ServiceConnection mCommectionMessenger = new ServiceConnection() {
        //系统用该方法传递onbind()方法返回Ibinder
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("mCommectionMessenger","onServiceConnected");
            mServicemessenger = new Messenger(service);
            mBound = true;
        }

        //系统与服务连接中断的时候会调用次方法，unbindservice（）不会调用次方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("mCommectionMessenger","onServiceDisconnected");
            mBound = false;
        }
    };
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

            //
            unbindService(mCommectionMessenger);
            unbindService(mConnection);
            mBound = false;
        }
    }
}
