package com.example.user.myapplication.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.user.myapplication.activities.TwoActicity;

/**
 * Created by user on 2016/9/20.
 */
public class GPXService extends Service {
    public static final String GPX_SERVICE="GPXSService";
    private TestBinder binder=new TestBinder();
    private Thread testThread;
    private boolean isStart=false;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(GPX_SERVICE, "oncreate service");
        startForeground();

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startForeground(){
        Notification.Builder builder=new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1,
                new Intent(this, TwoActicity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setTicker("前台服务开启");
        builder.setContentText("第一个前台服务");
        builder.setContentTitle("前台服务");
        builder.setNumber(1);
        Notification notification=builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Log.e(GPX_SERVICE,"发送通知");
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100,notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(GPX_SERVICE, "onstartCommand");
        return super.onStartCommand(intent,flags,startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(GPX_SERVICE,"ondestroy service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class TestBinder extends Binder {

        public void testMethod() {
            Log.e("TestService", "执行 testMethod()");
        }

    }

    //测试是否ANR
    private  void testANR(){
        try {
            Thread.sleep(50000);
        } catch (Exception e) {
        }
    }

    private void startThread(){
        stopThread();
        isStart = true;
        if (testThread == null) {
            testThread = new Thread(runnable);
            testThread.start();
        }
    }

    private void stopThread(){
        try {
            isStart = false;
            if (null != testThread && Thread.State.RUNNABLE == testThread.getState()) {
                try {
                    Thread.sleep(500);
                    testThread.interrupt();
                } catch (Exception e) {
                    testThread = null;
                }
            }
            testThread = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testThread = null;
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while(isStart) {
                Log.e("TestService", "runnable");
            }

        }
    };
}
