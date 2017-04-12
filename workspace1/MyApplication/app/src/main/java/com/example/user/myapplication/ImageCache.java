package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by user on 2016/9/2.
 */
public class ImageCache {
    final static int HARD_CACHE_CAPACITY=30;
    //定义缓存A
    public static final HashMap<String,Bitmap> mHardBitmapCache
            = new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY/ 2, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if(size()>HARD_CACHE_CAPACITY){
                mSoftBitmapCache.put(eldest.getKey(),new SoftReference<Bitmap>(eldest.getValue()));
                return true;
            }else{
                return false;
            }
        }
    };
    //定义缓存B
    public static final ConcurrentHashMap<String,SoftReference<Bitmap>> mSoftBitmapCache
            = new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY/2);

    /**
     * 从缓存中获取图片
     */

    public static Bitmap getBitmapForCache(String url){
        synchronized (mHardBitmapCache){
            final Bitmap bitmap=mHardBitmapCache.get(url);
            if(bitmap!=null){
                mHardBitmapCache.remove(url);
                mHardBitmapCache.put(url,bitmap);
                return bitmap;
            }
            SoftReference<Bitmap> referenceBitmap = mSoftBitmapCache.get(url);
            if(referenceBitmap!=null){
                final Bitmap bitmap1=referenceBitmap.get();
                if (bitmap!=null){
                    return bitmap1;
                }else{
                    mSoftBitmapCache.remove(url);
                }
            }
            return null;

        }
    }
    class ImageDownLoadTask extends AsyncTask<String,Void,Bitmap>{
        private final static int IO_BUFFER_SIZE=1024*4;
        private String url;
        private final WeakReference<ImageView> imageViewRefrence;

        protected ImageDownLoadTask(ImageView imageView) {
            imageViewRefrence = new WeakReference<ImageView>(imageView);

        }


        @Override
        protected Bitmap doInBackground(String... params) {
            final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            url=params[0];
            final HttpGet getRequest = new HttpGet(url);
            try {
                HttpResponse response = client.execute(getRequest);
                final int statusCode=response.getStatusLine().getStatusCode();
                if(statusCode!= HttpStatus.SC_OK){
                    Log.w("TAG","从"+url+"中下载图片失败,错误："+statusCode);
                    return null;

                }
                final HttpEntity entity = response.getEntity();
                if(entity!=null){
                    InputStream inputStream=null;
                    OutputStream outputStream=null;
                    try {
                        inputStream =entity.getContent();
                        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                        outputStream = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
                        //copy(inputStream, outputStream);
                        outputStream.flush();
                        final byte[] data =dataStream.toByteArray();
                        final Bitmap bitmap =BitmapFactory.decodeByteArray(data, 0, data.length);
                        return bitmap;
                    }finally {
                        if (inputStream !=null) {
                            inputStream.close();
                        }
                        if (outputStream !=null) {
                            outputStream.close();
                        }
                        entity.consumeContent();
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(client!=null){
                    client.close();
                }
            }

            return null;
        }
    }
}
