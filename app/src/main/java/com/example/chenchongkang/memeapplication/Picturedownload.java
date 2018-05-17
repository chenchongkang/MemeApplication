package com.example.chenchongkang.memeapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenchongkang on 2018/5/9.
 */

public class Picturedownload extends AppCompatActivity implements View.OnClickListener {
    private ImageView image ;
    private Button downLoadBtn ;
    private int a,b;
    private static String url;
    private String imagePath;

    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_picturedownload);
        ImageButton imageButton = (ImageButton) findViewById(R.id.return_picture_download);
        imageButton.setOnClickListener(this);
        SharedPreferences userInfor = getApplication().getSharedPreferences("config", Context.MODE_PRIVATE);
        int userid = userInfor.getInt("userid", 0);

        Intent intent = getIntent();
        a = intent.getIntExtra("imgid", 0);
        imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+a+".gif";
        url = "http://192.168.43.87:8081/meme/getpictureid/" + a;

        /**
         * Glide 加载图片保存到本地
         */
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path =  getImagePath(url);
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
                Toast.makeText(Picturedownload.this,"下载成功",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl+"?downloads")
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
            case R.id.return_picture_download:
                finish();
                break;
            default:
                break;
        }

    }

}
