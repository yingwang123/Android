package com.example.user.myapplication.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.user.myapplication.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by user on 2017/3/7.
 */
public class ImageUtils extends Activity{
    private int ImWidth,ImHeight,Width,Height;
    boolean is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagebigload);
        getScreenWidthAndHeight();
        loadBigImage((ImageView) findViewById(R.id.iv));

    }

    public void loadBigImage(final ImageView view){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            final String path = Environment.getExternalStorageDirectory()+"/DCIM"+"/3.jpg";
            BitmapFactory.Options options = new BitmapFactory.Options();
            final Bitmap bitmap=BitmapFactory.decodeFile(path,options);
            //view.setImageBitmap(getImageByScaleSize(path));
            view.setImageBitmap(getImageCompress(bitmap));
           /*view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(is){
                       view.setImageBitmap(getImageCompress(bitmap));
                   }else{
                       view.setImageBitmap(getImageScaleSizeByTec(path));
                   }
                   is=!is;
               }
           });*/
        }

    }
     public Bitmap getImageByScaleSize(String path){
         int scaleSize = 1;
         BitmapFactory.Options options = new BitmapFactory.Options();
         //getImageByScaleSize(path);
         if(ImWidth>ImHeight&&ImWidth>Width){
             scaleSize=(int)(ImWidth*1.0f/Width+0.5f);
         }else if (ImWidth<ImHeight&&ImHeight>Height){
             scaleSize=(int)(ImHeight*1.0f/Height+0.5f);
         }else{
             scaleSize = (int)(Math.sqrt(ImHeight*1.0f/Height+ImWidth*1.0f/Width)/2+0.5f);
         }
         options.inSampleSize = scaleSize;
         Bitmap bitmap = BitmapFactory.decodeFile(path,options);
         return bitmap;

     }
    public Bitmap getImageCompress(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        int options = 100;
        //压缩到100k
       while (baos.toByteArray().length/1024>100){
           byte[] bytes=baos.toByteArray();
            baos.reset();
            options-=10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);
        }

        ByteArrayInputStream im = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap1=BitmapFactory.decodeStream(im,null,null);
        return bitmap1;

    }
    public Bitmap getImageScaleSizeByTec(String path){
        int scaleSize=1;
        getImageWidthAndHeight(path);
        int WidthScaleSize=(int)(ImWidth*1.0f/Width*1.0f+0.5f);
        int HeightScaleSize = (int)(ImHeight*1.0f/Height*1.0f+0.5f);
        scaleSize = (int)(Math.sqrt(WidthScaleSize*WidthScaleSize+HeightScaleSize*HeightScaleSize)+0.5f);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=scaleSize;
        Bitmap bitmap=BitmapFactory.decodeFile(path,options);
        return  bitmap;

    }
    private void getImageWidthAndHeight(String path){
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            ImWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH,0);
            ImHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH,0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getScreenWidthAndHeight(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Width = metrics.widthPixels;
        Height = metrics.heightPixels;
    }

}
