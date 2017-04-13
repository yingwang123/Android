package com.example.user.myapplication.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by user on 2017/4/13.
 *
 * 由于大多数启动服务都不必同时处理多个请求，因此使用IntentService类实现服务时比较好的选择
 * intentService执行操作
 * # 创建默认的工作线程，用于在应用的主线程外执行传递给onStartCommand()的所有Intent
 * # 创建工作队列，用于将Intent逐一传递给onHandleIntent()实现，这样就不必担心多线程问题
 * # 在处理完所有启动请求后停止服务，因此您永远不必条用stopself()
 * # 提供onBind（）的默认实现（返回null）
 * # 提供onStartCommand()的默认实现，可将intent依次发送到工作队列和onHandleIntent()实现
 * 综上，只需要实现onHandleIntent（）来完成客户端提供的工作即可
 */
public class HelloIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public HelloIntentService(){
        this("jfjflak");
    }
    public HelloIntentService(String name) {

        super("HelloIntentService");
    }

    //该函数用于针对Intent的不同进行不同的事务处理就可以了.执行完所一个Intent请求对象所对应的工作之后，如果没有新的Intent请求达到，
    //则自动停止Service；否则ServiceHandler会取得下一个Intent请求传人该函数来处理其所对应的任务
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }
}
