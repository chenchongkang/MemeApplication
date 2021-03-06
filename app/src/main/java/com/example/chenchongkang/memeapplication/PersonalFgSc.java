package com.example.chenchongkang.memeapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenchongkang on 2018/3/7.
 */

public class PersonalFgSc extends AppCompatActivity implements View.OnClickListener{
    private ImageView image ;
    private Button downLoadBtn ;
    private static String url = "http://qq.yh31.com/tp/zjbq/201612231514480890.gif" ;
    private String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"201612231514480890.gif";

    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalfg_sc);
        ImageButton imageButton = (ImageButton) findViewById(R.id.return_3);
        imageButton.setOnClickListener(this);
        SharedPreferences userInfor = getApplication().getSharedPreferences("config", Context.MODE_PRIVATE);
        int userid = userInfor.getInt("userid", 0);

        /**
         * Glide 加载图片保存到本地
         */
//        Glide.with(getApplication())
//                .load("http://192.168.43.87:8081/meme/getuseravatar/" + userid)


        image = (ImageView)findViewById(R.id.image);
        downLoadBtn = (Button)findViewById(R.id.downLoadBtn);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        Glide.with(this).load(url).into(image);
        downLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String path =   getImagePath(url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path =   getImagePath(url);
                        /**
                         * 拷贝到指定路径
                         */
                        copyFile(path, imagePath);
                        Intent intentBroadcast = new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File file = new File(imagePath);
                        intentBroadcast.setData(Uri.fromFile(file));
                        sendBroadcast(intentBroadcast);
                    }
                }).start();
            }
        });

    }
    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_3:
                finish();
                break;
            default:
                break;
        }

    }

}
