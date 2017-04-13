package com.example.user.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by user on 2017/4/13.
 */
public class HelloService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
            stopSelf();
        }
    }
    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();
    public class LocalBinder extends Binder{

        public HelloService getService(){
            return  HelloService.this;
        }
    }

    public int getRandomNumber(){
        return  mGenerator.nextInt(100);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service starting",Toast.LENGTH_LONG).show();
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"service done",Toast.LENGTH_LONG).show();
    }
}
