package com.example.chenchongkang.memeapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Memepicture extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.zx_meme);
        ImageView imageView= (ImageView) findViewById(R.id.return_3);
        imageView.setOnClickListener(this);
        Intent intent=getIntent();
        int a= intent.getIntExtra("memeid",0);
        ImageView memeCover=(ImageView)findViewById(R.id.meme_cover);

                    Glide.with(this).load("http://172.24.7.1:8081/meme/getmemecover/" + a)
                    .placeholder(R.drawable.ic_startone)
                    .thumbnail(0.1f)
//                    .centerCrop()
                    .into(memeCover);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_3:
                finish();
                break;
            default:
                break;
        }

    }
}