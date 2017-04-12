package com.example.user.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Callable;



public class MainActivity extends ActionBarActivity {

    final DownUtil downUtil = new DownUtil("http://scimg.jb51.net/allimg/160716/105-160G61F250436.jpg", Environment.getExternalStorageDirectory()
            .getAbsolutePath() +"/com.soufun.ebzf/qq.jpg",4);
    EditText tv1;
    Handler handler=new Handler();
    Button stop;
    ProgressBar progressBar;
    NotificationManager manager;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GPXService.TestBinder testBinder = (GPXService.TestBinder) service;
            testBinder.testMethod();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1= (EditText) findViewById(R.id.tv1);
        stop= (Button) findViewById(R.id.btn1);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        Intent intent =new Intent(MainActivity.this,GPXService.class);
        this.startService(intent);

//        //停止service
//        Log.e("TestService", "执行 stopService()");
//        Intent intent1 =new Intent(MainActivity.this,GPXService.class);
//        stopService(intent);
//
//
//        //绑定service
//        Log.e("TestService", "执行 bindService()");
//        Intent intent2 =new Intent(MainActivity.this,GPXService.class);
//        bindService(intent,connection,BIND_AUTO_CREATE);
//
//        //解绑service
//        Log.e("TestService", "执行 unbindService()");
//        unbindService(connection);
        ScaleAnimation animation=new ScaleAnimation(0,2,0,2,0,0);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        //stop.setAnimation(animation);
        ObjectAnimator oa= ObjectAnimator.ofFloat(stop, "scaleX", 0,2);
        ObjectAnimator o1= ObjectAnimator.ofFloat(stop, "scaleY", 0,2);
        oa.setDuration(3000);
        o1.setDuration(3000);
        Paint  p =new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(3);
        p.isAntiAlias();
        Canvas canvas=new Canvas();

        canvas.drawCircle(100, 100, 100, p);
        canvas.drawColor(Color.RED);


        AnimatorSet set=new AnimatorSet();
        set.playTogether(oa, o1);
        set.start();
        manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将接口从线程队列中移除
                Log.i("click","onclick");
                handler.removeCallbacks(runnable);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,
                        new Intent(getApplicationContext(), TwoActicity.class), 0);
                Notification notify1 = new Notification();
                notify1.icon = R.mipmap.ic_launcher;
                notify1.tickerText = "TickerText:您有新短消息，请注意查收！";
                notify1.when = System.currentTimeMillis();
                notify1.setLatestEventInfo(getApplicationContext(), "Notification Title",
                        "This is the notification message", pendingIntent);
                notify1.number = 1;
                notify1.flags = Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
                manager.notify(1, notify1);


            }
        });
        stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("touch","action"+event.getAction());
                return false;

            }

        });
        //将线程接口立刻送到线程队列中
        Thread thread=new Thread(update_Thread);
        thread.start();
        handler.post(runnable);
        //update_progress.post(update_Thread);
        System.out.println("activity_id---->" + Thread.currentThread().getId());
        System.out.println("activity_name---->" + Thread.currentThread().getName());
        HandlerThread handlerThread=new HandlerThread("handlerthread");
        handlerThread.start();
        MyHandler handlermy=new MyHandler(handlerThread.getLooper());

        Message msg=handlermy.obtainMessage();
        Bundle bundle=new Bundle();
        bundle.putString("weather","晴天");
        bundle.putInt("temperature",30);
        msg.setData(bundle);
        msg.sendToTarget();


//        lList.add(1);
//        lList.add(2);
//        lList.add(3);
//        lList.add(4);
//        lList.add(5);
//        Collections.reverse(lList);
        //new PrintThread().start();
//        Account acct=new Account("1234567",1000);
//        new DrawThread("甲",acct,800).start();
//        new DrawThread("乙",acct,800).start();
        /*ThirdThreed rt=new ThirdThreed();
        FutureTask<Integer> task = new FutureTask<Integer>(rt);
        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+"循环变量i的值"+i);
            if(i==20){
                new Thread(task,"有返回值线程").start();
            }

        }
        try {
            System.out.println("子线程的返回值"+task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        //0101输出
//        for(int j=0;j<100;j++){
//            print0();
//            print1();
//        }
        //死锁
//        DeadLock d1 = new DeadLock();
//        new Thread(d1).start();
//        d1.init();
        //多线程下载
//       new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    downUtil.download();
//                    while (downUtil.getCommpleteRate()<=1){
//                        System.out.println("已完成："+downUtil.getCommpleteRate());
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();
        //以当前路径创建一个file对象
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"2.txt");
        try {
            File temp = File.createTempFile("mail",null);
            System.out.println(temp.getName());
            System.out.println(temp.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //使用FileInputStream来读取自身
       /* try {
            FileReader fis = new FileReader(Environment.getExternalStorageDirectory()+File.separator+"MobileLog.log");
            char[] bbuf = new char[32];
            int hasRead = 0;
            System.out.print("开始输出");
            while ((hasRead=fis.read(bbuf))>0){
                System.out.print(new String(bbuf,0,hasRead));
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"MobileLog.log");
            PrintStream ps = new PrintStream(fos);
            ps.println("普通字符串");
            ps.println(new Account());
            ps.println("new PushbackReader");

        } catch (Exception e) {
            e.printStackTrace();
        }

       /* InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);
        String buffer = null;
        try {
            while ((buffer=br.readLine())!=null){
                if(buffer.equals("exit")){
                    System.exit(1);
                }
                System.out.println("输入内同为："+buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       /* try {
            PushbackReader pr = new PushbackReader(new FileReader(Environment.getExternalStorageDirectory()+File.separator+"MobileLog.log"),128);
            char[] buf = new char[32];
            String lastContent="";
            int hasRead = 0;
            while ((hasRead=pr.read(buf))>0){
                String content = new String(buf,0,hasRead);
                int targetIndex =0;
                if((targetIndex=(lastContent+content).indexOf("new PushbackReader"))>0){
                    pr.unread((lastContent + content).toCharArray());
                    int len = targetIndex>32?32:targetIndex;
                    pr.read(buf,0,len);
                    System.out.print(new String(buf,0,len));
                }else{
                    System.out.print(lastContent);
                    lastContent=content;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/




    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            tv1.append("hello handler");
            //延时1s后又将线程加入到线程队列中
            handler.postDelayed(runnable,1000);
        }
    };

    Handler update_progress=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
            update_progress.post(update_Thread);
            if(msg.arg1==100){
                update_progress.removeCallbacks(update_Thread);
            }
        }
    };
    Runnable update_Thread=new Runnable() {
        int i=0;
        @Override
        public void run() {
            i=i+10;
            Message message=update_progress.obtainMessage();
            message.arg1=i;

            System.out.println("runn_id---->" + Thread.currentThread().getId());
            System.out.println("runn_name---->" + Thread.currentThread().getName());
            update_progress.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    };


    class MyHandler extends Handler{
        public MyHandler() {
        }
        //以Looper类型参数传递的函数，Looper为消息泵，不断循环的从消息队列中得到消息并处理，因此
        //每个消息队列都有一个Looper，因为Looper是已经封装好了的消息队列和消息循环的类
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("Handler_ID---->"+Thread.currentThread().getId());
            System.out.println("Handler_Name---->"+Thread.currentThread().getId());
            //将消息中的bundle数据取出来
            Bundle b = msg.getData();
            String whether = b.getString("wheather");
            int temperature = b.getInt("temperature");
            System.out.println("whether= "+whether+" ,temperature= "+temperature);


        }
    }
    //链表翻转

    public void reverse(){

    }

    public class MyFilenameFilter implements FilenameFilter{

        @Override
        public boolean accept(File dir, String filename) {

            return filename.endsWith(".java")||new File(filename).isDirectory();

        }
    }
    int i=0;
    public synchronized void print0(){

            try {
                if (i != 0) {
                    wait();
                }else{

                    System.out.println(i+"");
                    i++;
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
    public synchronized  void print1(){
        try {
            if(i!=1){
                wait();
            }else{

                System.out.println(i + "");
                i--;
                notifyAll();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 创建线程的三种方式
     * 1 继承Thread 编写简单，如果需要访问当前线程，则无需使用Thread.currentThread()方法
     * 直接用this即可访问
     * 2 实现runnable接口
     * 3 实现callable接口
     * 2，3：多个线程可以共享同一个target对象，所以适合多个线程来处理同一份资源的情况
     * ，从而可以讲CPU、代码和数据分开，形成清晰的模型，较好的体现面向对象思想；
     */
    class ThirdThreed implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            int i=0;
            for(;i<100;i++){
                System.out.println(Thread.currentThread().getName() + "的循环变量i的值"
                        + i);
            }
            return i;
        }
    }
    /**
     * 死锁
     */
    class A{
        public synchronized void foo(B b){
            System.out.println("当前线程名："+Thread.currentThread().getName()
            +"进入了A实例的foo方法");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程名："+Thread.currentThread().getName()
                    +"企图调用B实例的last方法");
            b.last();
        }
        public synchronized void last(){
            System.out.println("进入了A类的last方法内部");
        }
    }
    class B{
        public synchronized void bar(A a){
            System.out.println("当前线程名："+Thread.currentThread().getName()
                    +"进入了B实例的bar方法");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程名："+Thread.currentThread().getName()
                    +"企图调用A实例的last方法");
            a.last();
        }
        public synchronized void last(){
            System.out.println("进入了B类的last方法内部");
        }
    }
    public class DeadLock implements Runnable{
        A a=new A();
        B b=new B();
        public void init(){
            Thread.currentThread().setName("主线程");
            a.foo(b);
            System.out.println("进入主线程之后");
        }
        @Override
        public void run() {
            Thread.currentThread().setName("副线程");
            b.bar(a);
            System.out.println("进入副线程之后");

        }
    }




}
