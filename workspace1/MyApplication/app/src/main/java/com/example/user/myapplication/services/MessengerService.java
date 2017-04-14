package com.example.user.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 2017/4/14.
 */
public class MessengerService extends Service {
    public static final int MSG_SAY_HELLO=1;

    class IncommingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello messenger", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    final Messenger mMessenger = new Messenger(new IncommingHandler());
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "bind", Toast.LENGTH_LONG).show();
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MessengerService","oncreate");

    }
}
